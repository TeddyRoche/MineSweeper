package com.example;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VertexView extends Group {

    private static final double SIDE_LENGTH = 20;
    private Rectangle rectangle;
    private Label numberText;

    private boolean isMine;
    private boolean isRevealed;
    private boolean mineClicked = false;

    private int row;
    private int col;
    private int numRows;
    private int numCols;
    private MinesweeperBoard minesweeperBoard;
    private VertexView[][] vertexViews;
    private MineCounter mineCounter;
    @SuppressWarnings("unused")
    private Graph graph;


    public boolean isRevealed() {
        return isRevealed;
    }

    public VertexView(int row, int col, int numRows, int numCols, MinesweeperBoard minesweeperBoard, VertexView[][] vertexViews, boolean isMine, MineCounter mineCounter) {
        this.row = row;
        this.col = col;
        this.numRows = numRows;
        this.numCols = numCols;
        this.mineCounter = mineCounter; 
        this.minesweeperBoard = minesweeperBoard;
        this.vertexViews = vertexViews;
        this.isMine = isMine;
        this.isRevealed = false;

        rectangle = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setStrokeWidth(2);

        numberText = new Label();
        numberText.setFont(Font.font(14));
        numberText.setTextFill(Color.BLACK);
        numberText.setVisible(false);
        numberText.setLayoutX(6);
        numberText.setLayoutY(0);
            rectangle.setOnMouseClicked(event -> {
                if(!mineClicked) {
                    if (event.getButton() == MouseButton.PRIMARY) { 
                        handleLeftClick();
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        handleRightClick();
                    }
                }
            }); 
        


        getChildren().addAll(rectangle, numberText);
    }

    private void handleLeftClick() {
        if (!isRevealed && numberText.getGraphic() == null && App.clickable != false) {
            isRevealed = true;
            if (isMine) {
                rectangle.setFill(Color.RED);
                Image icon = new Image("bomb.png");
                ImageView imageView = new ImageView(icon);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                numberText.setGraphic(imageView);
                this.numberText.setTranslateX(-6);
                numberText.setVisible(true);
                revealAllMines();
                mineClicked = true;
                App.digitalClock.stopClock();
                App.clickable = false;
            } else {   
                App.digitalClock.start();
                if (countNeighboringMines() == 0) {
                    minesweeperBoard.revealCell(row, col);
                    revealNeighboringCells(row, col); 
                    rectangle.setFill(Color.BLANCHEDALMOND); 
                    getParent().requestLayout(); 
                } else {
                    int neighboringMines = countNeighboringMines();
                    numberText.setText(Integer.toString(neighboringMines));
                    numberText.setVisible(true);
                    rectangle.setFill(Color.BLANCHEDALMOND); 
                    getParent().requestLayout(); 
                    setColorBasedOnNumber(numberText, neighboringMines);
                }
            } 
        }
    }            


        private void handleRightClick() {
            if(App.clickable != false){
                App.digitalClock.start();
                if (!numberText.isVisible()) {
                    Image icon = new Image("icon.png");
                    ImageView imageView = new ImageView(icon);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    numberText.setGraphic(imageView);
                    this.numberText.setTranslateX(-6);
                    numberText.setVisible(true);
                    numberText.setMouseTransparent(true);
                    App.mineCount--;
                    updateMineCount(App.mineCount);
                    refreshMineCounter();
                }
                else{
                    numberText.setText("");
                    numberText.setVisible(false);
                    App.mineCount++;
                    updateMineCount(App.mineCount);
                    refreshMineCounter();
                }
            }
        }

        private void updateMineCount(int count) {
            App.updateMineCount(count);
        }
    
        private void refreshMineCounter() {
            mineCounter.setMineCount(App.mineCount);
        }

        private int countNeighboringMines() {
            int count = 0;
            int[][] neighbors = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1},           {0, 1},
                    {1, -1},  {1, 0},  {1, 1}
            };
    
            for (int[] neighbor : neighbors) {
                int neighborRow = row + neighbor[0]; 
                int neighborCol = col + neighbor[1]; 
                if (neighborRow >= 0 && neighborRow < numRows &&
                        neighborCol >= 0 && neighborCol < numCols) {
                    if (minesweeperBoard.isMineAt(neighborRow, neighborCol)) { 
                        count++;
                    }
                }
            }
            return count;
        }
    
        private void revealNeighboringCells(int row, int col) {
            int[][] neighbors = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},  {0, 1},
                {1, -1},  {1, 0}, {1, 1}
            };
            
            if (vertexViews == null) {
                return;
            }
            
            for (int[] neighbor : neighbors) {
                int neighborRow = row + neighbor[0];
                int neighborCol = col + neighbor[1];
            
                if (neighborRow >= 0 && neighborRow < numRows &&
                    neighborCol >= 0 && neighborCol < numCols &&
                    !vertexViews[neighborRow][neighborCol].isRevealed()) {
                    
                    vertexViews[neighborRow][neighborCol].isRevealed = true;
                    vertexViews[neighborRow][neighborCol].rectangle.setFill(Color.BLANCHEDALMOND);
        
                    if (!minesweeperBoard.isMineAt(neighborRow, neighborCol)) {
                        int neighboringMines = vertexViews[neighborRow][neighborCol].countNeighboringMines();
                        if (neighboringMines > 0) {
                            vertexViews[neighborRow][neighborCol].numberText.setText(Integer.toString(neighboringMines)); 
                            vertexViews[neighborRow][neighborCol].numberText.setVisible(true);
                            setColorBasedOnNumber(vertexViews[neighborRow][neighborCol].numberText, neighboringMines);
                        }
                        if (neighboringMines == 0) {
                            minesweeperBoard.revealCell(neighborRow, neighborCol);
                            revealNeighboringCells(neighborRow, neighborCol);
                        }
                    } else {
                        vertexViews[neighborRow][neighborCol].numberText.setText("M"); 
                        vertexViews[neighborRow][neighborCol].numberText.setVisible(true);
                    }
                }
            }
        }

        private void revealAllMines() {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    VertexView vertex = vertexViews[i][j];
                    if (vertex.isMine && !vertex.numberText.isVisible()) {
                        vertex.isRevealed = true;
                        vertex.rectangle.setFill(Color.RED);
                        Image icon = new Image("bomb.png");
                        ImageView imageView = new ImageView(icon);
                        imageView.setFitWidth(20);
                        imageView.setFitHeight(20);
                        vertex.numberText.setGraphic(imageView);
                        vertex.numberText.setTranslateX(-6);
                        vertex.numberText.setVisible(true);
                    }
                }
            }
        }
        
        private void setColorBasedOnNumber(Label label, int number) {
            switch (number) {
                case 1:
                    label.setTextFill(Color.BLUE);
                    break;
                case 2:
                    label.setTextFill(Color.GREEN);
                    break;
                case 3:
                    label.setTextFill(Color.RED);
                    break;
                case 4:
                    label.setTextFill(Color.DARKBLUE);
                    break;
                case 5:
                    label.setTextFill(Color.BROWN);
                    break;
                case 6:
                    label.setTextFill(Color.CYAN);
                    break;
                case 7:
                    label.setTextFill(Color.BLACK);
                    break;
                case 8:
                    label.setTextFill(Color.GRAY);
                    break;
                default:
                    label.setTextFill(Color.BLACK);
                    break;
            }
        }
        
    
}
