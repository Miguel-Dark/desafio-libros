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
        // Usa la clase consumoAPI para traer el contenido crudo (JSON) desde la URL de Gutendex
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        // Toma el texto JSON y usa el conversor (Jackson) para transformarlo en objetos de la clase Datos
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                // Ordena los libros comparando el número de descargas (de mayor a menor gracias al reversed)
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                // Corta la lista para quedarse únicamente con los primeros 10 resultados
                .limit(10)
                // Transforma cada objeto libro para extraer solo su título y convertirlo a letras mayúsculas
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }
}
