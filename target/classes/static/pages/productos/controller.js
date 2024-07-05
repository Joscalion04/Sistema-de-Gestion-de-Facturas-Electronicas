var api = backend + '/producto';

document.addEventListener("DOMContentLoaded", loaded)

var productos = {
    list: [],
    item: {codigoProducto: 0, identificadorProveedor: "", descripcion: "", precio: 0}
}

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
    }

    await render_content();
}

function render_content() {

    document.querySelector('#input-button').addEventListener('click', submitInfo);
    document.querySelector("#buscar-producto").addEventListener("click", function (event) {
        event.preventDefault();
        buscarProducto();
    });
    document.querySelector("#limpiar-producto").addEventListener("click", function (event) {
        event.preventDefault();
        limpiarProducto();
    });

    fetchAndList().then(r => fetchAndList());


}

function limpiarProducto() {
    document.getElementById("codigo").value = "";
    document.getElementById("descripcion").value = "";
    document.getElementById("precio").value = "";
}

function submitInfo() {
    loadItem();
    let request = new Request(api + '/agregar', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(productos.item)
    });
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorDeTransaccion();
            return;
        }
        transaccionExitosa();
        empty_item();
        document.location = "/pages/productos/view.html";
    })();
}

function loadItem() {
    productos.item.codigoProducto = document.getElementById("codigo").value;
    productos.item.identificadorProveedor = loginstate.prov.identificador;
    productos.item.descripcion = document.getElementById("descripcion").value;
    productos.item.precio = document.getElementById("precio").value;
}

async function fetchAndList() {
    const response = await fetch(`${api}/producto?cred=${loginstate.prov.identificador}`);
    if (!response.ok) {
        errorMessage(response.statusText);
        return;
    }
    productos.list = await response.json();
    fetchItem(productos.list);
}

function fetchItem(data) {
    const tbody = document.getElementById("list");
    tbody.innerHTML = '';

    data.forEach(producto => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = producto.codigoProducto;
        row.appendChild(idCell);

        const infoCell = document.createElement('td');
        infoCell.textContent = producto.descripcion;
        row.appendChild(infoCell);

        const priceCell = document.createElement('td');
        priceCell.textContent = producto.precio;
        row.appendChild(priceCell);

        const editCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Editar';
        editButton.addEventListener('click', () => searchProducto(producto.codigoProducto));
        editCell.appendChild(editButton);
        row.appendChild(editCell);

        tbody.appendChild(row);
    })
}

function buscarProducto() {
    var codigoProducto = document.getElementById("buscar-producto-search").value;
    document.getElementById("buscar-producto-search").value = "";

    searchProducto(codigoProducto).then(r => searchProducto(codigoProducto));
}

async function searchProducto(cod) {
    const response = await fetch(`${api}/buscar?cod=${cod}&cred=${loginstate.prov.identificador}`);
    if (!response.ok) {
        productoNotFound();
        return;
    }
    productos.item = await response.json();
    showItem();

}

function showItem() {
    document.getElementById("codigo").value = productos.item.codigoProducto;
    document.getElementById("descripcion").value = productos.item.descripcion;
    document.getElementById("precio").value = productos.item.precio;
}