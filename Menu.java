import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.*;

public class Menu {

    public void exibeMenuInicial() throws IOException{        
        System.out.println("Bem-vind*\n\nVoce deseja iniciar como cliente ou servidor?\n\nCliente (c)\nServidor (s)");
        
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

    private void showComandos(PrintWriter output){
        output.println("COMANDOS: ");
        output.println("---------------");
        output.println("  play:                 Reproduz a música");
        output.println("  pause:                Pausa a música");
        output.println("  add <num>:            Adiciona música correpondente ao número no final da lista de reprodução");
        output.println("  remove <num>:         Remove a primeira incidência da música correpondente ao número no lista de reprodução");
        output.println("  removeInd <index>:    Remove uma música correpondente ao índice em uma posição específica");
        output.println("  musicas:              Mostra a lista de músicas disponíveis");
        output.println("  fila:                 Mostra fila de reprodução");
        output.println("  limpaFila:            Limpa a fila de reprodução");
        output.println("  comandos:             Mostra os comandos");
        output.println("  sair:                 Logout do servidor");
        output.println(); 
    }

    public void menuComandos(String entrada, Fila fila, PrintWriter output){
        // System.out.println("Sei lá, manda um comando ai:");
        // Scanner scanner = new Scanner(System.in);
        // Fila fila = new Fila();
        String [] scanner = entrada.split(" ");
        Tocador toc = new Tocador();
        // String retorno;
        // showComandos(output);
        // while(true){
            switch (scanner[0]) {
                case "play":  toc.tocaMusica(fila.getFila().getFirst().getCaminho());/* System.out.println(" VAI PA ONDE?\n") */; // play nas músicas
                    break;
                // case "pause": toc.pausaMusica();//System.out.println(" TRAVA NA POSEEEEEEEEEEEE UUUUUUUUUUOUUUUU\n Chama no zoom\n Da um close\n"); // pause nas músicas
                //     break;
                case "add": fila.adicionaMusica(output, Integer.parseInt(scanner[1]));                 
                    break;
                case "remove": fila.removeMusica(output, Integer.parseInt(scanner[1]));                 
                    break;
                case "removeInd": fila.removeMusicaIndex(output, Integer.parseInt(scanner[1]));                 
                    break;
                case "musicas": fila.mostraMusicas(output);                 
                    break;
                case "fila": fila.mostraFila(output);                 
                    break;
                case "limpaFila": fila.limparFila(output);               
                        break;
                case "comandos": showComandos(output);               
                    break;
                case "sair": output.println("NÃO HÁ SAÍDAAA HEHEHE\n");//desconecta o usuário;                 
                    break;
            
                default: { output.println("\nCOMANDO INVÁLIDO, ESCOLHA ENTRE ESSES:\n"); showComandos(output);}
                    break;
            }

            // return retorno;
        // }
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
