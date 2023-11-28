package com.example.Config;

import org.json.JSONObject;

import com.example.Utils.JsonUtils;
import com.example.Entities.TableConfig;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TableConfigReader implements ConfigReader<TableConfig> {

    public TableConfig readConfig(String filePath) {
        TableConfig tableConfig = new TableConfig();
        try{
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject json = new JSONObject(content);
            tableConfig.setColor(JsonUtils.getVector3D(json,"color"));
            tableConfig.setSize(JsonUtils.getVector2D(json,"size"));
            tableConfig.setFriction(json.getDouble("friction"));
            tableConfig.setPocketSize(json.getDouble("pocketSize"));
            tableConfig.setPocketNum(json.getInt("pocketNum"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConfig;
    }
}
