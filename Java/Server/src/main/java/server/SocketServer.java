package server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import constantes.EventEnum;

public class SocketServer {

    private SocketIOServer server;

    public SocketServer() {
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);
        this.server = new SocketIOServer(config);
        this.server.addConnectListener(socketIOClient -> System.out.println("New client detected !"));
    }

    public SocketIOServer getServer() {
        return server;
    }

    public void start() {
        this.server.start();
        System.out.println("Serveur lancé");
    }

    public void stop() {
        this.server.stop();
        System.out.println("Serveur arreté");
    }

    public void sendAll(String msg) {
        this.server.getBroadcastOperations().sendEvent(String.valueOf(EventEnum.RECEIVE), msg);
    }
}
