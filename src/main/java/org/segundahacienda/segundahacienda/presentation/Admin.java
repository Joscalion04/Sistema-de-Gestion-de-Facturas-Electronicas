package org.segundahacienda.segundahacienda.presentation;

import org.segundahacienda.segundahacienda.logic.Service;
import org.segundahacienda.segundahacienda.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class Admin {
    @Autowired
    Service service;

    @GetMapping("/listado")
    public List<Usuario> listado() {
        return (List<Usuario>) service.listar_usuarios();
    }

    @Transactional
    @GetMapping("/approve")
    public void change_status(@RequestParam String id){
        Usuario usuario = service.buscar_usuario(id);
        if(usuario.getEstado()==0){
            service.habilitar_proveedor(id);
        }else{
            service.deshabilitar_proveedor(id);
        }
    }
}
