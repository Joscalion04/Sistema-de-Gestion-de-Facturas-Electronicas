var backend= "http://localhost:8080/api";
var api_login= backend+'/login';

var user ={
    credencial:"",
    contrasenha:"",
    estado_cuenta: 0,
    rol:"CLI"
}

document.addEventListener("DOMContentLoaded", loaded)

async function loaded(event){
    try{await menu();}catch(error){}
}

// Lo de abajo es para limiar los espacios necesarios que almacenan datos de los clientes.

function empty_item(){
    user.user={credencial:"", contrasenha:"", estado_cuenta: 0, rol:""};
}

function load_item(){
    user={
        credencial:document.getElementById("user_id").value,
        contrasenha:document.getElementById("user_password").value,
        status: 0,
        rol:"CLI"
    };
}

function validate_item(){
    let error= false;

    document.querySelectorAll('input').forEach( (i)=> {i.classList.remove("invalid");});

    if (user.credencial.length===0){
        document.querySelector("#user_id").classList.add("invalid");
        error=true;
    }

    if (user.contrasenha.length===0){
        document.querySelector("#user_password").classList.add("invalid");
        error=true;
    }

    if(user.rol !== "CLI" && user.rol !== "ADM"){
        error=true;
    }

    if(user.status !== 0 && user.status !== 1){
        error=true;
    }

    return error;
}

function add_user(){
    load_item();
    if(validate_item()) return;
    let request = new Request(api_login+'/signup', {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(user)});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        empty_item();
        document.location="/pages/login/view.html";
    })();
}

function login_user(){
    load_item();
    if(validate_item()) return;
    let request = new Request(api_login+'/login', {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(user)});
    (async ()=> {
        const response = await fetch(request);
        if (!response.ok) {
            const error = await response.json();
            alert(error.message);
            return;
        }

        await checkuser();

        if(loginstate.user.rol === 'ADM'){
            document.location="/pages/admin/view.html";
        } else {
            document.location="/pages/perfil/view.html";
        }
    })();
}