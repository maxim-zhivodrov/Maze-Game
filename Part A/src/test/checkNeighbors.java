package test;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class checkNeighbors
{
    public static void main(String[] args)
    {
        MyMazeGenerator myMaze=new MyMazeGenerator();
        Maze maze=myMaze.generate(10,10);

        Position tempPos1=new Position(0,0);
        Position tempPos2=new Position(3,0);
        Position tempPos3=new Position(6,4);
        ArrayList<Position> list1=myMaze.Neighbors(maze,tempPos1);
        ArrayList<Position> list2=myMaze.Neighbors(maze,tempPos2);
        ArrayList<Position> list3=myMaze.Neighbors(maze,tempPos3);
        System.out.println("Position 1 neighbors are:");
        for(int i=0;i<4;i++)
        {
            if(list1.get(i)!=null)System.out.println(list1.get(i).toString());

        }
        System.out.println("Position 2 neighbors are:");
        for(int i=0;i<4;i++)
        {
            if(list2.get(i)!=null)System.out.println(list2.get(i).toString());

        }
        System.out.println("Position 3 neighbors are:");
        for(int i=0;i<4;i++)
        {
            if(list3.get(i)!=null)System.out.println(list3.get(i).toString());

        }

    }
}
