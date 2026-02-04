package com.aluracursos.desafio_libros.principal;

import com.aluracursos.desafio_libros.model.Datos;
import com.aluracursos.desafio_libros.service.ConsumoAPI;
import com.aluracursos.desafio_libros.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);
    }
}
