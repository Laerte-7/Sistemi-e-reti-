import java.io.*;
import java.net.*;

public class CalcolatriceServer {
    private static final int PORTA = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Server avviato sulla porta " + PORTA);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connesso");

                
                Thread t = new Thread(new ClientHandler(client));
                t.start();
            }

        } catch (IOException e) {
            System.err.println("Errore server: " + e.getMessage());
        }
    }
}


class ClientHandler implements Runnable {
    private Socket client;

    ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            String richiesta = in.readLine();
            System.out.println("Richiesta del client: " + richiesta);

            try {
                String risposta = calcola(richiesta);
                out.println("Risultato: " + risposta);
            } catch (Exception e) {
                out.println("ERRORE: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Errore nella comunicazione col client: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Errore chiusura client: " + e.getMessage());
            }
        }
    }

    private static String calcola(String richiesta) {
        if (richiesta == null) {
            throw new IllegalArgumentException("Richiesta vuota");
        }

        String[] parti = richiesta.split(" ");
        if (parti.length != 3) {
            throw new IllegalArgumentException("Formato atteso: num1 operatore num2");
        }

        double num1 = Double.parseDouble(parti[0]);
        String operazione = parti[1];
        double num2 = Double.parseDouble(parti[2]);

        switch (operazione) {
            case "+": return String.valueOf(num1 + num2);
            case "-": return String.valueOf(num1 - num2);
            case "*": return String.valueOf(num1 * num2);
            case "/":
                if (num2 == 0) throw new ArithmeticException("Divisione per zero");
                return String.valueOf(num1 / num2);
            default:
                throw new IllegalArgumentException("Operazione non supportata");
        }
    }
}
