package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cliente {
    @Id
    @Basic
    @Column(name = "identificacion")
    private String identificacion;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "correo")
    private String Correo;

    @Basic
    @Column(name = "telefono")
    private String telefono;

    @Transient
    boolean modoEdicion = false;

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void cambiarModoEdicion() {
        modoEdicion = !modoEdicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente clientes = (Cliente) o;
        return Objects.equals(identificacion, clientes.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion, nombre, Correo, telefono);
    }
}
