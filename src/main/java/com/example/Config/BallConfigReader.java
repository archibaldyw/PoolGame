package com.example.Config;
import com.example.Entities.BallConfig;
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
            ballconfig.setCueBallColor(JsonUtils.getVector3D(json,"cueBallColor"));
            ballconfig.setCueBallPos(JsonUtils.getVector2D(json,"cueBallPos"));
            ballconfig.setMass(json.getDouble("mass"));
            ballconfig.setBlueBallPos(JsonUtils.getVector2DArray(json,"blueBallPos"));
            ballconfig.setRedBallPos(JsonUtils.getVector2DArray(json,"redBallPos"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ballconfig;
    }
}
