import java.net.InetAddress;

/**
 *  Guarda as informações necessárias para cada cliente online
 */
class ClientInfo{
    InetAddress enderecoIP;
    String nome;
    int porta;

    public ClientInfo(InetAddress enderecoIP, String nome, int porta){
        this.enderecoIP = enderecoIP;
        this.nome = nome;
        this.porta = porta;
    }

    public ClientInfo(){}

}