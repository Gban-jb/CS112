import java.awt.Font;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MathMagic extends Application {
	
	public static int getPositiveInteger(String headerText, String prompt)  {
		int retVal = 0;
		do {		
			// request positive integer
			TextInputDialog dialog = new TextInputDialog("1");
	        dialog.initStyle(StageStyle.UTILITY);
			dialog.setTitle("Input Positive Integer");
			dialog.setHeaderText(headerText);			
			dialog.setContentText(prompt);
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent())
				retVal = Integer.parseInt(result.get());
			if (retVal > 0)
				return retVal;
			else {
				// alert to invalid input
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.initStyle(StageStyle.UTILITY);
		        alert.setTitle("Invalid Input");
		        alert.setHeaderText(null); // uncomment to see default error header
		        alert.setGraphic(null); // uncomment to see default error header
		        alert.setContentText("Invalid positive integer. Please try again.");
		        alert.showAndWait();
			}
		} while (true);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        new JFXPanel(); // this will prepare JavaFX toolkit and environment
		final int SIZE = 10;
		// Get initial data from user
		int[] data = new int[SIZE];
		data[0] = getPositiveInteger("First Positive Integer", "Please enter a small positive integer:");
		data[1] = getPositiveInteger("Second Positive Integer", "Please enter another small positive integer:");
		// Describe next step
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Instructions");
        alert.setContentText("We will next add these two integers together to get a third, " +
				"add the second and third to get the fourth, etc., " +
				"until we have " + SIZE + " integers.");
        alert.showAndWait();
	
        //Uncomment for fast practice:
//		Random random = new Random();
//		data[0] = random.nextInt(10);
//		data[1] = random.nextInt(10);
        
		for (int i = 2; i < SIZE; i++)
			data[i] = data[i - 1] + data[i - 2];
		int sum = 0;
		
		// The previous Java GUI standard, Swing, didn't require an Application class
		// and could be used arbitrarily from any Java code context.
		// Here is a demonstration of older, simpler Swing means for showing dialogs:
		
		// Create stacked, monospaced, Swing JLabels for easier addition
	    JLabel[] labels = new JLabel[SIZE];
		for (int i = 0; i < SIZE; i++) {
			sum += data[i];
			labels[i] = new JLabel(String.format("%6d\n", data[i]));
			labels[i].setFont(new Font("Monospaced", Font.PLAIN, 12));
		}
		
		do {
			try {
				// request the sum
				int answer = Integer.parseInt(JOptionPane.showInputDialog(labels, "Enter the sum here."));
				JOptionPane.showMessageDialog(null, (answer == sum) ? "Correct!" : "Incorrect.  The sum was " + sum, "Result", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			catch (Exception e) {
				// alert to invalid input
				JOptionPane.showMessageDialog(null, "Invalid sum. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} while (true);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
