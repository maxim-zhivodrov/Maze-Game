package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rows, int columns)
    {
        if((rows<=0)||(columns<=0)||(rows<2 && columns<2)){
            rows=30;
            columns=30;
        }
        if((rows==2&&columns==2)||rows==1||columns==1){
            Position start=new Position(0,0);
            Position end=new Position(rows-1,columns-1);
            Maze maze=new Maze(rows,columns,start,end);
            return maze;
        }
        boolean goodPoints=true;
        int x1=(int)(Math.random() * (rows));
        int y1=(int)(Math.random() * (columns));
        int x2=(int)(Math.random() * (rows));
        int y2=(int)(Math.random() * (columns));

        if(x1==x2 && y1==y2) goodPoints=false;
        while(!goodPoints)
        {
            x2=(int)(Math.random() * (rows));
            y2=(int)(Math.random() * (columns));
            if(x1==x2 && y1==y2) goodPoints=false;
            else goodPoints=true;
        }
        Position start=new Position(x1,y1);
        Position end=new Position(x2,y2);
//       Position start=new Position(1,1);
//       Position end=new Position(0,0);
        Maze maze=new Maze(rows,columns,start,end);
        return maze;
    }
}
