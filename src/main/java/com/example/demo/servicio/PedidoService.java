package com.example.demo.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LineaPedido;
import com.example.demo.dto.PedidoRequest;
import com.example.demo.error.RecursoNoEncontradoException;
import com.example.demo.error.StockInsuficienteException;
import com.example.demo.modelo.Pedido;
import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.PedidoRepository;
import com.example.demo.repositorio.ProductoRepository;

@Service
public class PedidoService {

    private final ProductoRepository productos;
    private final PedidoRepository pedidos;

    public PedidoService(ProductoRepository productos, PedidoRepository pedidos) {
        this.productos = productos;
        this.pedidos = pedidos;
    }

    // Devuelve una lista: un Pedido por cada línea del carrito.
    // @Transactional: si una línea falla, se deshacen todas.
    @Transactional
    public List<Pedido> crear(PedidoRequest solicitud) {
        List<Pedido> creados = new ArrayList<>();

        for (LineaPedido linea : solicitud.lineas()) {
            Producto producto = productos.findById(linea.productoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe el producto " + linea.productoId()));

            if (producto.getStock() < linea.cantidad()) {
                throw new StockInsuficienteException("No hay stock suficiente de «" + producto.getNombre()
                        + "»: quedan " + producto.getStock() + " unidades.");
            }

            producto.setStock(producto.getStock() - linea.cantidad());

            // El constructor pone la fecha y calcula total = precio * unidades.
            creados.add(pedidos.save(new Pedido(producto, linea.cantidad())));
        }

        return creados;
    }
}