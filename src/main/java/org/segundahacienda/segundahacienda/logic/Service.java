package org.segundahacienda.segundahacienda.logic;

import org.segundahacienda.segundahacienda.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Autowired
    private FacturasRepository facturasRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private RegistraRepository registraRepository;

    @Autowired
    private ComponeRepository componeRepository;

    // ------------------- USUARIOS --------------------------

    public Iterable<Usuario> listar_usuarios () {
        return usuariosRepository.listar_usuarios("CLI");
    }

    public void registro_actualizacion (Usuario usuario){
        // Encripta la contraseña
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenhaEncriptada = encoder.encode(usuario.getContrasenha());
        usuario.setContrasenha(contrasenhaEncriptada);

        usuariosRepository.save(usuario);
        proveedoresRepository.crear_cuenta_proveedor(usuario.getCredencial());
    }

    public Usuario buscar_usuario (String credencial){
        return usuariosRepository.findById(credencial).orElse(null);
    }

    public void eliminar_usuario (String codigo){
        usuariosRepository.deleteById(codigo);
    }

    public void habilitar_proveedor(String identificador){
        usuariosRepository.habilitar_cuenta(identificador);

    }

    public void deshabilitar_proveedor(String identificador){
        usuariosRepository.deshabilitar_cuenta(identificador);
    }

    // ----------------- PROVEEDORES ------------------------

    public Proveedor buscar_proveedor (String identificador){
        Proveedor proveedor = proveedoresRepository.findById(identificador).orElse(null);
        return proveedor;
    }

    public void crear_y_actualizar(Proveedor proveedor) { proveedoresRepository.save(proveedor); }

    // ----------------- PRODUCTOS -------------------------

    public void crear_y_actualizar(Producto producto){
        productosRepository.save(producto);
    }

    public Producto buscar_producto(int codigo){
        return productosRepository.findById(codigo).orElse(null);
    }

    public boolean existe_producto(int codigo){
        return buscar_producto(codigo) != null;
    }

    public List<Producto> listar_productos_proveedor(String id){
        return productosRepository.listar_productos_proveedor(id);
    }

    public Producto buscar_producto_especifico(String id, int codigo){
        return productosRepository.encontrar_producto_proveedor(id, codigo);
    }

    public Iterable<Producto> buscar_productos_factura(int num_factura) { return productosRepository.productos_factura(num_factura); }

    // ----------------- FACTURAS   ------------------------

    public Iterable<Factura> listar_facturas() { return facturasRepository.findAll(); }

    public List<Factura> lista_facturas_proveedor(String id) { return facturasRepository.encontrar_facturas(id); }

    public void guardar_factura(Factura Aux){ facturasRepository.save(Aux);}

    public void crear_y_actualizar(Compone compone) { componeRepository.save(compone); }

    public void guardarCompone(int cantidadProducto, int codigoProducto, int numFactura){componeRepository.insertarCompone(cantidadProducto,codigoProducto,numFactura);}

    @Transactional
    public void actualizaFactura(String id){facturasRepository.actualizarIdentificacionCliente(id);}

    public double total_factura(int id) {
        try{
            return facturasRepository.total_factura(id);
        } catch (Exception ex){
            return 0;
        }
    }

    public Factura buscar_factura(int num_factura) { return facturasRepository.buscar_factura(num_factura); }

    public List<Factura> buscar_factura_especifica(int num_factura, String id) { return facturasRepository.buscar_factura_especifica(num_factura,id);}

    // ----------------- CLIENTES   ------------------------

    public Iterable<Cliente> lista_clientes_proveedor(String id) { return clientesRepository.encontrar_clientes_proveedor(id); }

    public Cliente buscar_cliente(String idCliente, String idProveedor) { return clientesRepository.encontrar_Cliente(idCliente,idProveedor); }

    public void crear_y_actualizar(Cliente cliente) { clientesRepository.save(cliente); }

    // ----------------- REGISTRAR   ------------------------

    public void crear_y_actualizar(Registra registrar) { registraRepository.save(registrar); }

    // ----------------- COMPONE   ------------------------

    public Compone retorna_compone(int factura){
        return componeRepository.retornarCompone(factura);
    }


}
