import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ShowCircle extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();

		Circle circle = new Circle();
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.heightProperty().divide(2));
		//circle.setCenterX(100);
		//circle.setCenterY(100);
		circle.setRadius(50);
		circle.setStroke(Color.BLUE);
		circle.setFill(Color.RED);
		pane.getChildren().add(circle);
		
		Scene scene = new Scene(pane, 300, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ShowCircle");
		primaryStage.show();	
	}

	public static void main(String[] args) {
		launch(args);
	}
}