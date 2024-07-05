package org.segundahacienda.segundahacienda.data;

import org.segundahacienda.segundahacienda.logic.Registra;
import org.segundahacienda.segundahacienda.logic.RegistraId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistraRepository extends CrudRepository<Registra, RegistraId> {
}
