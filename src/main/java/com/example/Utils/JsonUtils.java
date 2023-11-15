package com.example.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.Entities.Vector2D;
import com.example.Entities.Vector3D;

import java.util.ArrayList;

public class JsonUtils {
    public static Vector2D getVector2D(JSONObject json, String key) {
        JSONObject vectorJson = json.getJSONObject(key);
        double x = vectorJson.getDouble("x");
        double y = vectorJson.getDouble("y");
        return new Vector2D(x, y);
    }

    public static Vector3D getVector3D(JSONObject json, String key) {
        JSONObject vectorJson = json.getJSONObject(key);
        double x = vectorJson.getDouble("x");
        double y = vectorJson.getDouble("y");
        double z = vectorJson.getDouble("z");
        return new Vector3D(x, y, z);
    }

    public static ArrayList<Vector2D> getVector2DArray(JSONObject json, String key) {
        ArrayList<Vector2D> vector2DArray = new ArrayList<>();
        JSONArray vectorJson = json.getJSONArray(key);
        for (int i = 0; i < vectorJson.length(); i++) {
            double x = vectorJson.getJSONObject(i).getDouble("x");
            double y = vectorJson.getJSONObject(i).getDouble("y");
            vector2DArray.add(new Vector2D(x, y));
        }
        return vector2DArray;
    }
}
