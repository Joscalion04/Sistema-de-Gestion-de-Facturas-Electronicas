var backend="http://localhost:8080/api";
var api_login=backend+'/login';

var registerMode = 0;

var loginstate ={
    logged: false,
    user : {credencial:"", contrasenha:"", estado_cuenta:0, rol:""},
    prov : {identificador:"", nombre:"", correo:"", telefono:""}
}

async function checkuser(){
    let request = new Request(api_login+'/current-session-prov', {method: 'GET'});
    let response = await fetch(request);
    if (response.ok) {
        loginstate.logged = true;
        loginstate.prov = await response.json();

        request = new Request(api_login+'/current-session-user', {method: 'GET'});
        response = await fetch(request);

        if(response.ok){
            loginstate.user = await response.json();
        }
    }
    else {
        loginstate.logged = false;
    }
}

async function menu(){
    await checkuser();
    if (!loginstate.logged
        && document.location.pathname !== "/pages/login/view.html") {
        document.location = "/pages/login/view.html";
        throw new Error("Usuario no autorizado");
    }
    render_menu();
}

function render_menu() {
    let html;
    if (!loginstate.logged) {
        html = `
            <div class="wrapper">
            <div class="login-box">
                <div class="login-header">
                    <span id="title-login"> LOGIN</span>
                </div>
                <form>
                    <div class="input-box">
                        <input type="text" id="user_id" class="input-field" required>
                        <label for="user" class="label">Nombre de usuario</label>
                        <i class="bx bx-user icon"></i>
                    </div>
                    <div class="input-box">
                        <input type="password" id="user_password" class="input-field" required>
                        <label for="pass" class="label">Contraseña</label>
                        <i class="bx bx-lock-alt icon" id="show-password"></i>
                    </div>

                    <div class="input-box">
                        <input type="submit" id="input-buttom" class="input-submit" value="Iniciar sesión">
                    </div>
                    <div class="register">
                        <span id="messageBtnText">¿Aún no estás registrado?
                            <a id="changeModeBtn">Registrate aquí.</a>
                        </span>
                    </div>
                </form>
            </div>
        </div>
        `;
        document.querySelector('#menu').innerHTML = html;
        document.querySelector("#menu #changeModeBtn").addEventListener('click', change_mode);
        document.querySelector('#menu #input-buttom').addEventListener('click', login_signup);

        /*  <p>Los datos se han ingresado de forma érronea</p>
            <p>Credenciales erroneas</p>
            <p>La cuenta no ha sido habilitada por un administrador. Intentelo en otro momento.</p>
         */
    } else {
        html = `
            <nav class="nav">
                <div class="container">
                    <div class="logo">
                        <img src="/img/logo.png" alt="LOGO">
                    </div>
                    <div id="mainListDiv" class="main_list">
                        <ul class="navlinks">
                            <li id="acerca_de_link"><a href="#">ACERCA DE</a></li>
                            <li id="facturar_link"><a href="#">FACTURAR</a></li>
                            <li id="clientes_link"><a href="#">CLIENTES</a></li>
                            <li id="productos_link"><a href="#">PRODUCTOS</a></li>
                            <li id="facturas_link"><a href="#">FACTURAS</a></li>
                            <li id="perfil_link"><a href="#">USUARIO</a></li>
                            <li id="cerrar_link"><a href="#">CERRAR SESIÓN</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        `;
        document.querySelector('#menu').innerHTML = html;
        document.querySelector("#menu #acerca_de_link").addEventListener('click', e => {
            document.location = "/pages/acerca_de/view.html";
        });
        document.querySelector("#menu #facturar_link").addEventListener('click', e => {
            document.location = "/pages/facturar/view.html";
        });
        document.querySelector("#menu #clientes_link").addEventListener('click', e => {
            document.location = "/pages/clientes/view.html";
        });
        document.querySelector("#menu #productos_link").addEventListener('click', e => {
            document.location = "/pages/productos/view.html";
        });
        document.querySelector("#menu #facturas_link").addEventListener('click', e => {
            document.location = "/pages/facturas/view.html";
        });
        document.querySelector("#menu #perfil_link").addEventListener('click', async (e) => {
            document.location = "/pages/perfil/view.html";
        });
        document.querySelector("#menu #cerrar_link").addEventListener('click', logout);
    }
}

/*
*   La siguiente funcion 'change_mode' se encarga de cambiar los labels de la ventana de inicio de sesión,
*   y a su vez, permite alternar entre el modo de edición,y el modo de creación de usuario.
*   Todo esto en la ventana del login.
*/

function change_mode(){
    registerMode = !registerMode;

    const messageSpan = document.getElementById("messageBtnText");
    const changeModeLink = document.getElementById("changeModeBtn");

    if(registerMode){
        document.getElementById("title-login").innerHTML = "SIGNUP";
        messageSpan.firstChild.textContent = "¿Estás registrado? ";
        changeModeLink.textContent = "Inicia sesión aquí.";
        document.getElementById("input-buttom").value = "Registrar";
    } else {
        document.getElementById("title-login").innerHTML = " LOGIN";
        messageSpan.firstChild.textContent = "¿Aún no estás registrado? ";
        changeModeLink.textContent = "Registrate aquí.";
        document.getElementById("input-buttom").value = "Iniciar sesión";
    }
}

/*
*   La siguiente funcion 'login_signup' se encarga de alternar entre el modo de inicio de sesión,
*   y el modo de registro de usuario.
*   Todo esto en la ventana del login.
*/

function login_signup(event){
    event.preventDefault();
    if(registerMode){
        add_user();
    } else {
        login_user();
    }
}

/*
*   La siguiente funcion 'logout' se encarga de cerrar la sesión que se encuentre activa en el cliente.
*/

function logout(event){
    event.preventDefault();
    let request = new Request(api_login+'/logout', {method: 'POST'});
    (async ()=>{
    const response = await fetch(request);
    if (!response.ok) {errorMessage(response.status);return;}
    document.location="/pages/login/view.html";
    })();
}

function errorMessage(status){
    switch(status){
        case 404: error= "Registro no encontrado"; break;
        case 409: error="Registro duplicado"; break;
        case 401: error="Usuario no autorizado"; break;
        case 403: error="Usuario no tiene derechos"; break;
    }
    window.alert(error);
}


