var api_clientes = 'http://localhost:8080/api/clientes';

var state = {
    list: [],
    cliente: { identificacion: "", nombre: "", correo: "", telefono: "" }
}
var edit = false

document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try{await menu();}catch(error){}

    const request = new Request(api_clientes+"/clientes", { method: 'GET', headers: {} });
    try {
        const response = await fetch(request);
        console.log(response);
        if (response.ok) {
            state.list = await response.json();
            loadClientesTable();
        }
        document.querySelector("#registrar-cliente").addEventListener("click", registrarCliente);
        document.querySelector("#buscar-cliente").addEventListener("click", function(event) {
            event.preventDefault();
            buscarCliente();
        });
        document.querySelector("#limpiar-cliente").addEventListener("click", function(event) {
            event.preventDefault();
            limpiarClienteForm();
        });
    } catch (error) {
        console.error(error);
    }
}

function loadClientesTable() {
    var table = document.getElementById("table-clientes");
    table.innerHTML = "";
    state.list.forEach(cliente => loadRowCliente(table, cliente));
}

function loadRowCliente(table, cliente) {
    var row = document.createElement("tr");
    row.innerHTML = `<td>${cliente.identificacion}</td>` +
        `<td>${cliente.nombre}</td>` +
        `<td>${cliente.correo}</td>` +
        `<td>${cliente.telefono}</td>` +
        `<td><input class="edit-button" id="editar-cliente" type="submit" value="EDITAR"></td>`;
    row.querySelector("#editar-cliente").addEventListener("click", () => { editarCliente(cliente) });
    table.append(row);
}

function limpiarClienteForm(){
    document.getElementById("idF").value = "";
    document.getElementById("nombre-completo").value = "";
    document.getElementById("correo").value = "";
    document.getElementById("telefono").value = "";
    document.getElementById("identificacion-cliente-search").value = "";
    document.getElementById("idF").disabled = false;
    edit = false;
}

function editarCliente(clienteEditar) {
    document.getElementById("idF").value = clienteEditar.identificacion;
    document.getElementById("nombre-completo").value = clienteEditar.nombre;
    document.getElementById("correo").value = clienteEditar.correo;
    document.getElementById("telefono").value = clienteEditar.telefono;
    document.getElementById("idF").disabled = true;
    edit = true;
}

function buscarCliente() {
    var identificacionCliente = document.getElementById("identificacion-cliente-search").value;
    document.getElementById("identificacion-cliente-search").value = "";
    try {
        let request = new Request(api_clientes + '/buscarCliente', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ identificacionCliente: identificacionCliente })
        });
        (async () => {
            const response = await fetch(request);
            if (!response.ok) {
                clienteNotFound();
            } else {
                try{
                    state.cliente = await response.json();
                    editarCliente(state.cliente);
                    transaccionExitosa();
                }catch(error){
                    clienteNotFound();
                }
            }
            render_menu();
        })();
    } catch (error) {
        clienteNotFound();
        return;
    }
}

function registrarCliente() {
    var ClienteRegistra = {
        identificacion: document.getElementById("idF").value,
        nombre: document.getElementById("nombre-completo").value,
        correo: document.getElementById("correo").value,
        telefono: document.getElementById("telefono").value
    };
    try {
        let request;
        if(!edit){
            request = new Request(api_clientes+'/registrarCliente', {method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify(ClienteRegistra)});
        }else{
            request = new Request(api_clientes+'/editarCliente', {method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify(ClienteRegistra)});
        }
        (async ()=>{
            const response = await fetch(request);
            if (!response.ok) {
                errorDeTransaccion();
            }else{
                transaccionExitosa();
                render_menu();
            }
        })();
    } catch (error) {
        errorDeTransaccion();
        return;
    }
}