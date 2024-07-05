package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Factura;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturasRepository extends CrudRepository<Factura, String> {

    @Query(value = "select * from factura f where f.num_factura = :num_factura", nativeQuery = true)
    Factura buscar_factura(int num_factura);

    @Query(value = "select f.identificacion_cliente, f.num_factura, f.fecha_emision \n" +
            "from factura f, cliente c, registra r, proveedor p \n" +
            "            where f.identificacion_cliente = c.identificacion\n" +
            "            and c.identificacion = r.identificador_cliente\n" +
            "            and r.identificador_proveedor = p.identificador\n" +
            "            and p.identificador =  :id and f.num_factura = :num_factura", nativeQuery = true)
    List<Factura> buscar_factura_especifica(int num_factura, String id);

    @Query(value = "select f.identificacion_cliente, f.num_factura, f.fecha_emision \n" +
            "from factura f, cliente c, registra r, proveedor p \n" +
            "where f.identificacion_cliente = c.identificacion\n" +
            "and c.identificacion = r.identificador_cliente\n" +
            "and r.identificador_proveedor = p.identificador\n" +
            "and p.identificador =  :id", nativeQuery = true)
    List<Factura> encontrar_facturas(String id);


    @Query(value = "select sum((p.precio * c.cantidad_producto) + (p.precio * c.cantidad_producto) * 0.13)\n" +
            "from producto p, factura f, compone c \n" +
            "where p.codigo_producto = c.codigo_producto\n" +
            "and c.num_factura = f.num_factura\n" +
            "and f.num_factura = :num_factura", nativeQuery = true)
    double total_factura(int num_factura);

    @Modifying
    @Query(value ="UPDATE Factura f SET f.identificacion_cliente = :nuevaIdentificacion WHERE f.identificacion_cliente IS NULL", nativeQuery = true)
    void actualizarIdentificacionCliente(String nuevaIdentificacion);
}
