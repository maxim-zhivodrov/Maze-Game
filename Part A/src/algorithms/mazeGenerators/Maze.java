package algorithms.mazeGenerators;

import javafx.geometry.Pos;

public class Maze
{
    private int[][] maze;
    private Position StartPosition;
    private Position GoalPosition;

    protected Maze(int rows, int columns, Position start,Position end)
    {
       maze=new int[rows][columns];
       for (int i=0;i<rows;i++)
           for (int j=0;j<columns;j++)
               maze[i][j]=0;
       StartPosition=start;
        GoalPosition=end;
    }

    public Position getStartPosition() {
        return StartPosition;
    }

    public void setStartPosition(int x, int y){
        StartPosition.setPosition(x,y);
    }

    public Position getGoalPosition() { return GoalPosition; }

    public void setGoalPosition(int x, int y){
        GoalPosition.setPosition(x,y);
    }

    public int getRows(){return maze.length;}

    public int getColumns(){return maze[0].length;}

    public void setCell (Position pos, int val)
    {
        maze[pos.getRowIndex()][pos.getColumnIndex()]=val;
    }

    public int getCell(Position pos)
    {
        if(pos==null)
            return -1;
        return maze[pos.getRowIndex()][pos.getColumnIndex()];
    }

    /**
     * \the function prints the maze
     */
    public void print ()
    {
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                if (i == StartPosition.getRowIndex() && j == StartPosition.getColumnIndex())
                {//startPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                }
                else if (i == GoalPosition.getRowIndex() && j == GoalPosition.getColumnIndex())
                {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                }
                else if (maze[i][j] == 1)
                    System.out.print(" " + "\u001B[45m" + " ");
                else
                    System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }

    }
}
