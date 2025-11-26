import java.io.*;
import java.net.*;

public class CalcolatriceServer {
    private static final int PORTA = 8080;
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
    System.out.println("Server avviato sulla porta " + PORTA);
   
         while (true) {
            Socket client = serverSocket.accept();
    
      }
      
     
        String[] parti = richiesta.split(" ");
        if (parti.length != 3) {
              return "ERRORE: Formato non valido";
        }
        double num1 = Double.parseDouble(parti[0]);
        String operazione = parti[1];
        double num2 = Double.parseDouble(parti[2]);
        switch (operazione) {
              case "+": return num1 + num2;
              case "-": return num1 - num2;
              case "*": return num1 * num2;
              case "/": 
        if (num2 == 0) throw new ArithmeticException("Divisione per zero");
              return num1 / num2;
              default: throw new IllegalArgumentException("Operazione non supportata");
        }
      }
    }
         
      
    


}
