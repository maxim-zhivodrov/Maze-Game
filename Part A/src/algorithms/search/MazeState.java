package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState
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
        if(!(o instanceof MazeState)) return false;
        return this.position.equals(((MazeState) o).position);
    }

    @Override
    public void setPriority(double priority) {
        super.setPriority(priority);
    }

    public void setPriority(AState newState)
    {
        double x1=this.getPosition().getRowIndex();
        double y1=this.getPosition().getColumnIndex();
        double x2=((MazeState)newState).getPosition().getRowIndex();
        double y2=((MazeState)newState).getPosition().getColumnIndex();
        super.setPriority(Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2)));
    }

    public int comparePriority(AState newState)
    {
        if(this.getPriority()<newState.getPriority()) return -1;
        else if(this.getPriority()==newState.getPriority()) return 0;
        else return 1;
    }

    @Override
    public String toString() { return ""+position; }
}
