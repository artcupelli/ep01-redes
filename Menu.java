import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Menu {

    void exibeMenuInicial() throws IOException{        
        System.out.println("bem-vind*\n\nvoce deseja iniciar como cliente ou servidor?\n\ncliente (c)\nservidor (s)");
        
        Scanner scanner = new Scanner(System.in);

        boolean entradaValida = false;

        while(!entradaValida){
            String entrada = scanner.nextLine();

            switch(entrada){
                case "c":
                    entradaValida = true;
                    this.iniciaModoCliente();
                break;
    
                case "s":
                    entradaValida = true;
                    this.iniciaModoServidor();
                break;
                
                default:
                System.out.println("\nmodo invalido, selecione novamente");
                break;
            }
        }

    }

    private void iniciaModoCliente() throws UnknownHostException, IOException{
        System.out.println("MODO CLIENTE\n");

        Cliente cliente = new Cliente();

        cliente.conectaComServidor();


    }

    private void iniciaModoServidor() throws IOException{
        System.out.println("MODO SERVIDOR\n");

        Servidor servidor = new Servidor();

        servidor.liga();
    }
    
}
