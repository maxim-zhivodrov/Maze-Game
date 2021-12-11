package algorithms.search;

import java.util.ArrayList;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    public abstract Solution solve(ISearchable domain);

    public abstract String getName();

    protected int numOfNodesEvaluated;
    public String getNumberOfNodesEvaluated()
    {
        return ""+numOfNodesEvaluated;
    }

    protected Solution getPath(Solution ans,ISearchable domain)
    {
        AState tempState=domain.getGoalState();
        AState PI=tempState.getPi();

        ArrayList<AState> tempList=new ArrayList<>();
        while(tempState!=null)
        {
            tempList.add(tempState);
            tempState=tempState.getPi();
        }
        for(int i=tempList.size()-1;i>-1;i--){
            ans.addState(tempList.get(i));
        }
        return ans;
    }
}
