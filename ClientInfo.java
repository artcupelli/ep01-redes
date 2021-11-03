import java.net.InetAddress;
import java.net.Socket;

/**
 *  Guarda as informações necessárias para cada cliente online
 */
class ClientInfo{
    InetAddress enderecoIP;
    String nome;
    int porta;
    Socket socketComando;
    Socket socketMusica;

    public ClientInfo(InetAddress enderecoIP, String nome, int porta, Socket socketComando, Socket socketMusica){
        this.enderecoIP = enderecoIP;
        this.nome = nome;
        this.porta = porta;
        this.socketComando = socketComando;
        this.socketMusica = socketMusica;
    }

    public ClientInfo(){}

}