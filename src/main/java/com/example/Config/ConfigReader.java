package com.example.Config;

public interface ConfigReader<T> {
    T readConfig(String filePath);
}
