package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator
{
    /**
     * The function buils a maze path using a basic algorithm of growing toward the position of the GoalState
     * @param rows
     * @param columns
     * @return
     */
    @Override
    public Maze generate(int rows, int columns)
    {
        EmptyMazeGenerator emptyMaze=new EmptyMazeGenerator();
        Maze simpleMaze=emptyMaze.generate(rows,columns);
        Position temp=new Position(simpleMaze.getStartPosition().getRowIndex(),simpleMaze.getStartPosition().getColumnIndex());
        simpleMaze.setCell(simpleMaze.getStartPosition(),2);
        simpleMaze.setCell(simpleMaze.getGoalPosition(),2);
        while(!temp.equals(simpleMaze.getGoalPosition()))
        {
            int randomNumber=(int)(Math.round(Math.random()));
            if(!(checkIfLeft(randomNumber,temp,simpleMaze))){
                if(!(checkIfRight(randomNumber,temp,simpleMaze))){
                    checkIfSame(randomNumber,temp,simpleMaze);
                }
            }
            simpleMaze.setCell(temp,2);
        }
        randomizeWalls(simpleMaze);
        return simpleMaze;
    }

    /**
     * The function randomly puts walls in the maze in order to make it more legible
     * @param simpleMaze
     */
    private void randomizeWalls(Maze simpleMaze)
    {
        Position temp=new Position(0,0);
        for(int i=0;i<simpleMaze.getRows();i++)
        {
            for (int j = 0; j < simpleMaze.getColumns(); j++)
            {
                temp.setPosition(i, j);
                if (simpleMaze.getCell(temp) != 2)
                    simpleMaze.setCell(temp, (int) (Math.round(Math.random())));
                else
                    simpleMaze.setCell(temp, 0);
            }
        }
    }

    private boolean checkIfLeft(int randomNumber,Position temp, Maze simpleMaze){
        if(temp.getColumnIndex()<simpleMaze.getGoalPosition().getColumnIndex())
        {
            if(randomNumber==0 || temp.getRowIndex()==simpleMaze.getGoalPosition().getRowIndex())
                temp.setPosition(temp.getRowIndex(),temp.getColumnIndex()+1);
            else
            {
                if(temp.getRowIndex()<simpleMaze.getGoalPosition().getRowIndex())
                    temp.setPosition(temp.getRowIndex()+1,temp.getColumnIndex());
                else temp.setPosition(temp.getRowIndex()-1,temp.getColumnIndex());
            }
            return true;
        }
        return false;
    }
    private boolean checkIfRight(int randomNumber,Position temp,Maze simpleMaze){
        if(temp.getColumnIndex()>simpleMaze.getGoalPosition().getColumnIndex())
        {
            if(randomNumber==0 || temp.getRowIndex()==simpleMaze.getGoalPosition().getRowIndex())
                temp.setPosition(temp.getRowIndex(),temp.getColumnIndex()-1);
            else
            {
                if(temp.getRowIndex()<simpleMaze.getGoalPosition().getRowIndex())
                    temp.setPosition(temp.getRowIndex()+1,temp.getColumnIndex());
                else temp.setPosition(temp.getRowIndex()-1,temp.getColumnIndex());
            }
            return true;
        }
        return false;
    }
    private boolean checkIfSame(int randomNumber,Position temp,Maze simpleMaze){
        if(temp.getColumnIndex()==simpleMaze.getGoalPosition().getColumnIndex())
        {
            if(temp.getRowIndex()>simpleMaze.getGoalPosition().getRowIndex())
                temp.setPosition(temp.getRowIndex()-1,temp.getColumnIndex());
            else
                temp.setPosition(temp.getRowIndex()+1,temp.getColumnIndex());
            return true;
        }
       return false;
    }
}


