package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Proveedor {
    @Id
    @Column(name = "identificador")
    String identificador;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "telefono")
    private String telefono;

    @OneToOne
    @JoinColumn(name = "identificador", referencedColumnName = "credencial")
    private Usuario usuariosByIdentificador;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedor that = (Proveedor) o;
        return Objects.equals(usuariosByIdentificador.getCredencial(), that.usuariosByIdentificador.getCredencial()) && Objects.equals(nombre, that.nombre) && Objects.equals(correo, that.correo) && Objects.equals(telefono, that.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuariosByIdentificador.getCredencial(), nombre, correo, telefono);
    }

    public Usuario getUsuariosByIdentificador() {
        return usuariosByIdentificador;
    }

    public void setUsuariosByIdentificador(Usuario usuariosByIdentificador) {
        this.usuariosByIdentificador = usuariosByIdentificador;
    }
}
