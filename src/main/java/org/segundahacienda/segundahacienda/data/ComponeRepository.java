package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Compone;
import org.segundahacienda.segundahacienda.logic.ComponeId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponeRepository extends CrudRepository<Compone, ComponeId> {

    @Modifying
    @Query(value = "INSERT INTO Compone (cantidad_producto, codigo_producto, num_factura) VALUES (:cantidadProducto, :codigoProducto, :numFactura)", nativeQuery = true)
    void insertarCompone(int cantidadProducto, int codigoProducto, int numFactura);

    @Query(value = "SELECT * FROM compone c WHERE c.num_factura = :factura", nativeQuery = true)
    Compone retornarCompone(int factura);
}
