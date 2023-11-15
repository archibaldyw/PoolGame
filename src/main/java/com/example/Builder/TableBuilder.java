package com.example.Builder;

import com.example.Entities.Vector2D;
import javafx.scene.Group;

import com.example.Entities.TableConfig;
import com.example.Entities.Table;
import javafx.scene.shape.Circle;


public class TableBuilder {

    private TableConfig tableConfig;    // 读取的配置
    public TableBuilder(TableConfig tableconfig) {
        this.tableConfig = tableconfig;
    }
    public Table initTable(Group root) {
        Table table = new Table();
        table.setSize(tableConfig.getSize());
        table.setColor(tableConfig.getColor());
        table.setFriction(tableConfig.getFriction());
        root.getChildren().add(table.getCanvas());
        double width = table.getSize().x;
        double height = table.getSize().y;
        double holeRadius = 30;
        double offset = 6;
        Vector2D pocketPos[] = {
                new Vector2D(offset*2,offset*2),
                new Vector2D(offset*2, height-offset*2),
                new Vector2D(width/2, offset),
                new Vector2D(width/2, height-offset),
                new Vector2D(width-offset*2, offset*2),
                new Vector2D(width-offset*2, height-offset*2)
        };
        for(int i=0; i<6; i++) {
            Vector2D pos = pocketPos[i];
            Circle hole = new Circle(pos.x, pos.y, holeRadius);
            table.getPockets().add(hole);
            root.getChildren().add(hole);
        }
        return table;
    }
}
