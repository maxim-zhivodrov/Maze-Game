package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch
{
    /**
     * The builder makes a BestFirstSearch while calculating the priority of each state
     */
    public BestFirstSearch()
    {
        numOfNodesEvaluated = 0;
        Comparator<AState> comp=new Comparator<AState>()
        {
            @Override
            public int compare(AState o1, AState o2)
            {
                return o1.comparePriority(o2);
            }
        };
        queue=new PriorityQueue<>(comp);
    }

    /**
     * The function finds a solution to a given searching problem using the Best First Search algorithm
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
            AState tempState=queue.remove();
            ArrayList<AState> tempList=domain.getAllPossibleStates(tempState);
            for(int i=0;i<tempList.size();i++)
            {
                if(!tempList.get(i).isVisited())
                {
                    tempList.get(i).setVisited(true);
                    tempList.get(i).setPi(tempState);
                    tempList.get(i).setPriority(domain.getGoalState());
                    queue.add(tempList.get(i));
                    numOfNodesEvaluated++;
                    if(tempList.get(i).equals(domain.getGoalState()))
                    {
                        domain.getGoalState().setPi(tempList.get(i).getPi());
                        breakWhile=true;
                    }
                }
            }
            if(breakWhile) break;
        }

        return getPath(ans,domain);
    }

    @Override
    public String getName()  {
        return "Best_First_Search";
    }

}
