package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.Configurations;

import Server.ServerStrategySolveSearchProblem;
import Server.ServerStrategyGenerateMaze;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;



public class MyModel extends Observable implements IModel {

    private String directory;
    private Maze maze;
    private Solution sol;
    private boolean isMazeGenerated=false;
    private boolean isCharMoved=false;
    private boolean isSolved=false;
    private static final Logger LOG = LogManager.getLogger();


    public Server mazeGeneratingServer;
    public Server solveSearchProblemServer;


    public MyModel() {
        Configurator.setLevel(String.valueOf(LOG), Level.DEBUG);
        startServers();
    }

    public void startServers()
    {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        LOG.info("Server Started at port 5400!");
        LOG.info("Server's Strategy: Generate Maze");
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        LOG.info("Server Started at port 5401!");
        LOG.info("Server's Strategy: Solve Search Problem");

        LOG.info("Server is waiting for clients...");
    }

    public void stopServers()
    {
        mazeGeneratingServer.stop();
        LOG.info("The Maze Generating Server Stopped!");
        solveSearchProblemServer.stop();
        LOG.info("The Maze Solving Server Stopped!");

    }



    public void generateMaze(int rows, int columns)
    {
        isMazeGenerated=true;
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, columns};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[rows*columns+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            AMazeGenerator gen= Configurations.MazeGenerateRead();
            if(gen instanceof EmptyMazeGenerator&&client!=null){
                LOG.info("Client: "+client.toString()+" at port 5400 asked for a "+rows+"*"+columns+" size maze with Empty Maze Generator");
            }
            if(gen instanceof SimpleMazeGenerator &&client!=null){
                LOG.info("Client: "+client.toString()+" at port 5400 asked for a "+rows+"*"+columns+" size maze with Simple Maze Generator");
            }
            if(gen instanceof MyMazeGenerator&&client!=null){
                LOG.info("Client: "+client.toString()+" at port 5400 asked for a "+rows+"*"+columns+" size maze with My Maze Generator");
            }
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();

    }

    public void showSolution(int rowPos,int colPos) {
        if (maze!=null)
        {
            maze.setStartPosition(rowPos,colPos);
            isSolved=true;
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            toServer.writeObject(maze); //send maze to server
                            toServer.flush();
                            sol = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                            //Print Maze Solution retrieved from the server
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                ASearchingAlgorithm solver= Configurations.SolvingAlgorithmRead();
                if(solver instanceof BreadthFirstSearch&& !(solver instanceof BestFirstSearch) &&client!=null){
                    LOG.info("Client: "+client.toString()+" at port 5401 asked to solve a maze using the BreadthFirstSearch algorithm");
                }
                if(solver instanceof DepthFirstSearch &&client!=null){
                    LOG.info("Client: "+client.toString()+" at port 5401 asked to solve a maze using the DepthFirstSearch algorithm");
                }
                if(solver instanceof BestFirstSearch&&client!=null){
                    LOG.info("Client: "+client.toString()+" at port 5401 asked to solve a maze using the BestFirstSearch algorithm");
                }
                client.communicateWithServer();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            setChanged();
            notifyObservers();
        }
    }
    public void loadMaze(){
        //read maze from file
        try {
            byte savedMazeBytes[] = null;
            InputStream in = new MyDecompressorInputStream(new FileInputStream("savedMaze.maze"));
            savedMazeBytes = new byte[1000012];
            in.read(savedMazeBytes);
            in.close();
            this.maze=new Maze(savedMazeBytes);
            isMazeGenerated=true;
            setChanged();
            notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Maze getMaze() {
        return maze;
    }

    public Solution getSol() {
        return sol;
    }

    public void moveCharacter(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.NUMPAD8||keyEvent.getCode() == KeyCode.UP) {
            directory="up";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD2||keyEvent.getCode() == KeyCode.DOWN) {
            directory="down";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD6||keyEvent.getCode() == KeyCode.RIGHT) {
            directory="right";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD4||keyEvent.getCode() == KeyCode.LEFT) {
            directory="left";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD9) {
            directory="ur";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD3) {
            directory="dr";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD1) {
            directory="dl";
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD7) {
            directory="ul";
        }
        else{
            directory="";
        }
        if (!keyEvent.isControlDown()) {
            isCharMoved=true;
            setChanged();
            notifyObservers();
        }
    }



    public String getDirectory() {
        return directory;
    }


    public void setMazeGenerated(boolean mazeGenerated) {
        isMazeGenerated = mazeGenerated;
    }

    public void setCharMoved(boolean charMoved) {
        isCharMoved = charMoved;
    }

    public boolean isMazeGenerated() {
        return isMazeGenerated;
    }

    public boolean isCharMoved() {
        return isCharMoved;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void mouseDragUp(){
        directory="up";
        isCharMoved=true;
        setChanged();
        notifyObservers();
    }
    public void mouseDragDown(){
        directory="down";
        isCharMoved=true;
        setChanged();
        notifyObservers();
    }
    public void mouseDragRight(){
        directory="right";
        isCharMoved=true;
        setChanged();
        notifyObservers();
    }
    public void mouseDragLeft(){
        directory="left";
        isCharMoved=true;
        setChanged();
        notifyObservers();
    }

    public void saveMaze(int charCurrRow,int charCurrCol){
        if(this.maze!=null){
            try {
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream("savedMaze.maze"));
                this.maze.setStartPosition(charCurrRow,charCurrCol);
                out.write(this.maze.toByteArray());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Maze Saved Successfully!");
    }
}
