package algorithms.mazeGenerators;

import javafx.geometry.Pos;

import java.io.Serializable;
import java.util.Stack;

public class Maze implements Serializable
{
    private int[][] maze;
    private Position StartPosition;
    private Position GoalPosition;

    public Maze(byte[] bytes)
    {
        int[] intArray=new int[12];
        for(int i=0;i<12;i++)
        {
            if(bytes[i]<0)
                intArray[i]=bytes[i]+256;
            else
                intArray[i]=bytes[i];
        }
        maze=new int[intArray[0]*255+intArray[1]][intArray[2]*255+intArray[3]];
        int index=12;
        for (int i=0;i<intArray[0]*255+intArray[1]&&index<bytes.length;i++){
            for (int j=0;j<intArray[2]*255+intArray[3]&&index<bytes.length;j++)
            {
                maze[i][j]=bytes[index];
                index++;
            }
        }

        Position start=new Position(intArray[4]*255+intArray[5],intArray[6]*255+intArray[7]);
        Position end=new Position(intArray[8]*255+intArray[9],intArray[10]*255+intArray[11]);
        StartPosition=start;
        GoalPosition=end;
    }

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

    protected void setGoalPosition(int x, int y){
        GoalPosition.setPosition(x,y);
    }

    public int getRows(){return maze.length;}

    public int getColumns(){return maze[0].length;}

    protected void setCell (Position pos, int val)
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
        Position temp=new Position(0,0);

        for(int k = 0; k < getColumns()*2+1; k++)
            System.out.print("-");
        System.out.println();

        for (int i=0;i<getRows();i++)
        {
            for (int j = 0; j < getColumns(); j++)
            {
                temp.setPosition(i, j);
                if(j==0) System.out.print("|");
                if (temp.equals(getStartPosition()))
                    System.out.print("S");
                else if (temp.equals(getGoalPosition()))
                    System.out.print("E");
                else System.out.print(getCell(temp));
                System.out.print("|");
            }
            System.out.println();
            for(int k = 0; k < getColumns()*2+1; k++)
                System.out.print("-");
            System.out.println();
        }
    }

    // first=inputNumber/255
    // second=inputNumber-(first*255)
    // inputNumber=255*first+second
    public byte[] toByteArray()
    {

        byte[]ans=new byte[maze.length*maze[0].length+12];

        //indexes 0,1 are raws
        ans[0]=(byte)(maze.length/255);
        ans[1]= (byte) (maze.length-255*ans[0]);
        //indexes 2,3 are columns
        ans[2]=(byte)(maze[0].length/255);
        ans[3]= (byte) (maze[0].length-255*ans[2]);
        //indexes 4,5 are startPosition raw
        ans[4]=(byte)(StartPosition.getRowIndex() /255);
        ans[5]= (byte) (StartPosition.getRowIndex()-255*ans[4]);
        //indexes 6,7 are startPosition column
        ans[6]=(byte)(StartPosition.getColumnIndex() /255);
        ans[7]= (byte) (StartPosition.getColumnIndex()-255*ans[6]);
        //indexes 8,9 are goalPosition raw
        ans[8]=(byte)(GoalPosition.getRowIndex() /255);
        ans[9]= (byte) (GoalPosition.getRowIndex()-255*ans[8]);
        //indexes 10,11 are goalPosition column
        ans[10]=(byte)(GoalPosition.getColumnIndex() /255);
        ans[11]= (byte) (GoalPosition.getColumnIndex()-255*ans[10]);

        int freeIndex=12;
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                ans[freeIndex]= (byte) maze[i][j];
                freeIndex++;
            }
        }

        return ans;
    }

    public String byteArrayToString(){
        String s="";
        byte[] b=toByteArray();
        for(int i=0;i<b.length-1;i++)
            s=s+b[i]+" ";
        s=s+b[b.length-1];
        return s;
    }


}
