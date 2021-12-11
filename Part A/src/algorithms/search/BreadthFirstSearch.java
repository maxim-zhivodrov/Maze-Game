package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected Queue<AState> queue;

    public BreadthFirstSearch()
    {
        numOfNodesEvaluated = 0;
        queue=new LinkedList<AState>();
    }

    /**
     * The function finds a solution to a given searching problem using the Breadth First Search algorithm
     * @param domain
     * @return
     */
    @Override
    public Solution solve(ISearchable domain)
    {

        Solution ans=new Solution();

        if(domain.getStartState().equals(domain.getGoalState()))
        {
            ans.addState(domain.getGoalState());
            return ans;
        }

        queue.add(domain.getStartState());
        boolean breakWhile=false;
        while(!queue.isEmpty())
        {
            AState tempState=((LinkedList<AState>) queue).getFirst();
            ArrayList<AState> tempList=domain.getAllPossibleStates(tempState);
            for(int i=0;i<tempList.size();i++)
            {
                if(!tempList.get(i).isVisited())
                {
                    tempList.get(i).setVisited(true);
                    tempList.get(i).setPi(tempState);
                    queue.add(tempList.get(i));
                    numOfNodesEvaluated++;
                    if((tempList.get(i).equals(domain.getGoalState())))
                    {
                        domain.getGoalState().setPi(tempList.get(i).getPi());
                        breakWhile=true;
                    }
                }
            }
            queue.remove();
            if(breakWhile) break;
        }

        return getPath(ans,domain);
    }

    @Override
    public String getName() {
        return "Breadth_First_Search";
    }

}
