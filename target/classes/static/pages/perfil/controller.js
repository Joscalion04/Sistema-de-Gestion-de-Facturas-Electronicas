var api=backend+'/perfil';

document.addEventListener("DOMContentLoaded", loaded)

var facturas={
    list: []
}

var cliente={
    identificacion:"", nombre:"", correo:"", telefono:""
};

async function loaded(event){
    try{await menu();}catch(error){}

    render_content();
}

function render_content() {
    html = `
        <h1>${loginstate.prov.nombre}</h1>
        <h2>${loginstate.prov.correo}</h2>
        <h2>${loginstate.prov.telefono}</h2>
        <h2>Proveedor/a</h2>
        <a><input type="submit" value="Editar datos" class="LogoutButton" id="edit-buttom"></a><br>
    `;
    document.querySelector('#datos_perfil_usuario').innerHTML = html;
    document.querySelector('#datos_perfil_usuario #edit-buttom').addEventListener('click', edit_infomation);

    fetchAndList();
    last_client();
}

async function fetchAndList() {
    try {
        const response = await fetch(`${api}/buscar?cred=${loginstate.prov.identificador}`);
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        facturas.list = await response.json();
        fetchItem(facturas.list);
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

async function last_client() {
    try {
        const response = await fetch(api + '/ultimo-cliente');
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        cliente = await response.json();

        html = `
            <tr>
                <td>${cliente.identificacion}</td>
                <td>${cliente.nombre}</td>
                <td>${cliente.correo}</td>
            </tr>
        `;
        document.querySelector('#ultimo_cliente').innerHTML = html;
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

function fetchItem(data) {
    const tbody = document.getElementById('invoices_list');
    tbody.innerHTML = '';

    data.forEach(factura => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = factura.num_factura;
        row.appendChild(idCell);

        const dateCell = document.createElement('td');
        dateCell.textContent = factura.fecha_emision;
        row.appendChild(dateCell);

        const totalCell = document.createElement('td');
        totalCell.textContent = factura.total;
        row.appendChild(totalCell);

        tbody.appendChild(row);
    })
}

function toggle_itemview(){
    document.getElementById("itemoverlay").classList.toggle("active");
    document.getElementById("itemview").classList.toggle("active");
}

function charge_user_information(){
    if(loginstate.logged){
        document.querySelectorAll('#itemview input').forEach( (i)=> {i.classList.remove("invalid");});
        document.getElementById("edit_user_id").value = loginstate.prov.identificador;
        document.getElementById("edit_user_name").value = loginstate.prov.nombre;
        document.getElementById("edit_user_email").value = loginstate.prov.correo;
        document.getElementById("edit_user_phone").value = loginstate.prov.telefono;
        document.querySelector('#updateButtom').addEventListener('click', edit);
        document.querySelector('#cancelButtom').addEventListener('click', toggle_itemview);
    }
}

function edit_infomation(){
    toggle_itemview();
    charge_user_information();
}

function load_client_info(){
    cliente={
        identificador:document.getElementById("edit_user_id").value,
        nombre:document.getElementById("edit_user_name").value,
        correo: document.getElementById("edit_user_email").value,
        telefono:document.getElementById("edit_user_phone").value
    };
}

function edit(event){
    event.preventDefault();
    load_client_info();
    let request = new Request(api+`/actualizar-datos`, {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(cliente)});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        toggle_itemview();
        document.location="/pages/perfil/view.html";
    })();
}