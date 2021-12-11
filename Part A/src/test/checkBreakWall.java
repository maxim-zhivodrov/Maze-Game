package test;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class checkBreakWall {
    public static void main(String[] args) {
        MyMazeGenerator myMaze = new MyMazeGenerator();
        Maze maze = myMaze.generate(10, 10);

        Position temp = maze.getStartPosition(); //Define the start position manually
        ArrayList<Position> list=new ArrayList<>();
        list=myMaze.Neighbors(maze,temp);
        int counter=0;
        for(int i=0;i<2;i++){
            if(list.get(i)!=null&&maze.getCell(list.get(i))==1) counter++;
        }
        if(counter==2) System.out.println("BAD :("); else System.out.println("GOOD :)");

    }
}
