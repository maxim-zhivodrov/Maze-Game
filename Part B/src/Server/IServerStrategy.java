package Server;

import java.io.*;

public interface IServerStrategy
{
    void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException;
}
