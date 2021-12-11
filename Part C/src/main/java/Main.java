import Model.MyModel;
import View.MazeDisplayer;
import View.MyViewController;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.Optional;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        //(new MyViewController()).setAbout();
        MyModel model = new MyModel();
        //model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("Pokemon Maze");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyIntro.fxml").openStream());
        MenuItem saveIntro=(MenuItem) (fxmlLoader.getNamespace().get("saveIntro"));
        saveIntro.setDisable(true);
        MenuItem loadIntro=(MenuItem) (fxmlLoader.getNamespace().get("loadIntro"));
        loadIntro.setDisable(!((new File("savedMaze.maze")).exists()));
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("View/MyIntro.css").toExternalForm());
        primaryStage.setScene(scene);
        //--------------
        MyViewController view = fxmlLoader.getController();
//        view.setResizeEvent(scene);
        view.setMyViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
//        SetStageCloseEvent(primaryStage, view);
        primaryStage.setOnCloseRequest(event -> view.Xclose());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void SetStageCloseEvent(Stage primaryStage, MyViewController view) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    view.exit();

                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }
}