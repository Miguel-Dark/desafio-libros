package com.aluracursos.desafio_libros.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.aluracursos.desafio_libros.model.Datos;
import com.aluracursos.desafio_libros.model.DatosLibros;
import com.aluracursos.desafio_libros.service.ConsumoAPI;
import com.aluracursos.desafio_libros.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    // Lista para almacenar y mantener en memoria el historial de todos los libros encontrados durante la ejecución
    private List<DatosLibros> datosLibros = new ArrayList<>();

    public void muestraElMenu(){
        // Usa la clase consumoAPI para traer el contenido crudo (JSON) desde la URL de Gutendex
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        // Toma el texto JSON y usa el conversor (Jackson) para transformarlo en objetos de la clase Datos
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);

        // Recorre la lista de historial y muestra en consola cada título guardado en letras mayúsculas
        System.out.println("Tu historial de búsqueda");
        datosLibros.forEach(l -> System.out.println(l.titulo().toUpperCase()));

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

        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        // Captura la entrada del usuario desde el teclado y la guarda en una variable
        var tituloLibro = teclado.nextLine();
        // Llama a la API concatenando el nombre buscado, reemplazando espacios por "+" para que la URL sea válida
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ", "+"));
        // Convierte el nuevo JSON recibido en nuestra estructura de objetos Datos
        var datosBuqueda = conversor.obtenerDatos(json, Datos.class);
        // Usa Streams para filtrar la lista y busca el primer libro que coincida con el título (usando Optional para evitar errores si no hay nada)
        Optional<DatosLibros> libroBuscado = datosBuqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        // Verifica si el contenedor Optional tiene un valor adentro antes de intentar mostrarlo
        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado ");
            // 1. Guardamos el libro que encontramos en una variable
            var libro = libroBuscado.get();

            // 2. ¡Lo agregamos a nuestra lista de historial!
            datosLibros.add(libro);
            System.out.println(libro);

            // Extrae y muestra el objeto libro que está guardado dentro del Optional
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }
    }
}
