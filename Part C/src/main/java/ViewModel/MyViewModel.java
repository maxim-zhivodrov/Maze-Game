package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;


public class MyViewModel extends Observable implements Observer {
    private String directory;
    private IModel model;
    private boolean isMazeGenerated;
    private boolean isCharMoved;
    private boolean isSolved;

    public MyViewModel(IModel model){
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            directory=model.getDirectory();
            isMazeGenerated=model.isMazeGenerated();
            isCharMoved=model.isCharMoved();
            isSolved=model.isSolved();
            model.setCharMoved(false);
            model.setMazeGenerated(false);
            model.setSolved(false);
            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int rows, int columns){
        model.generateMaze(rows, columns);
    }

    public void moveCharacter(KeyEvent movement){
        model.moveCharacter(movement);
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    public String getDirectory() {
        return directory;
    }
    public void setMazeGenerated(boolean mazeGenerated) {
        isMazeGenerated = mazeGenerated;
    }

    public void setCharMoved(boolean charMoved) {
        isCharMoved = charMoved;
    }

    public boolean isMazeGenerated() {
        return isMazeGenerated;
    }

    public boolean isCharMoved() {
        return isCharMoved;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public Solution getSol() {
        return model.getSol();
    }

    public void showSolution(int rowPos,int colPos) {
        model.showSolution(rowPos,colPos);
    }

    public void exit() {
        this.model.stopServers();
    }

    public void loadMaze(){this.model.loadMaze();}

    public void mouseDragUp() {
        this.model.mouseDragUp();
    }
    public void mouseDragDown() {
        this.model.mouseDragDown();
    }
    public void mouseDragLeft() {
        this.model.mouseDragLeft();
    }
    public void mouseDragRight() {
        this.model.mouseDragRight();
    }

    public void saveMaze(int charCurrRow,int charCurrCol){this.model.saveMaze(charCurrRow,charCurrCol);}
}
