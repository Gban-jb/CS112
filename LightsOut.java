import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LightsOut extends Application {
    
    private int size; 
    private Button[][] buttons; 
    private Stage puzzleStage; 
    
    private static final Background ON = new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(10), new Insets(1)));
    private static final Background OFF = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(1)));
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lights Out Size");
        
        Label promptLabel = new Label("Please select a size:");
        ToggleGroup sizeGroup = new ToggleGroup();
        
        VBox selectionBox = new VBox(5); // VBox with 5 pixels spacing
        selectionBox.setAlignment(Pos.CENTER);
        selectionBox.getChildren().add(promptLabel);
        
        // Create radio buttons for sizes 3-9
        RadioButton selectedButton = null;
        for (int i = 3; i <= 9; i++) {
            RadioButton radioButton = new RadioButton(Integer.toString(i));
            radioButton.setToggleGroup(sizeGroup);
            selectionBox.getChildren().add(radioButton);
            
            if (i == 5) {
                radioButton.setSelected(true);
                selectedButton = radioButton;
            }
        }
        
        Button createButton = new Button("Create Puzzle");
        selectionBox.getChildren().add(createButton);
        
        createButton.setOnAction(e -> {
            // Get the selected size
            RadioButton selectedRadioButton = (RadioButton) sizeGroup.getSelectedToggle();
            size = Integer.parseInt(selectedRadioButton.getText());
            
            createPuzzleStage();
            
            // Close the selection stage
            primaryStage.close();
        });
        
        Scene scene = new Scene(selectionBox, 200, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void createPuzzleStage() {
        // Create a new stage for the puzzle
        puzzleStage = new Stage();
        puzzleStage.setTitle("Lights Out");
        puzzleStage.setResizable(false);
        
        BorderPane borderPane = new BorderPane();
        
        // Create HBox for buttons at the bottom
        HBox buttonBox = new HBox(20); // 20 pixels spacing
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefHeight(60);
        
        Button randomizeButton = new Button("Randomize");
        Button chaseLightsButton = new Button("Chase Lights");
        
        randomizeButton.setOnAction(e -> randomize());
        chaseLightsButton.setOnAction(e -> chaseLights());
        
        // Add buttons to HBox
        buttonBox.getChildren().addAll(randomizeButton, chaseLightsButton);
        
                GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.web("#555555"), null, null)));
        
        buttons = new Button[size][size];
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                final int finalRow = row;
                final int finalCol = col;
                
                Button lightButton = new Button();
                lightButton.setPrefSize(50, 50);
                lightButton.setBackground(OFF); // Initially off
                
                // Set action when light button is pressed
                lightButton.setOnAction(e -> press(finalRow, finalCol));
                
                // Add button to grid and array
                gridPane.add(lightButton, col, row);
                buttons[row][col] = lightButton;
            }
        }
        
        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttonBox);
        
        Scene scene = new Scene(borderPane, Math.max(250, 60 * size), (60 * size) + 60);
        puzzleStage.setScene(scene);
        puzzleStage.show();
        
        // Randomize the initial grid
        randomize();
    }
    
    private void randomize() {
        // Reset all lights to off
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setBackground(OFF);
            }
        }
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (Math.random() < 0.5) {
                    press(row, col);
                }
            }
        }
    }
    
    private void chaseLights() {
        for (int row = 0; row < size - 1; row++) {
            for (int col = 0; col < size; col++) {
                // If light is on, press the light below it
                if (isLightOn(row, col)) {
                    press(row + 1, col);
                }
            }
        }
    }
    
    private void press(int row, int col) {
        toggleLight(row, col); 
        if (row > 0) 
        	toggleLight(row - 1, col); // Button above
        if (row < size - 1) 
        	toggleLight(row + 1, col); // Button below
        if (col > 0) 
        	toggleLight(row, col - 1); // Button to the left
        if (col < size - 1) 
        	toggleLight(row, col + 1); // Button to the right
    }
    
    private void toggleLight(int row, int col) {
        Button button = buttons[row][col];
        if (isLightOn(row, col)) {
            button.setBackground(OFF);
        } else {
            button.setBackground(ON);
        }
    }
    
    private boolean isLightOn(int row, int col) {
        return buttons[row][col].getBackground().equals(ON);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}