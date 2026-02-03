package com.aluracursos.desafio_libros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // MÉTODO IMPLEMENTADO: Ejecuta la conversión real usando tipos genéricos
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            // MANEJO DE EXCEPCIÓN: Captura errores de procesamiento de JSON
            throw new RuntimeException(e);
        }
    }
}
