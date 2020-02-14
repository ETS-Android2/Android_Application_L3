import constantes.EventEnum;
import metier.Identity;
import metier.Message;
import server.SocketServer;

public class Main {
    public static void main(String[] args) {
        final SocketServer server = new SocketServer();

        server.getServer().addEventListener(EventEnum.SEND.toString(), Message.class, (socketIOClient, s, ackRequest) -> {
            System.out.println("Message reçu : " + s.toString());
            server.sendAll(s.toString());
        });
        server.getServer().addEventListener(EventEnum.CONNEXION.toString(), Identity.class, (socketIOClient, id, ackRequest) ->
                System.out.println(id + " vient de se connecter")
        );

        server.start();
    }
}