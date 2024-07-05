package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class Usuario {
    @Id
    @NotNull
    @Column(name = "credencial")
    private String credencial;
    @Basic
    @NotNull
    @Column(name = "contrasenha")
    private String contrasenha;
    @Basic
    @Column(name = "estado_cuenta")
    private int estado_cuenta;
    @Basic
    @Column(name = "rol")
    private String rol;

    public Usuario() {}

    public Usuario(String credencial, String contrasenha, int estado_cuenta, String rol) {
        this.credencial = credencial;
        this.contrasenha = contrasenha;
        this.estado_cuenta = estado_cuenta;
        this.rol = rol;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String usuario) {
        this.credencial = usuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public int getEstado(){return estado_cuenta;}

    public void setEstado(int estado){this.estado_cuenta = estado;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuarios = (Usuario) o;
        return Objects.equals(credencial, usuarios.credencial) && Objects.equals(contrasenha, usuarios.contrasenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credencial, contrasenha, rol);
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String estatus_actual(int estado){
        return (estado == 0) ? "Deshabilitada" : "Habilitada";

    }
}
