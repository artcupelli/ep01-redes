
import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
    Map<Integer, ClientInfo> activeClients;
    int numActiveClients;

    public Server() {
        this.activeClients = new HashMap<Integer, ClientInfo>();
        this.numActiveClients = 0;
    }

    /**
     * Inicia o servidor
     * @throws IOException
     */
    void liga() throws IOException {

        // Abre socket apresentacao
        ServerSocket socketApresentacao = new ServerSocket(6264);

        // Cria um menu (que lança os comandos)
        Menu menu = new Menu();

        // Cria as listas de threads (de comandos)
        ArrayList<ServerCommandThread> commandThreadList = new ArrayList<>();

        // Cria a fila de músicas
        Fila filaDeMusica = new Fila(commandThreadList);

        // Loop infinito do server
        while (true) {

            // Aceita conexão
            Socket socketConexao = socketApresentacao.accept();

            // Inicia thread de ler comando
            ServerCommandThread serverCommandThread = new ServerCommandThread(socketConexao, commandThreadList, menu,
                    filaDeMusica, this);
            commandThreadList.add(serverCommandThread);
            serverCommandThread.start();

            // Inicia thread de tocar músicas
            ServerMusicThread serverMusicThread = new ServerMusicThread(filaDeMusica);
            serverMusicThread.start();
        }
    }

    /**
     * Adiciona cliente na lista de clientes ativos
     * @param enderecoIP
     * @param nome
     * @param porta
     */
    public void adicionaCliente(InetAddress enderecoIP, String nome, int porta) {

        // Soma no número de clientes ativos
        this.numActiveClients++;

        // Cria um ID aleatório para o cliente
        int randomID = (int) (Math.random() * 1000);

        // Cria um novo cliente
        ClientInfo cliente = new ClientInfo(enderecoIP, nome, porta);

        // Adiciona o cliente no Map de clientes ativos
        this.activeClients.put(randomID, cliente);

        // Imprime os clientes conectados
        System.out.println("CLIENTES CONECTADOS " + this.numActiveClients);
        this.activeClients.forEach((id, cli) -> System.out.println(id + " | " + nome + " | " + enderecoIP));
        System.out.println("\n");

    }

}
