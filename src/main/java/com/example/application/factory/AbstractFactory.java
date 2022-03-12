package com.example.application.factory;

public interface AbstractFactory<T> {
    T create(String type);
}
