package com.example.Config;

public class ConfigReaderFactory {
    public ConfigReader createConfigReader(String fileType) {
        if ("ball".equalsIgnoreCase(fileType)) {
            return new BallConfigReader();
        }
        else if ("table".equalsIgnoreCase(fileType)) {
            return new TableConfigReader();
        }
        return null;
    }
}
