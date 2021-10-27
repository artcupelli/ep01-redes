import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Servidor {
    Map<Integer, Cliente> clientesAtivos;
    int numeroClientesAtivos;

    public Servidor() {
        this.numeroClientesAtivos = 0;
        this.clientesAtivos = new HashMap<Integer, Cliente>();
    }
    
    void liga() throws IOException {
        ServerSocket socketApresentacao = new ServerSocket(6264);
        Tocador tocador = new Tocador();
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        ArrayList<ServerThread> threadList = new ArrayList<>();
        Fila fila = new Fila(threadList);

        while (true) {
            
            Socket socketConexao = socketApresentacao.accept();
            ServerThread serverThread = new ServerThread(socketConexao, threadList, menu, fila);
            threadList.add(serverThread);
            serverThread.start();

            InetAddress enderecoIPdoCliente = socketConexao.getInetAddress();

            this.adicionaCliente(enderecoIPdoCliente);

            //escutaComando(socketConexao, menu, fila);
            
            // TO-DO: Escutando os comandos

            

            // TODO: Verifica se cliente ta on (sem prioridade)
        }
    }

    void adicionaCliente(InetAddress enderecoIP) {
        this.numeroClientesAtivos++;

        int idCliente = (int) (Math.random()*10);

        Cliente cliente = new Cliente(idCliente, enderecoIP);

        this.clientesAtivos.put(idCliente, cliente);

        System.out.println("CLIENTES CONECTADOS "+ this.numeroClientesAtivos);
    
        this.clientesAtivos.forEach((id, cli) -> System.out.println(id + " | " + cli.getIPAdress()));
        
        System.out.println("\n");

    }

    boolean verificaSeClienteTaOn(){
        return true;
    }
    
}
