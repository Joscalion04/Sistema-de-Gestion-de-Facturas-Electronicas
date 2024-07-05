package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Registra {

    @EmbeddedId
    private RegistraId id;

    public Registra(RegistraId id) {
        this.id = id;
    }

    public Registra() {

    }


    // Constructors, getters, and setters
}
