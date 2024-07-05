package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Proveedor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProveedoresRepository extends CrudRepository<Proveedor, String> {
    @Modifying
    @Transactional
    @Query(value = "insert into proveedor (identificador) value (:id)", nativeQuery = true)
    void crear_cuenta_proveedor(String id);

    @Query(value = "select * from Proveedor where identificador = :id", nativeQuery = true)
    Proveedor encontrar_proveedor(String id);

}
