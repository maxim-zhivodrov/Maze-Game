package algorithms.search;

import java.io.Serializable;
import java.util.Objects;

public abstract class AState  implements Serializable
{

    private boolean visited;
    private double priority;
    private AState Pi;


    public AState()
    {
        visited=false;
        priority=0;
        Pi=null;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return Objects.hash(Pi);
    }


    public abstract void printState();

    protected void setPi(AState pi)
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

    protected void setPriority(AState newState) {
        this.priority = newState.getPriority();
    }

    protected void setPriority(double priority) {
        this.priority = priority;
    }

    public boolean isVisited() {
        return visited;
    }

    protected void setVisited(boolean visited) {
        this.visited = visited;
    }

    protected abstract int comparePriority(AState newState);

}