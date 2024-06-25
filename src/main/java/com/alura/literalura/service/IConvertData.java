package com.alura.literalura.service;

public interface IConvertData {

    <T> T getDataToClass(String json, Class<T> clase );
}
