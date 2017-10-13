package lobby.networking.server.socket;

import lobby.Console;
import lobby.networking.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles all incoming socket connections, starts a thread for each connection.
 */
public class MainSocketServer extends Thread {

    private static final String SERVER_NAME = "[SOCKET SERVER]: ";

    /**
     * Executor used to have a ready pool of thread to handle incoming connections.
     */
    private ExecutorService executor;

    /**
     * Waits for new incoming connections, accept each new connection starting a dedicated thread.
     */
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(Server.SOCKET_PORT)) {
            this.setName(SERVER_NAME);

            //Pool of executors ready to handle all client connections
            executor = Executors.newCachedThreadPool();
            serverSocket.setSoTimeout(3000);

            while (Server.getServerInstance().isServerActive())
                waitConnection(serverSocket);

            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
            throw new AssertionError("Error while closing the socket!", e);
        } finally {
            if (Server.getServerInstance().isServerActive()) {
                new Thread(this).start();           //If main server class is still online I'll restart the SocketServer
                Thread.currentThread().interrupt();        //and interrupt the previous SocketServer thread
            }
        }
    }

    /**
     * Waits for an incoming connection to the serverSocket.
     * @throws IOException
     */
    private void waitConnection(ServerSocket serverSocket) throws IOException {
        Socket socket;
        try {
            socket = serverSocket.accept();
            Console.write(this.getName() + "Received a new client connection.");
            executor.submit(new SocketServer(socket));
        } catch (SocketTimeoutException | SocketException e) {
            //no actions here timeout serve only to block periodically the accept operation
        }
    }

    /**
     * Terminates all sockets threads.
     */
    public void terminate(){
        executor.shutdownNow();
    }
}
