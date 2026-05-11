package com.champlain.soft.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gameboard extends Application {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 800;

    private static final int ROWS = 10;
    private static final int COLS = 10;

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    private CellType[][] matrix = new CellType[ROWS][COLS];

    private Image grassImage;
    private Image playerImage;
    private Image princessImage;
    private Image bombImage;
    private Image wallImage;

    @Override
    public void start(Stage stage) {

        loadImages();
        initMatrix();

        GridPane grid = new GridPane();
        drawBoard(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle("Rescue the Princess");
        stage.setScene(scene);
        stage.show();
    }

    private void loadImages() {

        grassImage = new Image("file:C:/Users/jaido/IdeaProjects/rescuetheprincess/game/src/images/grass.png");

        playerImage = new Image("file:C:/Users/jaido/IdeaProjects/rescuetheprincess/game/src/images/player.png");

        princessImage = new Image("file:C:/Users/jaido/IdeaProjects/rescuetheprincess/game/src/images/princess.png");

        bombImage = new Image("file:C:/Users/jaido/IdeaProjects/rescuetheprincess/game/src/images/bomb.png");

        wallImage = new Image("file:C:/Users/jaido/IdeaProjects/rescuetheprincess/game/src/images/wall.png");
    }

    private void initMatrix() {

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                matrix[r][c] = CellType.GRASS;
            }
        }

        matrix[0][0] = CellType.PLAYER;
        matrix[9][9] = CellType.PRINCESS;
        matrix[4][5] = CellType.BOMB;

        matrix[1][1] = CellType.WALL;
        matrix[1][2] = CellType.WALL;
    }

    private ImageView createImageView(Image image) {

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(SCENE_WIDTH / COLS);
        imageView.setFitHeight(SCENE_HEIGHT / ROWS);

        return imageView;
    }

    private void drawBoard(GridPane grid) {

        grid.getChildren().clear();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                StackPane cell = new StackPane();

                cell.setPrefSize(
                        SCENE_WIDTH / COLS,
                        SCENE_HEIGHT / ROWS
                );

                cell.getChildren().add(createImageView(grassImage));

                if (matrix[row][col] == CellType.PLAYER) {

                    cell.getChildren().add(createImageView(playerImage));

                } else if (matrix[row][col] == CellType.PRINCESS) {

                    cell.getChildren().add(createImageView(princessImage));

                } else if (matrix[row][col] == CellType.BOMB) {

                    cell.getChildren().add(createImageView(bombImage));

                } else if (matrix[row][col] == CellType.WALL) {

                    cell.getChildren().add(createImageView(wallImage));
                }

                grid.add(cell, col, row);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}