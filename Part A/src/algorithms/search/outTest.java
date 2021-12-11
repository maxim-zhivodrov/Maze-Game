package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;

public class outTest
{
    public static void main(String[] args)
    {
//        EmptyMazeGenerator myGenerator=new EmptyMazeGenerator();
//        SimpleMazeGenerator myGenerator=new SimpleMazeGenerator();
        MyMazeGenerator myGenerator = new MyMazeGenerator();
        long beforeCreation = System.currentTimeMillis();
        Maze maze = myGenerator.generate(3 , 3);
        long afterCreation = System.currentTimeMillis();
        System.out.println("THE MAZE:");
        maze.print();
        SearchableMaze sm = new SearchableMaze(maze);

        //BFS
        ISearchingAlgorithm BFS = new BreadthFirstSearch();
        long beforeSolveBFS = System.currentTimeMillis();
        Solution solutionBFS = BFS.solve(sm);
        long afterSolveBFS = System.currentTimeMillis();
        System.out.println("THE SOLUTION OF BFS:");
        solutionBFS.printSolution();

        //DFS
        ISearchingAlgorithm DFS = new DepthFirstSearch();
        long beforeSolveDFS = System.currentTimeMillis();
        Solution solutionDFS = DFS.solve(sm);
        long afterSolveDFS = System.currentTimeMillis();
        System.out.println("THE SOLUTION OF DFS:");
        solutionDFS.printSolution();

        //BEST
        ISearchingAlgorithm BEST = new BestFirstSearch();
        long beforeSolveBEST = System.currentTimeMillis();
        Solution solutionBEST = BEST.solve(sm);
        long afterSolveBEST = System.currentTimeMillis();
        System.out.println("THE SOLUTION OF BEST:");
        solutionBEST.printSolution();
        System.out.println("start point: " + maze.getStartPosition());
        System.out.println("goal point: " + maze.getGoalPosition());
        System.out.println("time it took to BUILD:" + ((double) (afterCreation - beforeCreation)));
        System.out.println("time it took to SOLVE BFS:" + ((double) (afterSolveBFS - beforeSolveBFS)));
        System.out.println("time it took to SOLVE DFS:" + ((double) (afterSolveDFS - beforeSolveDFS)));
        System.out.println("time it took to SOLVE BEST:" + ((double) (afterSolveBEST - beforeSolveBEST)));
    }
}
