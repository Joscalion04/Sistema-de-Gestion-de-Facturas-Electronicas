 function productoNotFound(){
    Swal.fire({
        title: '¡ERROR!',
        text: `PRODUCTO NO ENCONTRADO`,
        icon: 'error',
        confirmButtonText: 'Aceptar',
        customClass: {
            popup: 'swal2-dark',
            title: 'swal2-dark',
            content: 'swal2-dark',
            confirmButton: 'swal2-dark'
        }
    });
}

 function clienteNotFound(){
    Swal.fire({
        title: '¡ERROR!',
        text: `CLIENTE NO ENCONTRADO`,
        icon: 'error',
        confirmButtonText: 'Aceptar',
        customClass: {
            popup: 'swal2-dark',
            title: 'swal2-dark',
            content: 'swal2-dark',
            confirmButton: 'swal2-dark'
        }
    });
}

 function errorDeTransaccion(){
    Swal.fire({
        title: '¡ERROR!',
        text: `ERROR AL REGISTRAR`,
        icon: 'error',
        confirmButtonText: 'Aceptar',
        customClass: {
            popup: 'swal2-dark',
            title: 'swal2-dark',
            content: 'swal2-dark',
            confirmButton: 'swal2-dark'
        }
    });
}

 function transaccionExitosa(){
    Swal.fire({
        title: '¡REALIZADO CON ÉXITO!',
        text: 'SE HA REALIZADO CORRECTAMENTE',
        icon: 'success',
        confirmButtonText: 'ACEPTAR',
        customClass: {
            popup: 'swal2-dark',
            title: 'swal2-dark',
            content: 'swal2-dark',
            confirmButton: 'swal2-dark'
        }
    });
}