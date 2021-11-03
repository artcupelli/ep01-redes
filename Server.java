
import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
    Map<Integer, ClientInfo> activeClients;
    Menu menu;
    Fila filaDeMusica;

    public Server() {
        this.activeClients = new HashMap<Integer, ClientInfo>();
    }

    /**
     * Inicia o servidor
     * 
     * @throws IOException
     */
    void liga() throws IOException {

        // Abre socket apresentacao para os comandos
        ServerSocket socketApresentacaoComando = new ServerSocket(6264);

        // Abre socket apresentacao para a música
        ServerSocket socketApresentacaoMusica = new ServerSocket(6364);

        // Cria um menu (que lança os comandos)
        this.menu = new Menu();

        // Cria as listas de threads (de comandos)
        ArrayList<ServerCommandThread> commandThreadList = new ArrayList<>();

        // Cria a fila de músicas
        this.filaDeMusica = new Fila(commandThreadList);

        // Instancia thread de tocar músicas
        ServerMusicThread serverMusicThread = new ServerMusicThread(this);

        // Inicia thread de tocar música
        serverMusicThread.start();

        // Loop infinito do server
        while (true) {

            // Aceita conexão com cliente
            Socket socketComandos = socketApresentacaoComando.accept();
            Socket socketMusica = socketApresentacaoMusica.accept();

            // Inicia nova thread de ler comando do novo cliente
            ServerCommandThread serverCommandThread = new ServerCommandThread(commandThreadList, this, socketComandos,
                    socketMusica);
            commandThreadList.add(serverCommandThread);
            serverCommandThread.start();

        }
    }

    /**
     * Adiciona cliente na lista de clientes ativos
     * 
     * @param enderecoIP
     * @param nome
     * @param porta
     */
    public void adicionaCliente(InetAddress enderecoIP, String nome, int porta, Socket socketComando,
            Socket socketMusica) {

        // Cria um novo cliente
        ClientInfo cliente = new ClientInfo(enderecoIP, nome, porta, socketComando, socketMusica);

        // Adiciona o cliente no Map de clientes ativos
        this.activeClients.put(porta, cliente);

        // Imprime os clientes conectados
        this.imprimeClientes();

    }

    public void imprimeClientes() {
        // Imprime os clientes conectados
        System.out.println("CLIENTES CONECTADOS (" + this.activeClients.size()+ ")");
        this.activeClients.forEach((id, cli) -> System.out.println(id + " | " + cli.nome + " | " + cli.enderecoIP));
        System.out.println("\n");
    }

}
