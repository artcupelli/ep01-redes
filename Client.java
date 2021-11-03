
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public Client() {
    }

    /**
     * Conecta novo cliente com o servidor
     * 
     * @throws UnknownHostException
     * @throws IOException
     */
    public void conectaComServidor() throws UnknownHostException, IOException {

        // Conecta com socket TCP (responsavel por receber os comandos)
        Socket socket = new Socket(InetAddress.getLocalHost(), 6264);

        // Cria output para o server
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Pega a entrada do usuario
        Scanner scanner = new Scanner(System.in);

        // Armazena entrada do usuario e seu nome
        String entradaUsuario, clientName = "vazio";

        // Cria thread para recepção da música
        ClientMusicRunnable clientMusicRunnable = new ClientMusicRunnable();
        new Thread(clientMusicRunnable).start();

        // Cria thread para envio dos comandos
        ClientCommandRunnable clientCommandRunnable = new ClientCommandRunnable(socket);
        new Thread(clientCommandRunnable).start();

        // Enquanto o usuario nao digitar 'sair' continua pegando os comandos
        do {

            // Se o usuario ainda nao inseriu um nome
            if (clientName.equals("vazio")) {

                // Solicita o nome
                System.out.print("\nColoque seu nome: ");
                entradaUsuario = scanner.nextLine();
                clientName = entradaUsuario;
                output.println(entradaUsuario);

                // Se digitar 'sair' termina a aplicacao
                if (entradaUsuario.toLowerCase().trim().equals("sair")) {
                    socket.close();
                    break;
                }

                // Caso contrario (ja tem nome)
            } else {

                // Imprime cabecalho da mensagem
                System.out.println("(" + clientName + ")" + " mensagem: ");

                // Pega proximo comando
                entradaUsuario = scanner.nextLine();

                // Imprime comando para no servidor
                output.println("(" + clientName + ") " + entradaUsuario);

                // Manda comando para o servidor
                output.println(entradaUsuario);

                // Se o comando for 'sair', fechar tudo
                if (entradaUsuario.equals("sair")) {
                    socket.close();
                    scanner.close();
                    break;
                }
            }

        } while (!entradaUsuario.equals("sair"));

    }

}
