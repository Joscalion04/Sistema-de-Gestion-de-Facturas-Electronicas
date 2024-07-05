package org.segundahacienda.segundahacienda.presentation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.segundahacienda.segundahacienda.logic.*;
import org.segundahacienda.segundahacienda.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class Clientes {
    @Autowired
    Service service;

    @GetMapping("/clientes")
    public Iterable<Cliente> show(@AuthenticationPrincipal UserDetailsImp user){
        Usuario usuario = new Usuario(user.getUser().getCredencial(), null,
                user.getUser().getEstado(), user.getUser().getRol());
        return service.lista_clientes_proveedor(usuario.getCredencial());
    }

    @PostMapping("/buscarCliente")
    public Cliente buscar(@AuthenticationPrincipal UserDetailsImp user, @RequestBody Map<String, String> requestBody) {
        String identificacionCliente = requestBody.get("identificacionCliente");
        Usuario usuario = new Usuario(user.getUser().getCredencial(), null, user.getUser().getEstado(), user.getUser().getRol());
        return service.buscar_cliente(identificacionCliente, usuario.getCredencial());
    }


    @PostMapping("/editarCliente")
    public void editar(@AuthenticationPrincipal UserDetailsImp user,  @RequestBody Cliente ClienteRegistra){
        service.crear_y_actualizar(ClienteRegistra);
    }

    @PostMapping("/registrarCliente")
    public void Registrar(@AuthenticationPrincipal UserDetailsImp user, @RequestBody Cliente ClienteRegistra){
        Usuario usuario = new Usuario(user.getUser().getCredencial(), null,
                user.getUser().getEstado(), user.getUser().getRol());
        Registra registro = new Registra(new RegistraId(usuario, ClienteRegistra));
        service.crear_y_actualizar(ClienteRegistra);
        service.crear_y_actualizar(registro);
    }

}
