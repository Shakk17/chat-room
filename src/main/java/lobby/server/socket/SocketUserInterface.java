package lobby.server.socket;

public class SocketUserInterface {

    /**
     * Will handle the socket connectivity, reference needed to send and receive messages.
     * @see SocketServer
     */
    private SocketServer socketServer;

    public SocketUserInterface(SocketServer socketServer) {
        this.socketServer = socketServer;
    }

    public void printEvent(String action) {/*
        try {
            socketServer.send(new PrintActionMessage(action));
        } catch (IOException e) {
            socketServer.handleDisconnection();
        }*/
    }
}
