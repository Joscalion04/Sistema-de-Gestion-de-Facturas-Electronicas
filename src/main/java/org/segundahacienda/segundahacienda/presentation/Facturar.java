package org.segundahacienda.segundahacienda.presentation;

import jakarta.servlet.http.HttpSession;
import org.segundahacienda.segundahacienda.logic.*;
import org.segundahacienda.segundahacienda.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/facturar")
public class Facturar {

    @Autowired
    Service service;

    @PostMapping("/buscarCliente")
    public Cliente buscarCliente(@AuthenticationPrincipal UserDetailsImp user, @RequestBody Map<String, String> requestBody) {
        String identificacionCliente = requestBody.get("identificacionCliente");
        Usuario usuario = new Usuario(user.getUser().getCredencial(), null, user.getUser().getEstado(), user.getUser().getRol());
        return service.buscar_cliente(identificacionCliente, usuario.getCredencial());
    }

    @PostMapping("/buscarProducto")
    public Producto buscarProducto(@RequestBody Map<String, String> requestBody) {
        return service.buscar_producto(Integer.parseInt(requestBody.get("identificacionProducto")));
    }

    @PostMapping("/procesar")
    public void facturar(@AuthenticationPrincipal UserDetailsImp user, @RequestBody Map<String, Object> requestBody) {
        Usuario usuario = new Usuario(user.getUser().getCredencial(), null, user.getUser().getEstado(), user.getUser().getRol());
        String identificacionCliente = (String) requestBody.get("identificacionCliente");
        List<Map<String, Object>> carritoCompras = (List<Map<String, Object>>) requestBody.get("carritoCompras");
        List<Producto> CarritoConmpras = new ArrayList<>();
        for (Map<String, Object> productoMap : carritoCompras) {
            Producto producto = new Producto();
            producto.setJSON(productoMap);
            CarritoConmpras.add(producto);
        }
        Cliente ClienteAux = service.buscar_cliente(identificacionCliente, usuario.getCredencial());
        Calendar calendario = Calendar.getInstance(); Date fechaActual = calendario.getTime();
        Factura FacturaAux = new Factura();
        FacturaAux.setFecha_emision(new java.sql.Date(fechaActual.getTime()));
        FacturaAux.setFacturaByCliente(ClienteAux);
        service.guardar_factura(FacturaAux);
        for(Producto PAux : CarritoConmpras){
            Compone c = new Compone(new ComponeId(PAux, FacturaAux), PAux.getConteo());
            service.crear_y_actualizar(c);
        }
    }
}
