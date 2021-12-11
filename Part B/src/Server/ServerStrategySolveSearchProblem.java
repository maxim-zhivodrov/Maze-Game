package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy
{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException
    {
        ObjectInputStream IS=new ObjectInputStream(inFromClient);
        ObjectOutputStream OS = new ObjectOutputStream(outToClient);
        OS.flush();

        String tempDirectoryPath=System.getProperty("java.io.tmpdir");
        new File(tempDirectoryPath+"\\Mazes and Solutions").mkdir();
        Maze maze= (Maze) IS.readObject();
        String ID=""+maze.getRows()+maze.getColumns()+maze.getStartPosition().getRowIndex()+ maze.getStartPosition().getColumnIndex()+maze.getGoalPosition().getRowIndex()+ maze.getGoalPosition().getColumnIndex();
        File f = new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID);
        boolean MazeExists=false;
        byte[] originalMazeToByteArray=maze.toByteArray();

        ByteArrayOutputStream BS=new ByteArrayOutputStream();
        MyCompressorOutputStream Compressor=new MyCompressorOutputStream(BS);
        BS.flush();
        Compressor.write(originalMazeToByteArray);
        byte[] originalMazeAfterCompression=BS.toByteArray();
        BS.close();

        if(f.exists() && f.isDirectory()) {//CHECKS IF AN FOLDER WITH A SPECIFIC ID EXISTS
            for(File mazeTxt:f.listFiles()) {//ITERATES THOUGH ALL FILES IN THE ID FILE
                if(mazeTxt.getName().contains("Maze")) {
                    byte[] readMaze=Files.readAllBytes(mazeTxt.toPath());
                    if(Arrays.equals(readMaze,originalMazeAfterCompression)) {//CHECKS IF THE MAZE IN FILE EQUALS TO THE RECIEVED MAZE
                        Solution finalSol=readSolutionOfRecievedMaze(tempDirectoryPath,ID,mazeTxt.getName().substring(4));
                        OS.writeObject(finalSol);
                        MazeExists=true;
                        break;
                    }
                }
            }
            if(!MazeExists) {//ENTERS IF THE ID EXISTS BUT THE RECIEVED MAZE DOESNT
                Solution finalSol=solveMaze(maze);
                File counterTxt=new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID+"\\Counter.txt");
                BufferedReader bfReader=new BufferedReader(new FileReader(counterTxt));
                int counter=Integer.parseInt(bfReader.readLine());
                bfReader.close();
                createMazeAndSolTxt(originalMazeAfterCompression,finalSol,""+(++counter),ID,tempDirectoryPath); //MAY THROW EXCEPTION
                OS.writeObject(finalSol);
            }
        }
        else{ //ENTERS IF ID DOESNT EXISTS
            new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID).mkdir();
            Solution finalSol=solveMaze(maze);
            createMazeAndSolTxt(originalMazeAfterCompression,finalSol,"1",ID,tempDirectoryPath); //MAY THROW EXCEPTION
            OS.writeObject(finalSol);
        }
        OS.flush();
        OS.close();
    }

    //SIMPLY SOLVES THE MAZE
    private Solution solveMaze(Maze maze){
        SearchableMaze sm=new SearchableMaze(maze);
        ASearchingAlgorithm searchAlgorithm=Configurations.SolvingAlgorithmRead();
        Solution sol=searchAlgorithm.solve(sm);
        return sol;
    }
    //CREATES THE TEXT FILES FOR THE MAZE AND THE SOLUTION
    private void createMazeAndSolTxt(byte[] originalMazeAfterCompression,Solution Sol,String Counter,String ID,String tempDirectoryPath) throws IOException {

        //Counter writing
        File counterTxt=new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID+"\\Counter.txt");
        counterTxt.createNewFile();
        FileWriter FW=new FileWriter(counterTxt);
        FW.write(Counter);
        FW.close();
        //Maze writing
        File newMaze=new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID+"\\Maze"+Counter+".txt");
        newMaze.createNewFile();
        Files.write(newMaze.toPath(),originalMazeAfterCompression);
        //Solution writing
        File newSol=new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID+"\\Sol"+Counter+".txt");
        newSol.createNewFile();
        FW=new FileWriter(newSol);
        FW.write(Sol.toString());
        FW.close();


    }
    //READS THE SOLUTION OF A GIVEN MAZE FROM A TEXT FILE
    private Solution readSolutionOfRecievedMaze(String tempDirectoryPath,String ID,String MazeNumber) throws IOException {
        File solTxt=new File(tempDirectoryPath+"\\Mazes and Solutions\\"+ID+"\\Sol"+MazeNumber);
        LinkedList<String> stringListSol=new LinkedList<>();
        BufferedReader bfReader=new BufferedReader(new FileReader(solTxt));
        String line="";
        while((line=bfReader.readLine())!=null)
            stringListSol.add(line);
        bfReader.close();
        Solution sol=new Solution();
        for(int i=0;i<stringListSol.size();i++)
        {
            String temp=stringListSol.get(i);
            int row=Integer.parseInt(temp.substring(1,temp.indexOf(',')));
            int column=Integer.parseInt(temp.substring(temp.indexOf(',')+1,temp.indexOf('}')));
            sol.addState(new MazeState(new Position(row,column)));
        }
        return sol;
    }



}


