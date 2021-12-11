package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy
{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        try {

            ObjectInputStream IS=new ObjectInputStream(inFromClient);
            ObjectOutputStream OS = new ObjectOutputStream(outToClient);
            OS.flush();
            //OS.close();

            int[] arr= (int[]) IS.readObject();

            AMazeGenerator myGenerator = Configurations.MazeGenerateRead();//changed
            Maze maze = myGenerator.generate(arr[0] , arr[1]);
            byte[] byteArr=maze.toByteArray();


            ByteArrayOutputStream bs=new ByteArrayOutputStream();
            MyCompressorOutputStream Compressor=new MyCompressorOutputStream(bs);
            bs.flush();
            Compressor.write(byteArr);
            byte[] mazeAfterCompression=bs.toByteArray();
            OS.writeObject(mazeAfterCompression);
            bs.close();
            OS.flush();
            OS.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
