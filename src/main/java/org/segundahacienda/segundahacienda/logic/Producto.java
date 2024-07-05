package org.segundahacienda.segundahacienda.logic;

import jakarta.persistence.*;

import java.util.Map;
import java.util.Objects;

@Entity
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo_producto")
    private int codigoProducto;
    @Basic
    @Column(name = "identificador_proveedor")
    private String identificadorProveedor;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "precio")
    private double precio;

    @Transient
    private int conteo = 1;

    public void rowcount(){conteo++;}

    public void downcount(){ conteo--;}

    public int getConteo() {
        return conteo;
    }

    public void setConteo(int conteo) {
        this.conteo = conteo;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getIdentificadorProveedor() {
        return identificadorProveedor;
    }

    public void setIdentificadorProveedor(String identificadorProveedor) {
        this.identificadorProveedor = identificadorProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return codigoProducto == producto.codigoProducto && Double.compare(precio, producto.precio) == 0 && Objects.equals(identificadorProveedor, producto.identificadorProveedor) && Objects.equals(descripcion, producto.descripcion);
    }

    public float Monto(){
        float impuesto = (float) (precio * 0.16);
        float subtotal = (float) (precio + impuesto);
        return subtotal*conteo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoProducto, identificadorProveedor, descripcion, precio);
    }

    public void setJSON(Map<String, Object> productoMap) {
        this.codigoProducto = ((Integer) productoMap.get("codigoProducto"));
        this.conteo = ((Integer) productoMap.get("conteo"));
        this.descripcion = ((String) productoMap.get("descripcion"));
        try{
            this.precio = Double.valueOf((Integer) productoMap.get("precio"));
        }catch (Exception e){
            this.precio = ((Integer) productoMap.get("precio"));
        }
        this.identificadorProveedor = ((String) productoMap.get("IdentificadorProveedor"));
    }
}
