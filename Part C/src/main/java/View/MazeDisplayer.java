package View;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private Maze maze;

    public MazeDisplayer() {
        widthProperty().addListener(e->redraw());
        heightProperty().addListener(e->redraw());
    }

    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }

    public StringProperty imageFileNameGoalProperty() {
        return ImageFileNameGoal;
    }

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public StringProperty imageFileNameWallProperty() {
        return ImageFileNameWall;
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public Maze getMaze() {
        return maze;
    }



    public void setMaze(Maze maze) {
        this.maze = maze;
    }



    public void setDimentions(Maze maze){
        this.maze = maze;
        redraw();
    }

    public void redraw(){
        if (maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight/maze.getRows();
            double cellWidth = canvasWidth/maze.getColumns();

            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0,0,getWidth(), getHeight()); //Clean the Canvas
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                //Draw maze
                for (int row = 0; row < maze.getRows(); row++) {
                    for (int column = 0; column < maze.getColumns(); column++) {
                        if (maze.getCell(new Position(row,column)) == 1){
                            graphicsContext2D.drawImage(wallImage, column*cellWidth, row*cellHeight,cellWidth,cellHeight);
                        }
                    }
                }

                Image characterGoal = new Image(new FileInputStream(ImageFileNameGoal.get()));
                graphicsContext2D.drawImage(characterGoal, maze.getGoalPosition().getColumnIndex() * cellWidth, maze.getGoalPosition().getRowIndex() * cellHeight, cellWidth, cellHeight);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    //My Additions 07/06 00:51
    public void setGrass(){
        this.ImageFileNameWall.setValue("resources/grassWall.jpg");
    }
    public void setRock(){
        this.ImageFileNameWall.setValue("resources/rockWall.jpg");
    }
    public void setTree(){
        this.ImageFileNameWall.setValue("resources/treeWall.jpg");
    }
    public void cleanCanvas(){
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        graphicsContext2D.clearRect(0,0,getWidth(), getHeight());
    }
    ///////

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
}
