import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MultipleStageDemo extends Application {

	public MultipleStageDemo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create a scene and place a ok button in the scene
		
		Scene scene = new Scene(new Button("OK"), 350, 350);
		primaryStage.setTitle("MyJavaFX");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage stage = new Stage();
		stage.setTitle("Second Stage");
		stage.setScene(new Scene (new Button ("Second"), 200, 200));
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
