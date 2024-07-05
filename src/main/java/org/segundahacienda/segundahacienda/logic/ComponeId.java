package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ComponeId {
    @ManyToOne
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto")
    private Producto productoByCompone;

    @ManyToOne
    @JoinColumn(name = "num_factura", referencedColumnName = "num_factura")
    private Factura facturaByCompone;

    public ComponeId(Producto productoByCompone, Factura facturaByCompone) {
        this.productoByCompone = productoByCompone;
        this.facturaByCompone = facturaByCompone;
    }

    public ComponeId() {

    }

    public Producto getProductoByCompone() {
        return productoByCompone;
    }

    public void setProductoByCompone(Producto productoByCompone) {
        this.productoByCompone = productoByCompone;
    }

    public Factura getFacturaByCompone() {
        return facturaByCompone;
    }

    public void setFacturaByCompone(Factura facturaByCompone) {
        this.facturaByCompone = facturaByCompone;
    }
}
