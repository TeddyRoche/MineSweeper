package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class App extends Application {

    private Graph graph;
    public static int mineCount;
    public static boolean clickable;
    private static MineCounter mineCounter; 
    public static DigitalClock digitalClock;

    public static void updateMineCount(int count) {
        mineCount = count; 
    }


    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        graph = new Graph(16, 16);
        System.out.println("Adjacency List:");
        graph.printAdjacencyList();
        mineCounter = new MineCounter();
        clickable = true; 

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon); 

        // Load the FXML 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
        loader.setController(new GraphController(graph, mineCounter, digitalClock));
        Parent root = loader.load();

        root.setStyle("-fx-background-color: gray;");

        digitalClock = new DigitalClock();
        
        ((StackPane) root).getChildren().add(digitalClock);
        ((StackPane) root).getChildren().add(mineCounter);

        StackPane stackPane = new StackPane(root, digitalClock, mineCounter);

        Scene scene = new Scene(stackPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("MineSweeper"); 
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
