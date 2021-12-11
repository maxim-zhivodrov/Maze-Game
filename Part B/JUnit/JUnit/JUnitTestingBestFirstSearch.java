package JUnit;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {
    private BestFirstSearch BEST=new BestFirstSearch();

    @Test
    void solve() {
        MyMazeGenerator myGenerator = new MyMazeGenerator();
        Maze maze = myGenerator.generate(100 , 100);
        SearchableMaze sm = new SearchableMaze(maze);
        assertNull(BEST.solve(null));
        assertNotNull(BEST.solve(sm));
    }

    @Test
    void getName() {
        assertEquals("Best_First_Search",BEST.getName());
        assertNotEquals("TEST",BEST.getName());
    }

}