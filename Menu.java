import java.io.IOException;
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

    private void showComandos(){
        System.out.println("COMANDOS: ");
        System.out.println("---------------");
        System.out.println("  play:                 Reproduz a musica");
        System.out.println("  pause:                Pausa a musica");
        System.out.println("  add <num>:            Adiciona musica correpondente ao numero no final da lista de reproducao");
        System.out.println("  remove <num>:         Remove a primeira incidencia da musica correpondente ao numero no lista de reproducao");
        System.out.println("  removeInd <index>:    Remove uma musica correpondente ao indice em uma posicao especifica");
        System.out.println("  musicas:              Mostra a lista de musicas disponiveis");
        System.out.println("  fila:                 Mostra fila de reproducao");
        System.out.println("  limpaFila:            Limpa a fila de reproducao");
        System.out.println("  comandos:             Mostra os comandos");
        System.out.println("  sair:                 Logout do servidor");
        System.out.println();
        
    }

    public void menuComandos(){
        // System.out.println("Sei lá, manda um comando ai:");
        Scanner scanner = new Scanner(System.in);
        Fila fila = new Fila();
        Tocador toc = new Tocador();
        showComandos();
        while(true){
            switch (scanner.next()) {
                case "play":  toc.tocaMusica(fila.getFila().getFirst().getCaminho());/* System.out.println(" VAI PA ONDE?\n") */; // play nas músicas
                    break;
                case "pause": toc.pausaMusica();//System.out.println(" TRAVA NA POSEEEEEEEEEEEE UUUUUUUUUUOUUUUU\n Chama no zoom\n Da um close\n"); // pause nas músicas
                    break;
                case "add": fila.adicionaMusica(scanner.nextInt());                 
                    break;
                case "remove": fila.removeMusica(scanner.nextInt());                 
                    break;
                case "removeInd": fila.removeMusicaIndex(scanner.nextInt());                 
                    break;
                case "musicas": fila.mostraMusicas();                 
                    break;
                case "fila": fila.mostraFila();                 
                    break;
                case "limpaFila": fila.limparFila();               
                        break;
                case "comandos": showComandos();               
                    break;
                case "sair": System.out.println(" NAO HA SAIDAAA HEHEHE\n");//desconecta o usuário;                 
                    break;
            
                default: {System.out.println("\nCOMANDO INVALIDO, ESCOLHA ENTRE ESSES:\n"); showComandos();}
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
