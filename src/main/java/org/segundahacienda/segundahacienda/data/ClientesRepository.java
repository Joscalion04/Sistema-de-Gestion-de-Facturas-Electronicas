package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends CrudRepository<Cliente, String> {
    @Query(value = "select c.identificacion, c.nombre, c.correo, c.telefono \n" +
            "from cliente c, registra r, proveedor p \n" +
            "where c.identificacion = r.identificador_cliente\n" +
            "and r.identificador_proveedor = p.identificador\n" +
            "and p.identificador = :id", nativeQuery = true)
    Iterable<Cliente> encontrar_clientes_proveedor(String id);

    @Query(value = "select * from cliente c where cliente c.identificacion = :id", nativeQuery = true)
    Cliente encontrar_cliente(String id);

    @Query(value = "SELECT * FROM Cliente WHERE identificacion IN "
            + "(SELECT identificador_cliente FROM Registra WHERE identificador_proveedor = "
            + "(SELECT identificador FROM Proveedor WHERE identificador = :idProveedor)) "
            + "AND identificacion = :idCliente", nativeQuery = true)
    Cliente encontrar_Cliente(String idCliente, String idProveedor);


}
