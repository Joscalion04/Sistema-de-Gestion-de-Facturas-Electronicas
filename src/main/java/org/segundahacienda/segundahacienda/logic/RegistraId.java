package org.segundahacienda.segundahacienda.logic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class RegistraId {
    @ManyToOne
    @JoinColumn(name = "identificador_proveedor", referencedColumnName = "credencial")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "identificador_cliente", referencedColumnName = "identificacion")
    private Cliente cliente;

    public RegistraId(Usuario usuario, Cliente cliente) {
        this.usuario = usuario;
        this.cliente = cliente;
    }

    public RegistraId() {

    }
}
