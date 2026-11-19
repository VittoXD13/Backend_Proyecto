package com.example.demo.servicio;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.error.RecursoNoEncontradoException;
import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    // Inyección por constructor: hace lo mismo que @Autowired sobre el campo,
    // pero deja el campo como final y es lo que recomienda Spring.
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Producto> listar(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return repositorio.findAll(Sort.by("id"));
        }
        return repositorio.findByCategoriaOrderById(categoria);
    }

    // En vez de orElse(null) —que terminaría en un 200 vacío—, lanzamos una
    // excepción que el manejador convierte en 404.
    public Producto buscar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el producto " + id));
    }

    public Producto crear(Producto producto) {
        // Si el cliente manda un id, lo ignoramos: así un POST nunca sobrescribe.
        producto.setId(null);
        return repositorio.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto actual = buscar(id);
        actual.setNombre(datos.getNombre());
        actual.setCategoria(datos.getCategoria());
        actual.setPrecio(datos.getPrecio());
        actual.setStock(datos.getStock());
        actual.setDescripcion(datos.getDescripcion());
        actual.setImagen(datos.getImagen());
        return repositorio.save(actual);
    }

    public void eliminar(Long id) {
        repositorio.delete(buscar(id));
    }
}
