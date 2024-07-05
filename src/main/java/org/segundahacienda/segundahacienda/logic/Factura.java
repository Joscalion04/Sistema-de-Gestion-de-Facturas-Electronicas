package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_factura")
    private int num_factura;

    @Basic
    @Column(name = "fecha_emision")
    private Date fecha_emision;

    @ManyToOne
    @JoinColumn(name = "identificacion_cliente", referencedColumnName = "identificacion")
    private Cliente facturaByCliente;

    @Transient
    private double total;


    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura that = (Factura) o;
        return Objects.equals(num_factura, that.num_factura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_factura, fecha_emision);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getFacturaByCliente() {
        return facturaByCliente;
    }

    public void setFacturaByCliente(Cliente facturaByCliente) {
        this.facturaByCliente = facturaByCliente;
    }
}
