import java.net.*;
import java.io.IOException;

public class SimpleUDPServer {
    private static final int BUFFER_SIZE = 1024;
    
    public static void main(String[] args) throws IOException {
        int port = 8888;
        
        // Crea socket legato alla porta
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server UDP avviato sulla porta " + port);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            
            while (true) {
                // Prepara pacchetto per ricezione
                DatagramPacket receivePacket = new DatagramPacket(
                    buffer, buffer.length);
                
                // Riceve dati (operazione bloccante)
                socket.receive(receivePacket);
                
                // Estrae informazioni
                String message = new String(receivePacket.getData(), 
                                          0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                
                System.out.printf("Ricevuto da %s:%d - %s%n", 
                                clientAddress, clientPort, message);
                
                // Echo back al client
                String response = "Echo: " + message;
                byte[] responseData = response.getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(
                    responseData, responseData.length, 
                    clientAddress, clientPort);
                
                socket.send(sendPacket);
            }
        }
    }
}
