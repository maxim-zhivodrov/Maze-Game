package algorithms.mazeGenerators;

import algorithms.search.AState;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator
{
    /**
     * The function creates a Maze using Prim's algorithm
     * @param rows
     * @param columns
     */
    @Override
    public Maze generate(int rows, int columns)
    {
        int random=0;
        EmptyMazeGenerator emptyMaze=new EmptyMazeGenerator();
        Maze myMaze=emptyMaze.generate(rows,columns);
        if((rows==2&&columns==2)||(rows==1)||(columns==1)){
            return myMaze;
        }
        if((rows==2 && columns <4 )||(columns==2 && rows <4)){
            myMaze.setStartPosition(0,0);
            myMaze.setGoalPosition(rows-1,columns-1);
        }
        if(rows==3 && columns ==3) myMaze.setStartPosition(0,0);

        Position tempPos=new Position(0,0);
        initializeMazeWithWalls(tempPos,myMaze);

        ArrayList<Position> list=new ArrayList<Position>();
        tempPos=myMaze.getStartPosition();
        myMaze.setCell(tempPos,0);
        InsertWalls(myMaze,tempPos,list);
        while(!list.isEmpty())
        {
            random=(int)(Math.random() * (list.size()));
            tempPos=list.get(random);
            Position theNextWall=NextWall(myMaze,tempPos);
            if(theNextWall!=null)
            {
                myMaze.setCell(tempPos,0);
                InsertWalls(myMaze,theNextWall,list);
                myMaze.setCell(theNextWall,0);
            }
            list.remove(random);
        }
        setGoalAfterPrim(myMaze);
        return myMaze;
    }

    /**
     * The function inserts all the valid neighbors of the position to the list
     * @param maze
     * @param pos
     * @param list
     */
    protected void InsertWalls(Maze maze,Position pos,ArrayList<Position> list)
    {
        for(int i=0;i<4;i++)
        {
            ArrayList<Position> posTemp=Neighbors(maze,pos);
            if((posTemp).get(i)!=null&&maze.getCell((posTemp).get(i))==1)
                list.add((posTemp).get(i));
        }
    }

    /**
     * The function determines where to continue to build the maze path
     * and returns the next valid position
     * @param maze
     * @param pos
     * @return
     */
    protected Position NextWall(Maze maze,Position pos)
    {
        ArrayList<Position> list=Neighbors(maze,pos);
        if(list.get(0)!=null&&list.get(1)!=null&&maze.getCell(list.get(0))==0&&maze.getCell(list.get(1))==1)
            return list.get(1);
        else if(list.get(0)!=null&&list.get(1)!=null&&maze.getCell(list.get(0))==1&&maze.getCell(list.get(1))==0)
            return list.get(0);
        else if(list.get(2)!=null&&list.get(3)!=null&&maze.getCell(list.get(2))==0&&maze.getCell(list.get(3))==1)
            return list.get(3);
        else if(list.get(2)!=null&&list.get(3)!=null&&maze.getCell(list.get(2))==1&&maze.getCell(list.get(3))==0)
            return list.get(2);

        return null;
    }

    /**
     * The function returns a list that will contain all the neighbors of the position
     * @param maze
     * @param pos
     * @return
     */
    protected ArrayList<Position> Neighbors(Maze maze,Position pos)
    {
        ArrayList <Position> list=new ArrayList<Position>();
        Position right=new Position(pos.getRowIndex(),pos.getColumnIndex()+1);
        Position left=new Position(pos.getRowIndex(),pos.getColumnIndex()-1);
        Position up=new Position(pos.getRowIndex()-1,pos.getColumnIndex());
        Position down=new Position(pos.getRowIndex()+1,pos.getColumnIndex());
        if(pos.getColumnIndex()+1 < maze.getColumns()) list.add(right);
        else list.add(null);
        if(pos.getColumnIndex()-1 > -1) list.add(left);
        else list.add(null);
        if(pos.getRowIndex()+1 < maze.getRows()) list.add(down);
        else list.add(null);
        if(pos.getRowIndex()-1 > -1) list.add(up);
        else list.add(null);
        return list;
    }

    /**
     * The function runs the maze and find the best place for the GOAL state to be in
     * @param maze
     */
    private void setGoalAfterPrim(Maze maze)
    {
        int x1=(int)(Math.random() * (maze.getRows()));
        int y1=(int)(Math.random() * (maze.getColumns()));
        Position bestPos=new Position(x1,y1);
        while(!(maze.getCell(bestPos)==0&& !bestPos.equals(maze.getStartPosition())))
        {
            x1=(int)(Math.random() * (maze.getRows()));
            y1=(int)(Math.random() * (maze.getColumns()));
            bestPos=new Position(x1,y1);
        }

        for(int i=0;i<maze.getRows();i++)
        {
            for(int j=0;j<maze.getColumns();j++)
            {
                Position tempPos=new Position(i,j);
                if(maze.getCell(tempPos)==0&& !tempPos.equals(maze.getStartPosition()))
                {
                    double newP=Math.sqrt(Math.pow(maze.getStartPosition().getColumnIndex()-tempPos.getColumnIndex(),2)+Math.pow(maze.getStartPosition().getRowIndex()-tempPos.getRowIndex(),2));
                    double oldP=Math.sqrt(Math.pow(maze.getStartPosition().getColumnIndex()-bestPos.getColumnIndex(),2)+Math.pow(maze.getStartPosition().getRowIndex()-bestPos.getRowIndex(),2));
                    if(newP>oldP) bestPos=tempPos;
                }
            }
        }
        maze.setGoalPosition(bestPos.getRowIndex(),bestPos.getColumnIndex());
    }

    private void initializeMazeWithWalls(Position tempPos,Maze myMaze){
        for(int i=0;i<myMaze.getRows();i++)
        {
            for (int j = 0; j < myMaze.getColumns(); j++)
            {
                tempPos.setPosition(i, j);
                myMaze.setCell(tempPos, 1);
            }
        }
    }
}

