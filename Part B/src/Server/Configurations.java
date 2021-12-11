package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.*;
import java.util.Properties;

public class Configurations
{
    public static void main(String[] args){
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("NumOfThreads","4");
            prop.setProperty("AlgorithmToSolve","BEST");
            prop.setProperty("AlgorithmToBuild","MyMaze");

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static int readNumOfThreads(){
        int returnProp=-1;
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            returnProp=Integer.valueOf(prop.getProperty("NumOfThreads"));


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnProp;
    }

    //OUR ADDITIONS
    public static AMazeGenerator MazeGenerateRead(){
        AMazeGenerator returnGenerator=null;
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            String generatorType=prop.getProperty("AlgorithmToBuild");
            if(generatorType.equals("EmptyMaze"))
                returnGenerator=new EmptyMazeGenerator();
            else if(generatorType.equals("SimpleMaze"))
                returnGenerator=new SimpleMazeGenerator();
            else
                returnGenerator=new MyMazeGenerator();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnGenerator;
    }

    //OUR ADDITIONS
    public static ASearchingAlgorithm SolvingAlgorithmRead(){
        ASearchingAlgorithm returnAlgorithm=null;
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            String algorithmType=prop.getProperty("AlgorithmToSolve");
            if(algorithmType.equals("DFS"))
                returnAlgorithm=new DepthFirstSearch();
            else if(algorithmType.equals("BFS"))
                returnAlgorithm=new BreadthFirstSearch();
            else
                returnAlgorithm=new BestFirstSearch();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnAlgorithm;
    }

}
