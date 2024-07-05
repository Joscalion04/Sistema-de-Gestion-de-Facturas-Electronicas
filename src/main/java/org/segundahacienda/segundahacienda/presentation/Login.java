package org.segundahacienda.segundahacienda.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.segundahacienda.segundahacienda.logic.Proveedor;
import org.segundahacienda.segundahacienda.logic.Service;
import org.segundahacienda.segundahacienda.logic.Usuario;
import org.segundahacienda.segundahacienda.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/login")
public class Login {
    @Autowired
    private Service service;

    @PostMapping("/login")
    public Usuario login(@RequestBody @Valid Usuario usuario, HttpServletRequest request){
        Usuario usuarioBD = service.buscar_usuario(usuario.getCredencial());
        if(usuarioBD == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Usuario no encontrado.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(usuario.getContrasenha(), usuarioBD.getContrasenha())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas. Vuelve a intentarlo.");
        }

        if(usuarioBD.getEstado() == 0 && usuarioBD.getRol().equals("CLI")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "La cuenta no ha sido aprobada por un administrador. Póngase en contacto con soporte técnico.");
        }

        try{
            request.login(usuario.getCredencial(), usuario.getContrasenha());
        } catch (ServletException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Error al momento de hacer inicio de sesion. Vuelve a intentarlo.");
        }

        Authentication auth = (Authentication) request.getUserPrincipal();
        Usuario user = ((UserDetailsImp) auth.getPrincipal()).getUser();
        return new Usuario(user.getCredencial(), null, user.getEstado(), user.getRol());
    }

    @PostMapping("/signup")
    public void signup(@RequestBody Usuario usuario){
        try {
            service.registro_actualizacion(usuario);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        try{
            request.logout();
        } catch (ServletException ignored){}
    }

    @GetMapping("/current-session-user")
    public Usuario getCurrentUser(@AuthenticationPrincipal UserDetailsImp user) {
        return new Usuario(user.getUser().getCredencial(), null,
                user.getUser().getEstado(), user.getUser().getRol());
    }

    @GetMapping("/current-session-prov")
    public Proveedor getCurrentProv(@AuthenticationPrincipal UserDetailsImp user) {
        return service.buscar_proveedor(user.getUser().getCredencial());
    }
}