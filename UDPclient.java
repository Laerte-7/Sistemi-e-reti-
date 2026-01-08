import java.net.*;
import java.io.IOException;
import java.util.Scanner;

public class SimpleUDPClient {
    public static void main(String[] args) throws IOException {
        String serverHost = "localhost";
        int serverPort = 8888;
        
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            Scanner scanner = new Scanner(System.in);
            
            System.out.println(" Client UDP - digita messaggi (quit per uscire)");
            
            String message;
            while (!(message = scanner.nextLine()).equals("quit")) {
                // Prepara e invia messaggio
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, serverAddress, serverPort);
                
                socket.send(sendPacket);
                
                // Riceve risposta
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer, receiveBuffer.length);
                
                socket.receive(receivePacket);
                
                String response = new String(receivePacket.getData(), 
                                           0, receivePacket.getLength());
                System.out.println("Risposta: " + response);
            }
        }
    }
}
