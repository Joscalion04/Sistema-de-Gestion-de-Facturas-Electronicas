var api_admin=backend+'/admin';

var proveedores={
    list: []
}

document.addEventListener("DOMContentLoaded", loaded)

async function loaded(event){
    try{await menu();}catch(error){}

    load_admin_info();
}

function load_admin_info() {
    html = `
        <div className="contenedor-img">
            <img src="/img/profile-user.png" alt="Icono"/>
        </div>
        <h1>${loginstate.user.credencial}</h1>
        <h2>Administrador/a</h2>
        <a><input type="submit" value="Cerrar sesión" class="LogoutButton" id="logoutButtom"></a><br>
    `;
    document.querySelector('#info-admin').innerHTML = html;
    document.querySelector('#logoutButtom').addEventListener('click', logout);

    fetchAndList();
}

async function fetchAndList() {
    try {
        const response = await fetch(api_admin + `/listado`);
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        proveedores.list = await response.json();

        var table = document.getElementById("providers_list");
        table.innerHTML = "";
        proveedores.list.forEach(proveedor => loadRowProv(table, proveedor));

    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

function loadRowProv(table, prov) {
    var row = document.createElement("tr");
    var state = (prov.estado === 1) ? "Habilitada" : "Deshabilitada";
    row.innerHTML = `<td>${prov.credencial}</td>` +
        `<td><a><button class="button-with-icon" type="submit" id="approveButtom"><i class="fa-solid fa-x"></i></button></a></td>` +
        `<td> ${state} </td>`;
    row.querySelector("#approveButtom").addEventListener("click", () => { habilitarCuenta(prov) });
    table.append(row);
}

async function habilitarCuenta(cuenta) {
    try {
        const response = await fetch(`${api_admin}/approve?id=${cuenta.credencial}`);
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        document.location="/pages/admin/view.html";

    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}