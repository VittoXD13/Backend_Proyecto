package com.example.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.ProductoRepository;

// Se ejecuta una vez al arrancar la API y carga el catálogo de ETB
// de Pobres TCG Store (el mismo de productos_tcg.html).
@Component
public class DatosIniciales implements CommandLineRunner {

    private static final String CATEGORIA = "ETB";
    private static final String RUTA = "image/productos_etb/";
    private static final int STOCK = 10; // el HTML no trae stock: cámbialo a gusto

    private final ProductoRepository repositorio;

    public DatosIniciales(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }
        repositorio.saveAll(List.of(
                new Producto("ETB ASCENDED HEROES", CATEGORIA, 80000, STOCK,
                        "ETB de la expansión Ascended Heroes protagonizada por MEGA DRAGONITE EX",
                        RUTA + "etb_acended_heroes.jpg"),
                new Producto("ETB CHAOS RISING", CATEGORIA, 75000, STOCK,
                        "ETB de la expansión Chaos Rising protagonizada por GRENINJA EX",
                        RUTA + "etb_chaos_rising.jpg"),
                new Producto("ETB PERFECT ORDER", CATEGORIA, 90000, STOCK,
                        "ETB de la expansión Perfect Order protagonizada por MEGA ZYGARDE EX",
                        RUTA + "etb_perfect_order.jpg"),
                new Producto("ETB DELTA REIGN", CATEGORIA, 95000, STOCK,
                        "ETB de la expansión Delta Reign protagonizada por MEGA RAYQUAZA EX",
                        RUTA + "etb_delta_reign.png"),
                new Producto("ETB PHANTASMAL FLAMES", CATEGORIA, 75000, STOCK,
                        "ETB de la expansión Phantasmal Flames protagonizada por MEGA CHARIZARD X EX",
                        RUTA + "etb_phanthasmal_flames.webp"),
                new Producto("ETB PITCH BLACK", CATEGORIA, 80000, STOCK,
                        "ETB de la expansión Pitch Black protagonizada por MEGA DARKRAI EX",
                        RUTA + "etb_pitch_black.webp")));
    }
}