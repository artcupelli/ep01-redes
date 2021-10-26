
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    private int id;
    private InetAddress enderecoIP;
    private String apelido;

    public Cliente(int id, InetAddress enderecoIP){
        this.enderecoIP = enderecoIP;
        this.id = id;
    }

    public Cliente(){};
    
    public void conectaComServidor() throws UnknownHostException, IOException{
        Socket socket = new Socket(InetAddress.getLocalHost(), 6264);
        Menu menu = new Menu();

            // reading the input from server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // returning the output to the server : true statement is to flush the buffer
            // otherwise
            // we have to do it manuallyy
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // taking the user input
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String response;
            String clientName = "empty";

            ClientRunnable clientRun = new ClientRunnable(socket);

            new Thread(clientRun).start();
            
            // loop closes when user enters sair command
            do {

                if (clientName.equals("empty")) {
                    System.out.print("\nColoque seu nome: ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    this.apelido = userInput;
                    // output.println(userInput);
                    if (userInput.equals("sair") || userInput.equals("SAIR")) {
                        socket.close();
                        break;
                    }
                } else {
                    String message = ("(" + clientName + ")" + " message: ");
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println("(" + clientName + ") " + userInput);
                    output.println(userInput);
                    if (userInput.equals("sair")) {
                        // reading the input from server
                        socket.close();
                        break;
                    }
                }

            } while (!userInput.equals("sair"));

    }

    public InetAddress getIPAdress(){
        return this.enderecoIP;
    }
}
