package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DigitalClock extends Pane {
    private Label timeLabel;
    private IntegerProperty secondsProperty;
    private Timeline timeline;

    public DigitalClock() {
        createClockLayout();
        createTimeLabel();
        startClock();
    }

    private void createClockLayout() {
        Label background = new Label();
        background.setMaxSize(80, 40);
        setTranslateX(341);
        setTranslateY(-273);
        setStyle("-fx-background-color: black; -fx-border-color: darkgray; -fx-border-width: 2px; -fx-padding: 5px; -fx-max-width: 80; -fx-max-height: 40;");

        getChildren().add(background);
    }

    private void createTimeLabel() {
        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 24; -fx-text-fill: RED;");
        timeLabel.setMaxSize(80, 40);
        timeLabel.setTranslateX(14);
        timeLabel.setTranslateY(2);
        timeLabel.setAlignment(Pos.CENTER);
        getChildren().add(timeLabel);
    }

    private void startClock() {
        secondsProperty = new SimpleIntegerProperty(0);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> secondsProperty.set(secondsProperty.get() + 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE); 

        timeLabel.textProperty().bind(secondsProperty.asString("%04d"));
    }
    public void start(){
        timeline.play();

    }

    public void reset(){
        secondsProperty.set(0);
    }

    public void stopClock() {
        timeline.stop();
    }
}








