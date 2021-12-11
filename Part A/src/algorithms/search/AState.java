package algorithms.search;

import java.util.Objects;

public abstract class AState
{

    private boolean visited;
    private double priority;

    public AState()
    {
        visited=false;
        priority=0;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return Objects.hash(Pi);
    }

    private AState Pi=null;

    public void printState(){}

    public void setPi(AState pi)
    {
        Pi = pi;
    }

    public AState getPi()
    {
        return Pi;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(AState newState) {
        this.priority = newState.getPriority();
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int comparePriority(AState newState)
    {
        return 0;
    }

}