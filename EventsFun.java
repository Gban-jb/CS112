import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;

public class EventsFun extends Application {
    //create pressX, pressY fields to store the mouse
    //pressX and pressY coordinates
    double pressX, pressY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create two Panes rootPane and drawPane
        Pane rootPane = new Pane();
        Pane drawPane = new Pane();
        //Binds the drawPane with and height to the rootPane width and height
        drawPane.prefWidthProperty().bind(rootPane.widthProperty());
        drawPane.prefHeightProperty().bind(rootPane.heightProperty());
        //Add the drawPane to the rootPane
        rootPane.getChildren().add(drawPane);
        //Creates a scene with the rootPane that is 500x500
        Scene scene = new Scene(rootPane, 500, 500);
        //Sets the scene to the primaryStage
        primaryStage.setScene(scene);
        //Sets the title of the primaryStage to "Draw Application"
        primaryStage.setTitle("Draw Application");
        //Shows the primaryStage
        primaryStage.show();
        //Add handlers for events

        //(1) using an inner class defined below
        drawPane.setOnMousePressed(new MyMouseHandler());
        
        // 2. using an anonymous inner class to handle mouse release events
        drawPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double releaseX = event.getX();
                double releaseY = event.getY();
                System.out.println("Mouse released at: " + releaseX + ", " + releaseY);
                System.out.println(event);

                //create a new line from the press and release coordinates 
                // and add it to the drawPane
                Line line = new Line(pressX, pressY, releaseX, releaseY);
                drawPane.getChildren().add(line);
            }
        });

        //3. using a lambda expression to handle ctrlz key press events to remove the last line
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode().toString().equals("Z")) {
                int size = drawPane.getChildren().size();
                if (size > 0) {
                    drawPane.getChildren().remove(size - 1);
                }
            }
        });
        drawPane.requestFocus();
    }

    class MyMouseHandler implements EventHandler<MouseEvent> {
        //get the mouse event and store the x and y coordinates
        @Override
        public void handle(MouseEvent event) {
            pressX = event.getX();
            pressY = event.getY();
            System.out.println("Mouse pressed at: " + pressX + ", " + pressY);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}