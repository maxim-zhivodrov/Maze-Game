package algorithms.search;

import java.util.ArrayList;

public class Solution
{
    private ArrayList<AState> SolutionPath;

    public Solution()
    {
        SolutionPath=new ArrayList<AState>();
    }

    public ArrayList<AState> getSolutionPath()
    {
        return SolutionPath;
    }

    protected void addState(AState newState)
    {
        SolutionPath.add(newState);
    }

    /**
     * The function print the complete path of the solution
     */
    public void printSolution()
    {
        for(int i=0;i<SolutionPath.size();i++)
        {
            SolutionPath.get(i).printState();
        }
    }

    public int getSolutionPathLength() {
        return SolutionPath.size();
    }
}
