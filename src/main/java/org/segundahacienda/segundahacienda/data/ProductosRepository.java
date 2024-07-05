package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends CrudRepository<Producto, Integer> {
    @Query(value = "select * from Producto p where p.identificador_proveedor = :id", nativeQuery = true)
    List<Producto> listar_productos_proveedor(String id);

    @Query(value = "select * from Producto p where p.identificador_proveedor = :id and p.codigo_producto = :codigo", nativeQuery = true)
    Producto encontrar_producto_proveedor(String id, int codigo);

    @Query( value = "select p.* \n" +
            "from factura f, compone c, producto p\n" +
            "where p.codigo_producto = c.codigo_producto\n" +
            "and c.num_factura = f.num_factura\n" +
            "and f.num_factura = :num_factura", nativeQuery = true)
    Iterable<Producto> productos_factura(int num_factura);
}
