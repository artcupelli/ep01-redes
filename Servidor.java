import java.io.IOException;
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
        ServerSocket socketApresentacao = new ServerSocket(6262);

        while (true) {

            Socket socketConexao = socketApresentacao.accept();

            InetAddress enderecoIPdoCliente = socketConexao.getInetAddress();

            this.adicionaCliente(enderecoIPdoCliente);
        }
    }

    void adicionaCliente(InetAddress enderecoIP) {
        int idCliente = (int) (Math.random()*10);

        Cliente cliente = new Cliente(idCliente, enderecoIP);

        this.clientesAtivos.put(idCliente, cliente);

        System.out.println("CLIENTES CONECTADOS "+ System.currentTimeMillis());
            
        this.clientesAtivos.forEach((id, cli) -> System.out.println(id + " | " + cli.getIPAdress()));
        
        System.out.println("\n");


    }
}
