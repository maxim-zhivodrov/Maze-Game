package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SolutionDisplayer extends Canvas {
    private Maze maze;
    private Solution sol;
    public StringProperty ImagePokeball=new SimpleStringProperty();

    public SolutionDisplayer() {
        widthProperty().addListener(e->redraw());
        heightProperty().addListener(e->redraw());
    }

    public void setMaze(Maze maze)
    {
        this.maze=maze;
    }

    public String getImagePokeball() {
        return ImagePokeball.get();
    }

    public StringProperty imagePokeballProperty() {
        return ImagePokeball;
    }

    public void setImagePokeball(String imagePokeball) {
        this.ImagePokeball.set(imagePokeball);
    }

    public void setSol(Solution sol) {
        this.sol = sol;
    }

    public void redraw(){
        if (maze != null) {
            ArrayList<AState> solList=sol.getSolutionPath();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.getRows();
            double cellWidth = canvasWidth / maze.getColumns();
            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0,0,getWidth(), getHeight()); //Clean the Canvas
                Image solutionImage = new Image(new FileInputStream(ImagePokeball.get()));
                for(int i=1;i<sol.getSolutionPathLength()-1;i++)
                {
                    graphicsContext2D.drawImage(solutionImage, ((MazeState)solList.get(i)).getPosition().getColumnIndex() * cellWidth,
                            ((MazeState)solList.get(i)).getPosition().getRowIndex() * cellHeight,
                            cellWidth, cellHeight);
//                    Thread.sleep(50);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanCanvas(){
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        graphicsContext2D.clearRect(0,0,getWidth(), getHeight());
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
}
