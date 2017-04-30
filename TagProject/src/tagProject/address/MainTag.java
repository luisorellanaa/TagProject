package tagProject.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tagProject.address.view.MainScreenControler;

import org.terrier.matching.models.TF_IDF;

public class MainTag extends Application {

public static Stage mainStage;
    

    @Override
    public void start(Stage primaryStage) throws IOException {
    	mainStage = primaryStage;
    	mainStage.setTitle("Rank");

        //Main Layout
    	FXMLLoader mainLoader = new FXMLLoader();
    	mainLoader.setLocation(MainTag.class.getResource("view/MainScreen.fxml"));
        AnchorPane mainOverview = (AnchorPane) mainLoader.load();
        
        //Index Layout
    	FXMLLoader indexLoader = new FXMLLoader();
    	indexLoader.setLocation(MainTag.class.getResource("view/IndexLayout.fxml"));
        AnchorPane indexOverview = (AnchorPane) indexLoader.load();

        MainScreenControler controller = mainLoader.getController();
        
        controller.setTabContent(indexOverview);

        
        Scene scene = new Scene(mainOverview);
        mainStage.setScene(scene);
        mainStage.show();
        
        
    }



    /**
     * Shows the person overview inside the root layout.
     
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainTag.class.getResource("view/MainScreen.fxml"));
            TabPane personOverview = (TabPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/
	public static void main(String[] args) {
		launch(args);
	}
}
