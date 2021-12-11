package algorithms.search;

import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable
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

    public void addState(AState newState)
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

    @Override
    public String toString() {
        String s="";
        for(int i=0;i<getSolutionPathLength();i++){
            s=s+SolutionPath.get(i).toString()+"\r\n";
        }
        return s;
    }
}
