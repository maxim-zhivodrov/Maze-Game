package View;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CharDisplayer extends Canvas {
    private Maze maze;
    public StringProperty ImageCharCurrPos=new SimpleStringProperty();
    public StringProperty ImageCharRight =new SimpleStringProperty();
    public StringProperty ImageCharLeft =new SimpleStringProperty();
    public StringProperty ImageCharUp =new SimpleStringProperty();
    public StringProperty ImageCharDown =new SimpleStringProperty();

    public int charCurrPosRow;
    public int charCurrPosCol;

    public CharDisplayer() {
        widthProperty().addListener(e->redraw());
        heightProperty().addListener(e->redraw());
    }

    public void setMaze(Maze maze)
    {
        this.maze=maze;
        charCurrPosRow=maze.getStartPosition().getRowIndex();
        charCurrPosCol=maze.getStartPosition().getColumnIndex();
    }
    public Maze getMaze() {
        return maze;
    }


    public void redraw(){
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.getRows();
            double cellWidth = canvasWidth / maze.getColumns();
            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0,0,getWidth(), getHeight()); //Clean the Canvas
                Image characterImage = new Image(new FileInputStream(ImageCharCurrPos.get()));
                graphicsContext2D.drawImage(characterImage, charCurrPosCol * cellWidth, charCurrPosRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void changePos(String direction)
    {
        try {
            if(direction.equals("left"))
            {
                if(maze.getCell(new Position(charCurrPosRow,charCurrPosCol-1))==0)
                {
                    charCurrPosCol--;
                }
                ImageCharCurrPos=ImageCharLeft;
            }

            else if(direction.equals("right"))
            {
                if(maze.getCell(new Position(charCurrPosRow,charCurrPosCol+1))==0)
                {
                    charCurrPosCol++;
                }
                ImageCharCurrPos=ImageCharRight;

            }

            else if(direction.equals("up"))
            {
                if(maze.getCell(new Position(charCurrPosRow-1,charCurrPosCol))==0)
                {
                    charCurrPosRow--;
                }
                ImageCharCurrPos=ImageCharUp;

            }

            else if(direction.equals("down"))
            {
                if(maze.getCell(new Position(charCurrPosRow+1,charCurrPosCol))==0)
                {
                    charCurrPosRow++;
                }
                ImageCharCurrPos=ImageCharDown;

            }
            else if(direction.equals("ur")){
                if(maze.getCell(new Position(charCurrPosRow-1,charCurrPosCol+1))==0 &&
                        (maze.getCell(new Position(charCurrPosRow-1,charCurrPosCol))==0 ||
                                maze.getCell(new Position(charCurrPosRow,charCurrPosCol+1))==0))
                {
                    charCurrPosRow--;
                    charCurrPosCol++;
                    ImageCharCurrPos=ImageCharRight;
                }
            }
            else if(direction.equals("dr")){
                if(maze.getCell(new Position(charCurrPosRow+1,charCurrPosCol+1))==0 &&
                        (maze.getCell(new Position(charCurrPosRow+1,charCurrPosCol))==0 ||
                                maze.getCell(new Position(charCurrPosRow,charCurrPosCol+1))==0))
                {
                    charCurrPosRow++;
                    charCurrPosCol++;
                    ImageCharCurrPos=ImageCharRight;
                }
            }
            else if(direction.equals("dl")){
                if(maze.getCell(new Position(charCurrPosRow+1,charCurrPosCol-1))==0 &&
                        (maze.getCell(new Position(charCurrPosRow+1,charCurrPosCol))==0 ||
                                maze.getCell(new Position(charCurrPosRow,charCurrPosCol-1))==0))
                {
                    charCurrPosRow++;
                    charCurrPosCol--;
                    ImageCharCurrPos=ImageCharLeft;
                }
            }
            else if(direction.equals("ul")){
                if(maze.getCell(new Position(charCurrPosRow-1,charCurrPosCol-1))==0 &&
                        (maze.getCell(new Position(charCurrPosRow-1,charCurrPosCol))==0 ||
                                maze.getCell(new Position(charCurrPosRow,charCurrPosCol-1))==0))
                {
                    charCurrPosRow--;
                    charCurrPosCol--;
                    ImageCharCurrPos=ImageCharLeft;
                }
            }
            else
            {
                audio.playError();
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            audio.playError();
        }

        redraw();
        if(charCurrPosRow==maze.getGoalPosition().getRowIndex()&&charCurrPosCol==maze.getGoalPosition().getColumnIndex())
        {
            //My Additions 08/06
            MyViewController.resetTrainerWallArray();
            /////////
            audio.theme.stop();
            audio.playSolved();
            Stage finished=new Stage();
            finished.initModality(Modality.APPLICATION_MODAL);
            Parent finishedRoot= null;
            Button newGame= null;
            Button cancel= null;
            try {
                FXMLLoader fxmlLoader1 = new FXMLLoader();
                finishedRoot = fxmlLoader1.load(getClass().getResource("../View/finished.fxml").openStream());
                newGame = (Button)(fxmlLoader1.getNamespace().get("PlayAgain"));
                cancel = (Button)(fxmlLoader1.getNamespace().get("Cancel"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            newGame.setOnAction(e -> {
                finished.close();
                Stage playStage=new Stage();
                playStage.initModality(Modality.APPLICATION_MODAL);
                playStage.setTitle("Make Your Game Awesome!");
                Parent playRoot = null;
                try {
                    //My Additions 08/06
                    FXMLLoader fxmlLoader2 = new FXMLLoader();
                    playRoot =  fxmlLoader2.load(getClass().getResource("../View/playOptions.fxml").openStream());
                    Scene playScene=new Scene(playRoot, 550, 300);
                    playScene.getStylesheets().add(getClass().getResource("playOptions.css").toExternalForm());
                    MyViewController view=fxmlLoader2.getController();
                    if (view.playButton!=null) {
                        view.playButton.setDisable(true);
                    }
                    playStage.setScene(playScene);
                    playStage.setOnCloseRequest(event -> MyViewController.resetTrainerWallArray());
                    playStage.show();
                    //////////
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            //My Additions 07/06 1:32
            MyViewController.setIsGoalReached(true);
            /////
            cancel.setOnAction(e -> finished.close());
            Scene scene=new Scene(finishedRoot,650,400);
            scene.getStylesheets().add(getClass().getResource("finished.css").toExternalForm());
            finished.setScene(scene);
            finished.show(); ///was showAndWait
        }
    }


    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }


    public int getCharCurrPosRow() {
        return charCurrPosRow;
    }

    public int getCharCurrPosCol() {
        return charCurrPosCol;
    }

    public String getImageCharCurrPos() {
        return ImageCharCurrPos.get();
    }

    public StringProperty imageCharCurrPosProperty() {
        return ImageCharCurrPos;
    }

    public String getImageCharRight() {
        return ImageCharRight.get();
    }

    public StringProperty imageCharRightProperty() {
        return ImageCharRight;
    }

    public String getImageCharLeft() {
        return ImageCharLeft.get();
    }

    public StringProperty imageCharLeftProperty() {
        return ImageCharLeft;
    }

    public String getImageCharUp() {
        return ImageCharUp.get();
    }

    public StringProperty imageCharUpProperty() {
        return ImageCharUp;
    }

    public String getImageCharDown() {
        return ImageCharDown.get();
    }

    public StringProperty imageCharDownProperty() {
        return ImageCharDown;
    }

    public void setImageCharCurrPos(String imageCharCurrPos) {
        this.ImageCharCurrPos.set(imageCharCurrPos);
    }

    public void setImageCharRight(String imageCharRight) {
        this.ImageCharRight.set(imageCharRight);
    }

    public void setImageCharLeft(String imageCharLeft) {
        this.ImageCharLeft.set(imageCharLeft);
    }

    public void setImageCharUp(String imageCharUp) {
        this.ImageCharUp.set(imageCharUp);
    }

    public void setImageCharDown(String imageCharDown) {
        this.ImageCharDown.set(imageCharDown);
    }

    //My Additions 07/06 00:24
    public void setAsh(){
        this.ImageCharDown.setValue("resources/ash_down.jpg");
        this.ImageCharUp.setValue("resources/ash_up.jpg");
        this.ImageCharLeft.setValue("resources/ash_left.jpg");
        this.ImageCharRight.setValue("resources/ash_right.jpg");
        this.ImageCharCurrPos.setValue("resources/ash_down.jpg");
    }
    public void setMisty(){
        this.ImageCharDown.setValue("resources/misty_down.jpg");
        this.ImageCharUp.setValue("resources/misty_up.jpg");
        this.ImageCharLeft.setValue("resources/misty_left.jpg");
        this.ImageCharRight.setValue("resources/misty_right.jpg");
        this.ImageCharCurrPos.setValue("resources/misty_down.jpg");
    }
    public void setBrendan(){
        this.ImageCharDown.setValue("resources/brendan_down.jpg");
        this.ImageCharUp.setValue("resources/brendan_up.jpg");
        this.ImageCharLeft.setValue("resources/brendan_left.jpg");
        this.ImageCharRight.setValue("resources/brendan_right.jpg");
        this.ImageCharCurrPos.setValue("resources/brendan_down.jpg");
    }

    public void cleanCanvas(){
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        graphicsContext2D.clearRect(0,0,getWidth(), getHeight());
    }


}