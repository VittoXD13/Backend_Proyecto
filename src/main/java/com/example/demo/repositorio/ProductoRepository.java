package com.example.demo.repositorio;

import com.example.demo.modelo.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Spring Data arma la consulta a partir del nombre del método:
    // SELECT * FROM producto WHERE categoria = ? ORDER BY id
    List<Producto> findByCategoriaOrderById(String categoria);
}
