package com.aluracursos.desafio_libros.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.aluracursos.desafio_libros.model.Datos;
import com.aluracursos.desafio_libros.model.DatosLibros;
import com.aluracursos.desafio_libros.service.ConsumoAPI;
import com.aluracursos.desafio_libros.service.ConvierteDatos;

import java.util.Comparator;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }
}
