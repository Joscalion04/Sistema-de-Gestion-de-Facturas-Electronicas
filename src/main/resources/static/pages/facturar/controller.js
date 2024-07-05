var api_facturar = 'http://localhost:8080/api/facturar';

var state = {
    cliente: {identificacion: "", nombre: "", correo: "", telefono: ""},
    producto: {codigoProducto: 1, identificadorProveedor: "", descripcion: "", precio: 0, conteo: 1},
    carrito: []
}

var producto = {
    codigoProducto: 1,
    identificadorProveedor: "",
    descripcion: "",
    precio: 0,
    conteo: 1
};

function totalProducto(producto) {
    return producto.precio * producto.conteo;
}

function decrementarProducto(producto) {
    producto.conteo = producto.conteo - 1;
    loadCarritoTable();
}

function incrementarProducto(producto) {
    producto.conteo = producto.conteo + 1;
    loadCarritoTable();
}

function calcularTotalCarrito() {
    let totalCarrito = 0;
    state.carrito.forEach(producto => {
        totalCarrito += producto.conteo * producto.precio;
    });
    return totalCarrito;
}

document.addEventListener("DOMContentLoaded", loaded)

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
    }
    document.querySelector("#boton-buscarCliente-facturar").addEventListener("click", function (event) {
        event.preventDefault();
        buscarCliente();
    });
    document.querySelector("#boton-buscarProducto-facturar").addEventListener("click", function (event) {
        event.preventDefault();
        buscarProducto();
    });
    document.querySelector("#boton-facturar-carritoCompras").addEventListener("click", function (event) {
        event.preventDefault();
        facturar();
    });
}

function loadCarritoTable() {
    var table = document.getElementById("table-carritoCompras");
    table.innerHTML = "";
    state.carrito.forEach(producto => loadRowCarrito(table, producto));
    document.getElementById("total-facturar").textContent = "Total: " + calcularTotalCarrito();
}

function loadRowCarrito(table, producto) {
    var row = document.createElement("tr");
    row.innerHTML = `<td>
                        <form>
                            <p>${producto.conteo}</p>
                            <button id="decrementarProducto-carrito" class="button-with-icon" type="button">
                                <i class="fa-solid fa-x"></i>
                            </button>
                        </form>
                    </td>` +
        `<td>${producto.descripcion}</td>` +
        `<td>${producto.precio}</td>` +
        `<td>${totalProducto(producto)}</td>` +
        `<td>
                        <form>
                            <button id="incrementarProducto-carrito" class="button-with-icon-up" type="button">
                                <i class="fa-solid fa-angles-up"></i>
                            </button>
                        </form>
                    </td>`;
    row.querySelector("#decrementarProducto-carrito").addEventListener("click", () => {
        decrementarProducto(producto)
    });
    row.querySelector("#incrementarProducto-carrito").addEventListener("click", () => {
        incrementarProducto(producto)
    });
    table.append(row);
}

function buscarCliente() {
    var identificacionCliente = document.getElementById("buscar-cliente-facturar").value;
    document.getElementById("buscar-cliente-facturar").value = "";
    try {
        let request = new Request(api_facturar + '/buscarCliente', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({identificacionCliente: identificacionCliente})
        });

        (async () => {
            const response = await fetch(request);
            if (!response.ok) {
                clienteNotFound();
            } else {
                try {
                    state.cliente = await response.json();
                    document.getElementById("cliente-facturar").textContent = "Cliente: " + state.cliente.nombre;
                } catch (error) {
                    clienteNotFound();
                }
            }
            render_menu();
        })();
    } catch (error) {
        clienteNotFound();
        console.error(error);
        return;
    }
}

function buscarProducto() {
    var identificacionProducto = document.getElementById("buscar-producto-facturar").value;
    document.getElementById("buscar-producto-facturar").value = "";
    try {
        let request = new Request(api_facturar + '/buscarProducto', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({identificacionProducto: identificacionProducto})
        });
        (async () => {
            const response = await fetch(request);
            if (!response.ok) {
                productoNotFound();
            } else {
                try {
                    producto = await response.json();
                    state.carrito.push(producto);
                    loadCarritoTable();
                } catch (error) {
                    productoNotFound();
                }
            }
            render_menu();
        })();
    } catch (error) {
        console.error(error);
        return;
    }
}

function facturar() {
    const datosSolicitud = {
        identificacionCliente: state.cliente.identificacion,
        carritoCompras: state.carrito
    };
    try {
        let request = new Request(api_facturar + '/procesar', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(datosSolicitud)
        });
        (async () => {
            const response = await fetch(request);
            if (!response.ok) {
                errorDeTransaccion();
            } else {
                try {
                    state.cliente = {identificacion: "", nombre: "", correo: "", telefono: ""};
                    state.carrito = [];
                    document.getElementById("cliente-facturar").textContent = "Cliente: ";
                    transaccionExitosa();
                    loadCarritoTable();
                } catch (error) {
                    errorDeTransaccion();
                }
            }
            render_menu();
        })();
    } catch (error) {
        errorDeTransaccion();
    }
}
