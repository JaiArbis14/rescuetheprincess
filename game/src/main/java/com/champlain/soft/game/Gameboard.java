package com.champlain.soft.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

public class Gameboard extends Application {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 800;
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int NUMBER_OF_BOMBS = 5;

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    private CellType[][] matrix = new CellType[ROWS][COLS];

    private Image grassImage;
    private Image playerImage;
    private Image princessImage;
    private Image bombImage;
    private Image wallImage;

    private Random random = new Random();
    private GridPane grid;

    private int playerRow = 1;
    private int playerCol = 1;
    private boolean gameOver = false;

    @Override
    public void start(Stage stage) {
        loadImages();
        initMatrix();

        grid = new GridPane();
        drawBoard();

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN -> movePlayer(1, 0);
                case RIGHT -> movePlayer(0, 1);
                case LEFT -> movePlayer(0, -1);
                case UP -> movePlayer(-1, 0);
            }
        });

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

        addWalls();

        matrix[playerRow][playerCol] = CellType.PLAYER;
        placeRandom(CellType.PRINCESS);

        for (int i = 0; i < NUMBER_OF_BOMBS; i++) {
            placeRandom(CellType.BOMB);
        }
    }

    private void addWalls() {
        for (int r = 0; r < ROWS; r++) {
            matrix[r][0] = CellType.WALL;
            matrix[r][COLS - 1] = CellType.WALL;
        }

        for (int c = 0; c < COLS; c++) {
            matrix[0][c] = CellType.WALL;
            matrix[ROWS - 1][c] = CellType.WALL;
        }
    }

    private void placeRandom(CellType type) {
        int row;
        int col;

        do {
            row = random.nextInt(ROWS);
            col = random.nextInt(COLS);
        } while (matrix[row][col] != CellType.GRASS);

        matrix[row][col] = type;
    }

    private void movePlayer(int rowMove, int colMove) {
        if (gameOver) {
            return;
        }

        int newRow = playerRow + rowMove;
        int newCol = playerCol + colMove;

        if (matrix[newRow][newCol] == CellType.WALL) {
            return;
        }

        if (matrix[newRow][newCol] == CellType.PRINCESS) {
            gameOver = true;
            showAlert("Victory", "You rescued the princess!");
        }

        matrix[playerRow][playerCol] = CellType.GRASS;

        playerRow = newRow;
        playerCol = newCol;

        matrix[playerRow][playerCol] = CellType.PLAYER;

        drawBoard();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SCENE_WIDTH / COLS);
        imageView.setFitHeight(SCENE_HEIGHT / ROWS);
        return imageView;
    }

    private void drawBoard() {
        grid.getChildren().clear();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(SCENE_WIDTH / COLS, SCENE_HEIGHT / ROWS);

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