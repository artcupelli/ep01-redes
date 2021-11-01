
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

    public Client() {
    }

    public void conectaComServidor() throws UnknownHostException, IOException {

        // Conecta com socket TCP (responsavel por receber os comandos)
        Socket socket = new Socket(InetAddress.getLocalHost(), 6264);

        // Retornando o output para o server
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Pegando a entrada do usuario
        Scanner scanner = new Scanner(System.in);

        // Armazena entrada do usuario e seu nome
        String userInput, clientName = "empty";

        // Cria thread para recepção da música 
        ClientMusicRunnable clientMusicRunnable = new ClientMusicRunnable();
        new Thread(clientMusicRunnable).start();
        
        // Cria thread para envio dos comandos 
        ClientCommandRunnable clientCommandRunnable = new ClientCommandRunnable(socket);
        new Thread(clientCommandRunnable).start();


        // loop closes when user enters sair command
        do {

            if (clientName.equals("empty")) {
                System.out.print("\nColoque seu nome: ");
                userInput = scanner.nextLine();
                clientName = userInput;
                output.println(userInput);
                // this.apelido = userInput;
                // output.println(userInput);
                if (userInput.toLowerCase().trim().equals("sair")) {
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

}
