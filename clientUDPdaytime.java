import java.io.*;
import java.net.*;

public class DaytimeClientV1 {
    // Indirizzo del server (localhost per test su stessa macchina)
    private static final String HOST = "localhost";
    
    // Porta su cui il server ascolta (deve corrispondere a quella del server)
    private static final int PORTA = 1313;
    
    // Timeout per la ricezione (5 secondi = 5000 millisecondi)
    private static final int TIMEOUT = 5000;
    
    public static void main(String[] args) {
        System.out.println("=== DAYTIME CLIENT V1.0 ===");
        
        // try-with-resources: chiude automaticamente il socket
        try (DatagramSocket socket = new DatagramSocket()) {
            
            // Imposta timeout: se non arriva risposta entro 5 secondi, lancia eccezione
            socket.setSoTimeout(TIMEOUT);
            
            // ─────────────────────────────────────────
            // PASSO 1: PREPARAZIONE E INVIO RICHIESTA
            // ─────────────────────────────────────────
            
            // Risolve il nome host in indirizzo IP
            InetAddress serverAddress = InetAddress.getByName(HOST);
            
            // Dati da inviare (stringa vuota per questo protocollo)
            byte[] dati = "".getBytes();
            
            // Crea il pacchetto destinato al server
            DatagramPacket packetInvio = new DatagramPacket(
                dati, dati.length, serverAddress, PORTA
            );
            
            System.out.println("Invio richiesta al server...");
            
            // Invia il pacchetto al server
            socket.send(packetInvio);
            
            // ─────────────────────────────────────────
            // PASSO 2: RICEZIONE E VISUALIZZAZIONE RISPOSTA
            // ─────────────────────────────────────────
            
            // Buffer per ricevere la risposta
            byte[] buffer = new byte[1024];
            
            // Crea il pacchetto per la ricezione
            DatagramPacket packetRisposta = new DatagramPacket(buffer, buffer.length);
            
            // BLOCCA qui fino all'arrivo della risposta (o timeout)
            socket.receive(packetRisposta);
            
            // Estrae la stringa dai byte ricevuti
            // Usa getLength() per leggere solo i byte effettivamente ricevuti
            String risposta = new String(
                packetRisposta.getData(), 0, packetRisposta.getLength()
            );
            
            // Visualizza la risposta
            System.out.println("Data/Ora server: " + risposta);
            
        } catch (SocketTimeoutException e) {
            // Eccezione specifica per timeout
            System.err.println("TIMEOUT: Server non risponde");
        } catch (Exception e) {
            // Altre eccezioni (host non trovato, errori I/O, ecc.)
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
