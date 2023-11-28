package com.example.Config;
import com.example.Entities.BallConfig;
import com.example.Entities.Vector2D;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.Utils.JsonUtils;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BallConfigReader implements ConfigReader<BallConfig> {

    public BallConfig readConfig(String filePath) {
        BallConfig ballconfig = new BallConfig();
        try{
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject json = new JSONObject(content);
            ballconfig.setMass(json.getDouble("mass"));
            JSONArray ballsJson = json.getJSONArray("Balls");
            for(int i=0; i<ballsJson.length(); i++) {
                JSONObject ballJson = ballsJson.getJSONObject(i);
                ballconfig.getColor().add(ballJson.getString("color"));
                ballconfig.getPosition().add(new Vector2D(ballJson.getDouble("x"), ballJson.getDouble("y")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ballconfig;
    }
}
