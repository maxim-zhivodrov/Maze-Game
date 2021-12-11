package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState  implements Serializable
{
    Position position;

    public MazeState(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void printState() {
        System.out.println(position.toString());
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)return false;
        if(!(o instanceof MazeState)) return false;
        return this.position.equals(((MazeState) o).position);
    }

    @Override
    protected void setPriority(double priority) {
        super.setPriority(priority);
    }

    protected void setPriority(AState newState)
    {
        double x1=this.getPosition().getRowIndex();
        double y1=this.getPosition().getColumnIndex();
        double x2=((MazeState)newState).getPosition().getRowIndex();
        double y2=((MazeState)newState).getPosition().getColumnIndex();
        super.setPriority(Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2)));
    }

    protected int comparePriority(AState newState)
    {
        if(this.getPriority()<newState.getPriority()) return -1;
        else if(this.getPriority()==newState.getPriority()) return 0;
        else return 1;
    }

    @Override
    public String toString() { return ""+position; }
}
