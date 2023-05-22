package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class StartGameMenu extends Application {
    private List<Integer> ballPositions;
    private static Rectangle selectGameRectangle;
    public static int gameIndex = 0;
    private Slider difficultySlider, numberOfPinsSlider;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(StartGameMenu.class.getResource("startGame.fxml"));
        HBox hBox = new HBox();
        selectGameRectangle = new Rectangle(200, 200);
        selectGameRectangle.setOnMouseClicked(event -> getGame());
        selectGameRectangle.setFill(new ImagePattern(Loader.getImage("/images/map2.png")));
        hBox.getChildren().add(selectGameRectangle);
        GridPane gridPane = new GridPane();
        Label difficultyLabel = new Label("difficulty: 2");
        difficultyLabel.setTextFill(Color.color(1, 1, 1));
        difficultyLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        difficultyLabel.setAlignment(Pos.CENTER);
        difficultyLabel.setMinWidth(200);
        gridPane.add(difficultyLabel, 0, 0);
        this.difficultySlider = new Slider(1, 3, 2);
        this.difficultySlider.setShowTickLabels(true);
        this.difficultySlider.setShowTickMarks(true);
        this.difficultySlider.setMajorTickUnit(1);
        this.difficultySlider.setMinorTickCount(0);
        this.difficultySlider.setSnapToTicks(true);
        this.difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            difficultyLabel.setText("difficulty: " + newValue.intValue());
        });
        gridPane.add(this.difficultySlider, 0, 1);
        Label numberOfPinsLabel = new Label("number of pins: 25");
        numberOfPinsLabel.setTextFill(Color.color(1, 1, 1));
        numberOfPinsLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        numberOfPinsLabel.setAlignment(Pos.CENTER);
        numberOfPinsLabel.setMinWidth(200);
        gridPane.add(numberOfPinsLabel, 0, 2);
        this.numberOfPinsSlider = new Slider(10, 40, 25);
        this.numberOfPinsSlider.setShowTickLabels(true);
        this.numberOfPinsSlider.setShowTickMarks(true);
        this.numberOfPinsSlider.setMajorTickUnit(5);
        this.numberOfPinsSlider.setMinorTickCount(1);
        this.numberOfPinsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numberOfPinsLabel.setText("number of pins: " + newValue.intValue());
        });
        gridPane.add(this.numberOfPinsSlider, 0, 3);
        Button startGameButton = new Button("start game");
        startGameButton.setOnMouseClicked(event -> {
            try {
                GameMenu gameMenu = new GameMenu();
                gameMenu.setDifficulty((int) this.difficultySlider.getValue());
                gameMenu.setNumberOfPins((int) this.numberOfPinsSlider.getValue());
                gameMenu.setGameIndex(gameIndex);
                gameMenu.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gridPane.add(startGameButton, 0, 4);
        hBox.getChildren().add(gridPane);
        pane.getChildren().add(hBox);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void getGame() {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        Label label = new Label("choose game map");
        label.setMinWidth(600);
        label.setFont(Font.font("Cambria", 32));
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.color(1, 1, 1));
        label.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().add(label);
        HBox hBox = new HBox();
        for (int i = 0; i < 3; i++) {
            Paint paint = new ImagePattern(new Image(ProfileMenu.class.getResource(
                    "/images/map"+i+".png").toExternalForm()));
            Rectangle rectangle = new Rectangle(200, 200, paint);
            final int index = i;
            rectangle.setOnMouseClicked(event -> {
                StartGameMenu.gameIndex = index;
                selectGameRectangle.setFill(new ImagePattern(Loader.getImage(
                        "/images/map"+index+".png")));
                System.out.println("clicked" + index);
                popup.hide();
            });
            hBox.getChildren().add(rectangle);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPrefSize(600, 200);
        vBox.getChildren().add(scrollPane);
        popup.getContent().add(vBox);
        popup.show(LoginMenu.stage);
    }
}
