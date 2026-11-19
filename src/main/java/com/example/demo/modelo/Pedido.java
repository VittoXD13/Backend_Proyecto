
package com.example.demo.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Registro de cada compra: qué producto (ETB), cuándo, cuántas unidades y cuánto se cobró.
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Producto comprado (uno de los ETB de la tienda).
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private LocalDateTime fecha;
    private Integer unidades;

    // Se guarda el total cobrado en ese momento, así no cambia si el precio del producto sube después.
    private Integer total;

    public Pedido() {
    }

    // El total se calcula con el precio actual del producto.
    public Pedido(Producto producto, Integer unidades) {
        this.producto = producto;
        this.fecha = LocalDateTime.now();
        this.unidades = unidades;
        this.total = producto.getPrecio() * unidades;
    }

    public Pedido(Producto producto, LocalDateTime fecha, Integer unidades, Integer total) {
        this.producto = producto;
        this.fecha = fecha;
        this.unidades = unidades;
        this.total = total;
    }

    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public LocalDateTime getFecha() { return fecha; }
    public Integer getUnidades() { return unidades; }
    public Integer getTotal() { return total; }
}

