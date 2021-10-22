
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        // Esse while não ta funfando direito, tem que estudar como faz
        while(socket.getInetAddress().isReachable(500)){
            System.out.print("conectado -\r");
            System.out.print("conectado /\r");
            System.out.print("conectado |\r");
            System.out.print("conectado \\\r");
            menu.menuComandos();
        }

    }

    public InetAddress getIPAdress(){
        return this.enderecoIP;
    }
}
