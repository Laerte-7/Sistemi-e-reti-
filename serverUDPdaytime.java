import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DaytimeServerV1 {
    // Porta su cui il server ascolta (1313 per test, 13 è la porta standard daytime)
    private static final int PORTA = 1313;
    
    // Formattatore per visualizzare data e ora in formato leggibile
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static void main(String[] args) {
        System.out.println("=== DAYTIME SERVER V1.0 ===");
        System.out.println("Server avviato sulla porta " + PORTA);
        
        // try-with-resources: chiude automaticamente il socket alla fine
        try (DatagramSocket socket = new DatagramSocket(PORTA)) {
            
            // Loop infinito per gestire richieste multiple
            while (true) {
                // ─────────────────────────────────────────
                // PASSO 1: RICEZIONE RICHIESTA DAL CLIENT
                // ─────────────────────────────────────────
                
                // Buffer per contenere i dati ricevuti (max 1024 byte)
                byte[] buffer = new byte[1024];
                
                // Crea il pacchetto per la ricezione
                DatagramPacket packetRicevuto = new DatagramPacket(buffer, buffer.length);
                
                // BLOCCA qui fino all'arrivo di un datagramma
                socket.receive(packetRicevuto);
                
                // Estrae e stampa l'indirizzo IP del client
                System.out.println("Richiesta da: " + 
                    packetRicevuto.getAddress().getHostAddress());
                
                // ─────────────────────────────────────────
                // PASSO 2: PREPARAZIONE RISPOSTA
                // ─────────────────────────────────────────
                
                // Ottieni data/ora corrente formattata
                String dataOra = LocalDateTime.now().format(FORMATTER);
                
                // Converti la stringa in array di byte per l'invio
                byte[] dati = dataOra.getBytes();
                
                // ─────────────────────────────────────────
                // PASSO 3: INVIO RISPOSTA AL CLIENT
                // ─────────────────────────────────────────
                
                // Crea il pacchetto di risposta indirizzato al client
                // Usa indirizzo e porta del pacchetto ricevuto per rispondere
                DatagramPacket packetRisposta = new DatagramPacket(
                    dati, dati.length,
                    packetRicevuto.getAddress(),  // Indirizzo del client
                    packetRicevuto.getPort()       // Porta del client
                );
                
                // Invia il pacchetto
                socket.send(packetRisposta);
                
                // Log della risposta inviata
                System.out.println("Inviato: " + dataOra);
            }
            
        } catch (Exception e) {
            // Gestione errori (porta occupata, permessi, ecc.)
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
