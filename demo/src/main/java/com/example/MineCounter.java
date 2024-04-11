package com.example;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MineCounter extends Pane {
    private Label mineLabel;
    private IntegerProperty mineCount;

    public MineCounter() {
        mineCount = new SimpleIntegerProperty(App.mineCount);
        mineLabel = new Label();
        mineLabel.setFont(Font.font(24));
        mineLabel.setTextFill(Color.RED);
        mineLabel.setPrefSize(80, 40);
        mineLabel.setAlignment(Pos.CENTER);
        getChildren().add(mineLabel);
        setMaxSize(80, 40);
        setTranslateX(-341);
        setTranslateY(-273);

        setStyle("-fx-background-color: black; -fx-border-color: darkgray; -fx-border-width: 2px; -fx-padding: 5px;");

        mineLabel.textProperty().bind(Bindings.convert(mineCount));
    }

    public void setMineCount(int count) {
        mineCount.set(count);
    }
}
