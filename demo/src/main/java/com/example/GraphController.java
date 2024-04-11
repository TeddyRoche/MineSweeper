package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {

    @FXML
    private StackPane stackPane;
    private GridPane gridPane = new GridPane();
    private Button easy = new Button("Easy");
    private Button medium = new Button("Medium");
    private Button hard = new Button("Hard");
    private VBox buttonBox = new VBox();
    private VertexView[][] vertexViews;
    
    @SuppressWarnings("unused")
    private Graph graph;
    private MineCounter mineCounter;
    @SuppressWarnings("unused")
    private DigitalClock digitalClock;


    public GraphController(Graph graph, MineCounter mineCounter, DigitalClock digitalClock) {
        this.graph = graph;
        this.mineCounter = mineCounter; 
        this.digitalClock = digitalClock;
    }

    EventHandler<ActionEvent> easyEventHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            int rows = 16;
            int cols = 16; 
            App.mineCount = 60;
            updateMineCount(App.mineCount);
            updateMineCount(App.mineCount);
            refreshMineCounter();
            App.digitalClock.reset();
            updateGrid(rows, cols);
            App.clickable = true;
        }
    };

    EventHandler<ActionEvent> mediumEventHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            int rows = 16;
            int cols = 24; 
            App.mineCount = 125;
            updateMineCount(App.mineCount);
            refreshMineCounter();
            App.digitalClock.reset();
            updateGrid(rows, cols);
            App.clickable = true;
        }
    };

    EventHandler<ActionEvent> hardEventHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            int rows = 24;
            int cols = 34; 
            App.mineCount = 300;
            updateMineCount(App.mineCount);
            updateGrid(rows, cols);
            refreshMineCounter();
            App.digitalClock.reset();
            gridPane.setTranslateY(20);
            App.clickable = true;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonBox.getChildren().addAll(easy, medium, hard);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setTranslateY(-310);
        buttonBox.setMaxWidth(200);
        buttonBox.setMaxHeight(5);


        easy.setMinSize(50, 40);
        easy.setTranslateX(-70);
        easy.setTranslateY(75);
        easy.setOnAction(easyEventHandler);

        medium.setMinSize(50, 40);
        medium.setTranslateX(0);
        medium.setTranslateY(35);
        medium.setOnAction(mediumEventHandler);

        hard.setMinSize(50, 40);
        hard.setTranslateX(70);
        hard.setTranslateY(-5);
        hard.setOnAction(hardEventHandler);
        stackPane.getChildren().add(buttonBox);
    }

    private void updateGrid(int rows, int cols) {          
        stackPane.getChildren().removeAll(gridPane, buttonBox);
    
        gridPane.getChildren().clear();


        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(rows, cols, App.mineCount);

        vertexViews = new VertexView[rows][cols];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean isMine = minesweeperBoard.isMineAt(i, j);
                VertexView vertexView = new VertexView(i, j, rows, cols, minesweeperBoard, vertexViews, isMine, mineCounter); 
                vertexViews[i][j] = vertexView;
                gridPane.add(vertexView, j, i);
            }
        }
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        stackPane.getChildren().addAll(gridPane, buttonBox);
    }

    private void updateMineCount(int count) {
        App.updateMineCount(count);
    }

    private void refreshMineCounter() {
        mineCounter.setMineCount(App.mineCount);
    }
}

