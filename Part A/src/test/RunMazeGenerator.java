package test;
import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args){
        //testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        //testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator)
    {
        System.out.println(String.format("Maze generation time(ms): %s",mazeGenerator.measureAlgorithmTimeMillis(100,100)));
        Maze maze=mazeGenerator.generate(100,100);
        maze.print();
        Position startPosition=maze.getStartPosition();
        System.out.println(String.format("Start Position: %s",startPosition));
        System.out.println(String.format("Goad Position: %s",maze.getGoalPosition()));

    }
}
