package algorithms.search;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    Stack<AState> stack;

    public DepthFirstSearch() {
        numOfNodesEvaluated = 0;
        stack=new Stack<AState>();
    }

    @Override
    public Solution solve(ISearchable domain) {

        Solution ans=new Solution();
        if(domain.getStartState().equals(domain.getGoalState())) //IF START AND GOAL ARE THE SAME
        {
            ans.addState(domain.getGoalState());
            return ans;
        }

        AState tempState=domain.getStartState();
        stack.push(tempState);
        boolean breakWhile=false;

        while(!stack.empty())
        {
            tempState=stack.pop();
            if(!tempState.isVisited())
            {
                numOfNodesEvaluated++;
                tempState.setVisited(true);
                ArrayList<AState> Neighbors=domain.getAllPossibleStates(tempState);
                breakWhile=stackPushFromArrayList(Neighbors,stack,tempState,domain);
            }

            if(breakWhile) break;
        }
        return getPath(ans,domain);
    }

    @Override
    public String getName()  {
        return "Deapth_First_Search";
    }

    /**
     * The function inserts the neighbors to the stack and returns whether or not one of the neighbors is the goal
     * @param list
     * @param stack
     * @param currState
     * @param domain
     * @return
     */
    private boolean stackPushFromArrayList(ArrayList<AState> list,Stack<AState> stack,AState currState,ISearchable domain)
    {
        boolean flag=false;
        for(int i=list.size()-1;i>-1;i--)
        {
            if(!list.get(i).isVisited())
            {
                if(list.get(i).equals(domain.getGoalState()))
                {
                    domain.getGoalState().setPi(currState);
                    flag=true;
                    break;
                }
                list.get(i).setPi(currState);
                stack.push(list.get(i));
            }
        }
        return flag;
    }
}
