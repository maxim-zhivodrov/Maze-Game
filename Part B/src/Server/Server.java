package Server;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService executorService; //changed

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        executorService = Executors.newFixedThreadPool(Configurations.readNumOfThreads());//changed
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    // changed
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            serverStrategy(clientSocket);
                        }
                    });
//                    new Thread(() -> {
//                        serverStrategy(clientSocket);
//                    }).start();
                }
                catch (SocketTimeoutException e) {
                    System.out.println("Socket Timeout - No clients pending!");
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.MINUTES);
            serverSocket.close();
        }
        catch (IOException e) { }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void serverStrategy(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }


}


