package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable
{
    private Maze maze;
    private MazeState[][]exploredArr;
    private MazeState startState;
    private MazeState goalState;

    public SearchableMaze(Maze maze)
    {
        this.maze=maze;
        this.startState=new MazeState(maze.getStartPosition());
        this.goalState=new MazeState(maze.getGoalPosition());
        exploredArr=new MazeState[maze.getRows()][maze.getColumns()];
        for(int i=0;i<maze.getRows();i++)
        {
            for(int j=0;j<maze.getColumns();j++)
                exploredArr[i][j]=new MazeState(new Position(i,j));
        }
        for(int i=0;i<maze.getRows();i++)
        {
            for(int j=0;j<maze.getColumns();j++)
            {
                if(exploredArr[i][j].equals(startState))
                    exploredArr[i][j].setVisited(true);
            }
        }
    }

    @Override
    public AState getStartState()
    {
        return startState;
    }

    @Override
    public AState getGoalState()
    {
        return goalState;
    }

    /**
     * The function return all the possible states that can be reached from the current state
     * @param currentState
     * @return
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState currentState)
    {
        boolean rightBool=false,leftBool=false,upBool=false,downBool=false;
        ArrayList<AState> states = new ArrayList<>();
        Position up=null,down=null,right=null,left=null,pos=null;
        pos= ((MazeState)currentState).getPosition();

        if(pos.getColumnIndex()+1< maze.getColumns())
            right=exploredArr[pos.getRowIndex()][pos.getColumnIndex()+1].getPosition();
        if(pos.getColumnIndex()-1 > -1)
            left=exploredArr[pos.getRowIndex()][pos.getColumnIndex()-1].getPosition();
        if(pos.getRowIndex()-1 > -1)
            up=exploredArr[pos.getRowIndex()-1][pos.getColumnIndex()].getPosition();
        if(pos.getRowIndex()+1 < maze.getRows())
            down=exploredArr[pos.getRowIndex()+1][pos.getColumnIndex()].getPosition();

        ArrayList<AState> hypStates=getHypotenuses(right,left,up,down);

        if(up!=null && maze.getCell(up)==0)
            states.add(exploredArr[up.getRowIndex()][up.getColumnIndex()]); //else states.add(null);
        if(hypStates.get(0)!=null)states.add(hypStates.get(0));
        if(right!=null && maze.getCell(right)==0)
            states.add(exploredArr[right.getRowIndex()][right.getColumnIndex()]); //else states.add(null);
        if(hypStates.get(1)!=null)states.add(hypStates.get(1));
        if(down!=null && maze.getCell(down)==0)
            states.add(exploredArr[down.getRowIndex()][down.getColumnIndex()]); //else states.add(null);
        if(hypStates.get(2)!=null)states.add(hypStates.get(2));
        if((left!=null && maze.getCell(left)==0))
            states.add(exploredArr[left.getRowIndex()][left.getColumnIndex()]); //else states.add(null);
        if(hypStates.get(3)!=null)states.add(hypStates.get(3));

       //initialize Array if finished
        if(states.contains(this.goalState))
        {
            int goalIndex=states.indexOf(this.goalState);
            ArrayList<AState> tempAns=new ArrayList<>();
            tempAns.add(states.get(goalIndex));
            InitializeArray();
            return tempAns;
        }
        return states;
    }

    /**
     * The function returns a copy of the given array
     * @param states
     * @return
     */
    private ArrayList<AState> cloneArray(ArrayList<AState> states)
    {
        ArrayList<AState> Arr =new ArrayList<AState>();

        for(int i=0;i<states.size();i++)
        {
            AState state=new MazeState(((MazeState)states.get(i)).getPosition());
            Arr.add(state);
        }

        return Arr;
    }

    /**
     * The function returns all the states that are reachable to the current state by going in a single diagonal move
     * @param right
     * @param left
     * @param up
     * @param down
     * @return
     */
    private ArrayList<AState> getHypotenuses(Position right,Position left,Position up,Position down)
    {
        ArrayList<AState> hypStates=new ArrayList<>();
        for(int i=0;i<hypStates.size();i++) hypStates.set(i,null);
        MazeState ur=null,ul=null,dr=null,dl=null;
        if((right!=null&&up!=null)&&((maze.getCell(up)==0) || (maze.getCell(right)==0))&&
                maze.getCell(exploredArr[right.getRowIndex()-1][up.getColumnIndex()+1].getPosition())==0)
        {
            ur=exploredArr[right.getRowIndex()-1][up.getColumnIndex()+1];
            hypStates.add(ur);
        }
        else hypStates.add(null);
        if ((right!=null&&down!=null)&&((maze.getCell(down)==0) || (maze.getCell(right)==0))&&
                maze.getCell(exploredArr[right.getRowIndex()+1][down.getColumnIndex()+1].getPosition())==0)
        {
            dr=exploredArr[right.getRowIndex()+1][down.getColumnIndex()+1];
            hypStates.add(dr);
        }
        else hypStates.add(null);
        if((down!=null&&left!=null)&&((maze.getCell(down)==0) || (maze.getCell(left)==0))&&
                maze.getCell(exploredArr[left.getRowIndex()+1][down.getColumnIndex()-1].getPosition())==0)
        {
            dl=exploredArr[left.getRowIndex()+1][down.getColumnIndex()-1];
            hypStates.add(dl);
        }
        else hypStates.add(null);

        if((up!=null&&left!=null)&&((maze.getCell(up)==0) || (maze.getCell(left)==0))&&
                maze.getCell(exploredArr[left.getRowIndex()-1][up.getColumnIndex()-1].getPosition())==0)
        {
            ul=exploredArr[left.getRowIndex()-1][up.getColumnIndex()-1];
            hypStates.add(ul);
        }
        else hypStates.add(null);
        return hypStates;
    }

    /**
     * The function initializes ths maze with a VISITED value of false and a PRIORITY value of 0
     */
    private void InitializeArray (){

        for (int j = 0; j < maze.getRows(); j++){
            for (int k = 0; k < maze.getColumns(); k++)
            {
                exploredArr[j][k].setVisited(false);
                exploredArr[j][k].setPriority(0);
            }
        }
        exploredArr[startState.getPosition().getRowIndex()][startState.getPosition().getColumnIndex()].setVisited(true);
    }

}