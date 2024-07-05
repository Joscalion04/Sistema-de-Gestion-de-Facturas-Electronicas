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

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/perfil")
public class Perfil {

    @Autowired
    private Service service;

    @GetMapping("/{credencial}")
    public Proveedor proveedor(@PathVariable String credencial){
        return service.buscar_proveedor(credencial);
    }

    @GetMapping("/buscar")
    public List<Factura> read(@RequestParam String cred){
        List<Factura> lista = service.lista_facturas_proveedor(cred);

        for(Factura f : lista){
            f.setTotal(service.total_factura(f.getNum_factura()));
        }

        return lista;
    }

    @GetMapping("/current-user")
    public Usuario getCurrentUser(@AuthenticationPrincipal UserDetailsImp user) {
        return new Usuario(user.getUser().getCredencial(), null,
                user.getUser().getEstado(), user.getUser().getRol());
    }


    /*@GetMapping("/user")
    String view(Model model,  HttpSession httpSession){
        Proveedor proveedor = (Proveedor) httpSession.getAttribute("proveedor");

        Iterable<Factura> facturasIterable = service.lista_facturas_proveedor(proveedor.getUsuariosByIdentificador().getCredencial());
        List<Factura> facturasList = StreamSupport.stream(facturasIterable.spliterator(), false)
                .toList();

        for(Factura f : facturasIterable){
            f.setTotal(service.total_factura(f.getNum_factura()));
        }

        int totalFacturas = facturasList.size();

        List<Cliente> ultimoCliente = (List<Cliente>) service.lista_clientes_proveedor(proveedor.getIdentificador());
        Cliente uc;

        try{
            uc = ultimoCliente.getLast();
        }catch (Exception ex){
            uc = new Cliente();
        }

        model.addAttribute("totalFacturas", totalFacturas);
        model.addAttribute("ultimoCliente", uc);
        model.addAttribute("facturas", facturasIterable);
        model.addAttribute("datos_usuario", proveedor);
        return "/user/user";
    }
     */
    @GetMapping("/ultimo-cliente")
    Cliente listar_ultimo(@AuthenticationPrincipal UserDetailsImp user) {
        List<Cliente> ultimoCliente = (List<Cliente>) service.lista_clientes_proveedor(user.getUser().getCredencial());
        Cliente uc;

        try{
            uc = ultimoCliente.getLast();
        }catch (Exception ex){
            uc = new Cliente();
        }

        return uc;
    }

    @PostMapping("/actualizar-datos")
    void actualizar(@AuthenticationPrincipal UserDetailsImp user, @RequestBody Proveedor proveedor){
        if(user.getUser() != null && user.getProv() != null){
            proveedor.setUsuariosByIdentificador(user.getUser());
            service.crear_y_actualizar(proveedor);
        }
    }
}
