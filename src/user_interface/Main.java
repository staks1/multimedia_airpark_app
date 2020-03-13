package user_interface;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class Main extends Application {


    public void init(){


    }

    @Override
    public void start(Stage stage) throws IOException
    {
        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();


        // Path to the FXML File
        String fxmlDocPath = "/home/st/IdeaProjects/Texnologia_polymeswn_askhsh/src/user_interface/park_test_renew.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        try{

        // Create the Pane and all Details
        VBox root = (VBox) loader.load(fxmlStream);   //return the root of the screen

        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("AirparkApp");
        // Display the Stage
        stage.show();



    }catch(Exception e){
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        launch(args);



    }
}
