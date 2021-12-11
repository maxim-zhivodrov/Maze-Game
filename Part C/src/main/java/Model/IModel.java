package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

public interface IModel {

    String getDirectory();
    void moveCharacter(KeyEvent movement);
    Maze getMaze();
    void setMazeGenerated(boolean mazeGenerated);
    void setCharMoved(boolean charMoved);
    boolean isMazeGenerated();
    boolean isCharMoved();
    void generateMaze(int rows, int columns);
    Solution getSol();
    void showSolution(int rowPos, int colPos);
    boolean isSolved();
    void setSolved(boolean solved);


    void stopServers();

    void loadMaze();

    void mouseDragUp();
    void mouseDragDown();
    void mouseDragRight();
    void mouseDragLeft();

    void saveMaze(int charCurrRow, int charCurrCol);
}
