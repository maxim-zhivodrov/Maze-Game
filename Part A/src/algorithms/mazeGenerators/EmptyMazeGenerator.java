package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rows, int columns)
    {
        if(rows<2 || columns<2) generate(30,30);
//        else if(rows==2 && columns <4 || columns==2 && rows <4)
//        {
//            Maze myMaze=new Maze(0,0,null,null);
//            myMaze.setStartPosition(0,0); myMaze.setGoalPosition(rows-1,columns-1);
//            return myMaze;
//        }
//        else if(rows==3 && columns ==3)
//        {
//            Maze myMaze=new Maze(0,0,null,null);
//            myMaze.setStartPosition(0,0);
//        }

        boolean goodPoints=true;
        int x1=(int)(Math.random() * (rows-1));
        int y1=(int)(Math.random() * (columns-1));
        int x2=(int)(Math.random() * (rows-1));
        int y2=(int)(Math.random() * (columns-1));

        if(rows==2 && columns <4 || columns==2 && rows <4) {x1=0;y1=0;x2=rows-1;y2=columns-1;}

        if(x1==x2 && y1==y2) goodPoints=false;
        while(!goodPoints)
        {
            x2=(int)(Math.random() * (rows-1));
            y2=(int)(Math.random() * (columns-1));
            if(x1==x2 && y1==y2) goodPoints=false;
            else goodPoints=true;
        }
        Position start=new Position(x1,y1);
        Position end=new Position(x2,y2);
//       Position start=new Position(0,0);
//       Position end=new Position(rows/2,columns/2);
        Maze maze=new Maze(rows,columns,start,end);
        return maze;
    }
}
