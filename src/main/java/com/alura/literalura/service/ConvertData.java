package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getDataToClass(String json, Class<T> clase) {
        try {
            // Deserialized Object -> Pasar de un json a clase
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    } // end getDataToClass()
} // end ConvertData class
