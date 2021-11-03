import java.util.*;
import java.io.*;

/**
 * Cria a fila de musicas para reproducao e guarda as musicas disponiveis
 */
public class Fila {

    private Map<Integer, Musica> musicasDisponiveis; // Todas as musicas
    private LinkedList<Musica> fila; // Fila para a reproducao
    private ArrayList<ServerCommandThread> leitoresDeComando; // Lista das threads do receptor de comando para cada user

    public Fila(ArrayList<ServerCommandThread> leitoresDeComando) {

        this.musicasDisponiveis = new HashMap<Integer, Musica>();
        this.fila = new LinkedList<Musica>();
        this.leitoresDeComando = leitoresDeComando;

        // Cadastra as musicas da pasta './musicas' nas musicas disponiveis
        this.cadastraMusicasDisponiveis();

    }

    /**
     * Imprime as musicas disponiveis no programa
     * 
     * @param output
     */
    public void mostraMusicas(PrintWriter output) {

        // Para cada thread (usuario ativo) na lista
        for (ServerCommandThread leitor : leitoresDeComando) {

            // Envia para o cliente o cabecalho e o total de musicas disponiveis
            leitor.remetenteDeDados
                    .println("\nMUSICAS DISPONIVEIS PARA REPRODUCAO - " + this.musicasDisponiveis.size() + ":");
        }

        // Para cada musica disponivel e com ID
        for (int idMusica = 1; this.musicasDisponiveis.containsKey(idMusica); idMusica++) {

            // Para cada thread (usuario ativo) na lista
            for (ServerCommandThread leitor : leitoresDeComando) {

                // Envia para o cliente o ID da musica e o nome
                leitor.remetenteDeDados.println(this.musicasDisponiveis.get(idMusica).getNumero() + ": "
                        + this.musicasDisponiveis.get(idMusica).getNome());
            }
        }

        // Para cada thread (usuario ativo) na lista
        for (ServerCommandThread sT : leitoresDeComando)

            // Envia um espaco
            sT.remetenteDeDados.println();
    }

    /**
     * Imprime a fila de reproducao para o cliente
     * 
     * @param output
     */
    public void mostraFila(PrintWriter output) {

        // Se a fila estiver vazia
        if (this.fila.isEmpty()) {

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem de erro
                sT.remetenteDeDados.println("\n*LISTA DE REPRODUCAO VAZIA*\n");
            }
        }

        // Se a fila nao estiver vazia
        else {

            // Itera pela fila
            Iterator<Musica> it = this.fila.iterator();
            int i = 1;

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Imprime cabecalho
                sT.remetenteDeDados.println("\nLISTA DE REPRODUCAO - " + this.fila.size() + ":");
            }

            // Enquanto ha musicas na fila
            while (it.hasNext()) {

                // Pega musica
                Musica aux = it.next();

                // Para cada thread de envio/recebimento de comando
                for (ServerCommandThread sT : leitoresDeComando)

                    // Imprime musica
                    sT.remetenteDeDados.println(i + ": " + aux.getNome() + " (" + aux.getNumero() + ")");
                i++;
            }

            // Para cada thread de envio/recebimento de comando imprime um espaco
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println();
        }
    }

    /**
     * Adiciona musica na fila
     * 
     * @param output
     * @param num
     */
    public void adicionaMusica(PrintWriter output, int num) {

        // Pega a musica do acervo
        Musica novaMusica = this.musicasDisponiveis.get(num);

        // Se a musica existir
        if (novaMusica != null) {

            // Adiciona musica na fila
            this.fila.addLast(novaMusica);

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem
                sT.remetenteDeDados.println("\n" + novaMusica.getNome() + " foi adicionado no fim da fila.\n");
            }
        }

        // Se a musica nao existir
        else {

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem de erro
                sT.remetenteDeDados.println("\nNumero invalido para adicao\n");
            }

            // Mostra as musicas disponiveis
            mostraMusicas(output);
        }
    }

    /**
     * Remove da fila a primeira incidencia da musica com ID = num
     * 
     * @param output
     * @param num
     */
    public void removeMusica(PrintWriter output, int num) {

        // Procura musica com ID = num
        Musica musica = this.musicasDisponiveis.get(num);

        // Pega o index da musica
        int index = fila.indexOf(musica);

        // Se houver musica com o ID selecionado
        if (musica != null && fila.contains(musica)) {

            // Tira musica da fila
            this.fila.remove(musica);

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando)

                // Envia mensagem
                sT.remetenteDeDados.println("\n" + musica.getNome() + " foi removida no indice " + (index + 1) + "\n");

        }

        // Caso contrario, se a musica nao estiver na fila mas existir
        else if (!fila.contains(musica) && musicasDisponiveis.containsValue(musica)) {

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem de erro
                sT.remetenteDeDados.println("\nMusica nao presente na fila\n");
            }

            // Mostra fila novamente para o cliente
            mostraFila(output);

        }

        // Caso contrario (nao existe esta musica)
        else {

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem de erro
                sT.remetenteDeDados.println("\nNumero invalido para remocao\n");

            }

            // Mostra musicas disponiveis para o cliente
            mostraMusicas(output);
        }
    }

    /**
     * Remove a fila a musica na posicao (index) = num
     * 
     * @param output
     * @param num
     */
    public void removeMusicaIndex(PrintWriter output, int num) {

        // Pega index verdadeiro
        int index = num - 1;

        // Se tem musica no index
        if (fila.get(index) != null) {

            // Remove musica da fila
            Musica musicaRemovida = this.fila.remove(index);

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem
                sT.remetenteDeDados.println("\n" + musicaRemovida.getNome() + " foi removida na posicao " + num + "\n");
            }

        }

        // Se nao tiver musica no index
        else {

            // Para cada thread de envio/recebimento de comando
            for (ServerCommandThread sT : leitoresDeComando) {

                // Envia mensagem de erro
                sT.remetenteDeDados.println("\nIndice invalido\n");

                // Mostra a fila novamente
                mostraFila(output);
            }
        }
    }

    /**
     * Limpa fila de reproducao
     */
    public void limparFila(PrintWriter output) {
        this.fila.clear();
        mostraFila(output);
    }

    /**
     * Cadastra as musicas disponiveis no programa (que sao as da pasta './musicas')
     */
    private void cadastraMusicasDisponiveis() {

        // Pega todos os arquivos da pasta './musicas'
        File[] arquivosMusicas = new File("./musicas").listFiles();

        // Cria um ID incremental para cada musica, comecando de 1
        int idMusica = 1;

        // Para cada arquvivo na pasta './musicas'
        for (int i = 0; i < arquivosMusicas.length; i++) {

            // Verifica se o arquivo termina em '.wav'
            if (arquivosMusicas[i].getName().endsWith(".wav")) {

                // Instancia nova musica com os dados do arquivo e o ID
                Musica musicas = new Musica(arquivosMusicas[i].getName(), arquivosMusicas[i].getPath(), idMusica);

                // Adiciona a nova musica na lista de musicas disponiveis
                this.musicasDisponiveis.put(musicas.getNumero(), musicas);

                // Incrementa o ID
                idMusica++;

                // Caso o arquivo nao termine em './wav' nao eh um arquivo valido para o
                // programa
            } else {

                // Imprime erro do arquivo
                System.out.println("Formato nao suportado para " + arquivosMusicas[i].getName());
            }
        }
    }

    public Map<Integer, Musica> getMusicasDisponiveis() {
        return this.musicasDisponiveis;
    }

    public LinkedList<Musica> getFila() {
        return this.fila;
    }

}
