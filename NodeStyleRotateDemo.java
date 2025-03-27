import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NodeStyleRotateDemo extends Application {

	public NodeStyleRotateDemo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		StackPane pane = new StackPane();
		Button btOk = new Button("OK");
		btOk.setStyle("-fx-border-color:blue;");
		pane.getChildren().add(btOk);
		
		pane.setRotate(45);
		pane.setStyle("-fx-border-color: red; -fx-background-color: Yellow");
		
		Scene scene = new Scene(pane, 200, 250);		// TODO Auto-generated method stub

		primaryStage.setTitle("NodeStyleRotateDemo");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
