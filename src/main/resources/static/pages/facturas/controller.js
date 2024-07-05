var api = backend + '/facturas';

document.addEventListener("DOMContentLoaded", loaded)

var facturas = {
    list: [],
    item: {fecha_emision: "", num_factura: ""}
}

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
    }

    render_content();
}

function render_content() {
    document.querySelector("#buscar-factura-search").addEventListener("click", function (event) {
        event.preventDefault();
        searchFactura().then(r => searchFactura());
    });
    document.querySelector("#limpiar-factura").addEventListener("click", function (event) {
        event.preventDefault();
        limpiarFactura();
    });

    fetchAndList().then(r => fetchAndList());

}

async function searchFactura() {
    loadFactura();
    const response = await fetch(`${api}/buscar?num=${facturas.item.num_factura}&cred=${loginstate.prov.identificador}`);
    if (!response.ok) {
        errorMessage(response.statusText);
        return;
    }
    facturas.list = await response.json();
    fetchItem(facturas.list);
}

function loadFactura() {
    facturas.item.num_factura = document.getElementById("buscar-factura").value;
}

function limpiarFactura() {
    document.getElementById("buscar-factura").value = "";
    fetchAndList().then(r => fetchAndList());
}

async function fetchAndList() {
    const response = await fetch(`${api}/facturas?cred=${loginstate.prov.identificador}`);
    if (!response.ok) {
        errorMessage(response.statusText);
        return;
    }
    facturas.list = await response.json();
    fetchItem(facturas.list);
}

function fetchItem(data) {
    const tbody = document.getElementById('facturas-list');
    tbody.innerHTML = '';

    data.forEach(factura => {
        const row = document.createElement('tr');

        const idenCell = document.createElement('td');
        idenCell.textContent = factura.num_factura;
        row.appendChild(idenCell);

        const dateCell = document.createElement('td');
        dateCell.textContent = factura.fecha_emision;
        row.appendChild(dateCell);

        const totalCell = document.createElement('td');
        totalCell.textContent = factura.total;
        row.appendChild(totalCell);

        // Add the button for generating PDF
        const pdfButtonCell = document.createElement('td');
        const pdfButton = document.createElement('input');
        pdfButton.textContent = 'PDF';
        pdfButton.type = "submit";
        pdfButton.onclick = function () {
            generateFile(factura.num_factura, 'pdf');
        };
        pdfButtonCell.appendChild(pdfButton);
        row.appendChild(pdfButtonCell);

        // Add the button for generating XML
        const xmlButtonCell = document.createElement('td');
        const xmlButton = document.createElement('input');
        xmlButton.textContent = 'XML';
        xmlButton.type = "submit";
        xmlButton.onclick = function () {
            generateFile(factura.num_factura, 'xml');
        };
        xmlButtonCell.appendChild(xmlButton);
        row.appendChild(xmlButtonCell);

        tbody.appendChild(row);
    });
}

function generateFile(numFactura, fileType) {

    fetch(`${api}/${fileType}?num_factura=${numFactura}&cred=${loginstate.prov.identificador}`)
        .then(response => {
            if (response.ok) {
                return response.blob();
            } else {
                errorMessage(response.status);
            }
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `factura_${numFactura}.${fileType}`;
            document.body.appendChild(a);
            a.click();
            a.remove();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
