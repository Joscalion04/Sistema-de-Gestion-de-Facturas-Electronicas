package org.segundahacienda.segundahacienda.presentation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.segundahacienda.segundahacienda.logic.Producto;
import org.segundahacienda.segundahacienda.logic.Service;
import org.segundahacienda.segundahacienda.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class Productos {
    @Autowired
    private Service service;

    @GetMapping("/producto")
    public List<Producto> read(@RequestParam String cred){return service.listar_productos_proveedor(cred);}

    @PostMapping("/agregar")
    public void add(@RequestBody Producto producto){
        try {
            service.crear_y_actualizar(producto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/verify")
    public boolean verify(@RequestParam("cod") int cod) { return service.existe_producto(cod);}

    @GetMapping("/buscar")
    public Producto obtain(@RequestParam("cod") int cod, @RequestParam("cred") String cred) { return service.buscar_producto_especifico(cred, cod);}
}

    /*@GetMapping("/productos")
    public String show(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Producto producto = new Producto();
        producto.setIdentificadorProveedor(usuario.getCredencial());
        model.addAttribute("buscador", new Producto());
        model.addAttribute("producto", producto);
        model.addAttribute("productos_proveedor", service.listar_productos_proveedor(usuario.getCredencial()));
        return "/productos/productos";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable("codigo") int codigo, Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Producto producto = service.buscar_producto((int)codigo);
        model.addAttribute("buscador", new Producto());
        model.addAttribute("producto", producto);
        model.addAttribute("productos_proveedor", service.listar_productos_proveedor(usuario.getCredencial()));
        return "/productos/productos";
    }

    @GetMapping("/productos/buscar")
    public String editar(@ModelAttribute Producto producto_buscado, Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Producto producto = service.buscar_producto_especifico(usuario.getCredencial(), producto_buscado.getCodigoProducto());

        if(producto == null){
            producto = new Producto();
        }

        model.addAttribute("buscador", new Producto());
        model.addAttribute("producto", producto);
        model.addAttribute("productos_proveedor", service.listar_productos_proveedor(usuario.getCredencial()));
        return "/productos/productos";
    }

    @PostMapping("/productos/actualizar")
    public String actualizar(@Valid @ModelAttribute Producto producto, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            model.addAttribute("buscador", new Producto());
            model.addAttribute("producto", producto);
            model.addAttribute("productos_proveedor", service.listar_productos_proveedor(usuario.getCredencial()));
            return "/productos/productos";
        }
        service.crear_y_actualizar(producto);
        return "redirect:/productos";
    }
}*/
