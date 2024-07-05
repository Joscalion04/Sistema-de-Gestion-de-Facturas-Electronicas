package org.segundahacienda.segundahacienda.logic;

import com.itextpdf.layout.element.Cell;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Compone {
    @EmbeddedId
    ComponeId id;

    @Basic
    @Column(name = "cantidad_producto")
    private int cantidadProducto;

    public Compone(ComponeId id, int cantidadProducto) {
        this.id = id;
        this.cantidadProducto = cantidadProducto;
    }

    public Compone() {
    }

    public ComponeId getId() {
        return id;
    }

    public void setId(ComponeId id) {
        this.id = id;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compone compone = (Compone) o;
        return cantidadProducto == compone.cantidadProducto && Objects.equals(id, compone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidadProducto);
    }
}
