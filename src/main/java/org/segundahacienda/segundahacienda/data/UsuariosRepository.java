package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuario, String> {
    @Query(value = "SELECT * FROM Usuario u WHERE u.rol = :rol",nativeQuery = true)
    Iterable<Usuario> listar_usuarios(String rol);

    @Modifying
    @Query("UPDATE Usuario u SET u.estado_cuenta = 1 WHERE u.credencial = :id")
    void habilitar_cuenta(String id);

    @Modifying
    @Query("UPDATE Usuario u SET u.estado_cuenta = 0 WHERE u.credencial = :id")
    void deshabilitar_cuenta(String id);
}
