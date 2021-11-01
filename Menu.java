import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Oferece as opções de interação e lê as respostas do cliente
 */
public class Menu {

    /**
     * Inicia o programa no modo 'Cliente' ou 'Servidor', dependendo da escolha do
     * cliente
     */
    public void exibeMenuInicial() throws IOException {

        // Imprime a pergunta
        System.out.println("Bem-vind_\n\nVoce deseja iniciar como cliente ou servidor?\n\nCliente (c)\nServidor (s)");

        // Instancia um scanner para coletar a resposta
        Scanner scanner = new Scanner(System.in);

        // Guarda se a entrada foi uma das opções
        boolean entradaValida = false;

        // Espera uma entrada valida
        while (!entradaValida) {

            // Pega próxima linha
            String entrada = scanner.nextLine();

            // Verifica o conteudo
            switch (entrada) {

            // Caso 'c': inicia modo cliente
            case "c":
                entradaValida = true;
                this.iniciaModoCliente();
                break;

            // Caso 's': inicia modo servidor
            case "s":
                entradaValida = true;
                this.iniciaModoServidor();
                break;

            // Caso contrario: espera outra resposta
            default:
                System.out.println("\nmodo invalido, selecione novamente");
                break;
            }
        }
    }


    /**
     * Exibe a lista de comandos disponiveis para o cliente
     * 
     * @param output
     */
    private void showComandos(PrintWriter output) {

        // Imprime os comandos
        output.println("COMANDOS: ");
        output.println("---------------");
        output.println("  play:                 Reproduz a musica");
        output.println("  pause:                Pausa a musica");
        output.println(
                "  add <num>:            Adiciona musica correpondente ao numero no final da lista de reproducao");
        output.println(
                "  remove <num>:         Remove a primeira incidência da musica correpondente ao numero no lista de reproducao");
        output.println("  removeInd <index>:    Remove uma musica correpondente ao índice em uma posição especifica");
        output.println("  musicas:              Mostra a lista de musicas disponíveis");
        output.println("  fila:                 Mostra fila de reproducao");
        output.println("  limpaFila:            Limpa a fila de reproducao");
        output.println("  comandos:             Mostra os comandos");
        output.println("  sair:                 Logout do servidor");
        output.println();
    }


    /**
     * Toma decisao da proxima acao do programa baseado no comando dado pelo usuario
     * 
     * @param entrada
     * @param fila
     * @param output
     */
    public void menuComandos(String entrada, Fila fila, PrintWriter output) {

        String[] args = entrada.split(" ");

        switch (args[0]) {
        // case "play": toc.tocaMusica(fila.getFila().getFirst().getCaminho());/*
        // System.out.println(" VAI PA ONDE?\n") */; // play nas musicas
        // break;

        // Caso 'add': Adiciona musica com o id contido em args[1], ao final da fila
        case "add":
            fila.adicionaMusica(output, Integer.parseInt(args[1]));
            break;

        // Caso 'remove': Remove da fila a primeira musica com o id contido em args[1]
        case "remove":
            fila.removeMusica(output, Integer.parseInt(args[1]));
            break;

        // Caso 'removeInd': Remove musica no index contido em args[1]
        case "removeInd":
            fila.removeMusicaIndex(output, Integer.parseInt(args[1]));
            break;

        // Caso 'musicas': Mostra todas as musicas (opcoes) do programa
        case "musicas":
            fila.mostraMusicas(output);
            break;

        // Caso 'fila': Mostra (em ordem) as musicas na fila
        case "fila":
            fila.mostraFila(output);
            break;

        // Caso 'limpaFila': Retira todas as musicas da fila
        case "limpaFila":
            fila.limparFila(output);
            break;

        // Caso 'comandos': Mostra a lista de comandos reconhecidos pelo programa
        case "comandos":
            showComandos(output);
            break;

        // Caso 'sair': Desconecta o cliente
        case "sair":
            output.println("NAO HA SAIDAAA HEHEHE\n");// desconecta o usuário;
            break;

        // Caso contrario: Espera outro comando, exibe a lista de comandos
        default:
            output.println("\nCOMANDO INVALIDO, ESCOLHA ENTRE ESSES:\n");
            showComandos(output);
            break;
        }

    }


    /**
     * Inicia o programa no Modo Cliente
     * @throws UnknownHostException
     * @throws IOException
     */
    private void iniciaModoCliente() throws UnknownHostException, IOException {
        
        // Imprime modo de execucao
        System.out.println("MODO CLIENTE\n");

        // Instancia um cliente da aplicacao
        Client cliente = new Client();

        // Conecta o cliente ao servidor
        cliente.conectaComServidor();
    }

    
    /**
     * Inicia o programa no Modo Servidor
     * @throws IOException
     */
    private void iniciaModoServidor() throws IOException {
        
        // Imprime modo de execucao
        System.out.println("MODO SERVIDOR\n");

        // Instancia um servidor da aplicacao
        Server servidor = new Server();

        // Liga o servidor
        servidor.liga();
    }

}
