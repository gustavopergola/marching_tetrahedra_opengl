import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	Scene scene1;
	int counter = 0;
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// First Scene
		final Label label1 = new Label("Select your process file");
		
		final Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counter <= 1000) {
                	counter++;
                    updateUI(counter, label1);
                    try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
        countThread.setDaemon(true);
        countThread.start();
        
		Button button1 = new Button(); 
		button1.setText("Select TXT File");
		
		Button button2 = new Button();
		button2.setText("Start");
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open TXT File");
		
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1, button1, button2);
		layout1.setAlignment(Pos.CENTER);
		
		scene1 = new Scene(layout1, 300, 250);
		
		primaryStage.setScene(scene1);
		primaryStage.show();
		
	}
	
	private void updateUI(int counter, final Label label) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final String msg = String.format("Count: %d", counter);
                label.setText(msg);
            }
        });
    }


}
