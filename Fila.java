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

    // TO-DO: Colocar quem colocou na fila

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
     * 
     * @param output
     */
    public void mostraFila(PrintWriter output) {
        // Imprime a relação de número e música
        if (this.fila.isEmpty())
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\n*LISTA DE REPRODUCAO VAZIA*\n");
        else {
            Iterator<Musica> it = this.fila.iterator();
            int i = 1;
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("LISTA DE REPRODUCAO - " + this.fila.size() + ":");
            while (it.hasNext()) {
                Musica aux = it.next();
                for (ServerCommandThread sT : leitoresDeComando)
                    sT.remetenteDeDados.println(i + ": " + aux.getNome() + " (" + aux.getNumero() + ")");
                i++;
            }
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println();
        }
    }

    // Adiciona música no final da fila
    public void adicionaMusica(PrintWriter output, int num) {
        Musica novaMusica = this.musicasDisponiveis.get(num);
        if (novaMusica != null) {
            this.fila.addLast(novaMusica);
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\n" + novaMusica.getNome() + " foi adicionado no fim da fila.\n");
        } else {
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\nNumero invalido para adicao\n");
            mostraMusicas(output);
        }
    }

    // Remove primeira incidêcia da música
    public void removeMusica(PrintWriter output, int num) {
        Musica novaMusica = this.musicasDisponiveis.get(num);
        int index = fila.indexOf(novaMusica);
        if (novaMusica != null && fila.contains(novaMusica)) {
            this.fila.remove(novaMusica);
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados
                        .println("\n" + novaMusica.getNome() + " foi removida no indice " + (index + 1) + "\n");
        } else if (!fila.contains(novaMusica) && musicasDisponiveis.containsValue(novaMusica)) {
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\nMusica nao presente na fila\n");
            mostraFila(output);
        } else {
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\nNumero invalido para remocao\n");
            mostraMusicas(output);
        }
    }

    // Vale a pena fazer um remover por indice
    public void removeMusicaIndex(PrintWriter output, int num) {
        int index = num - 1;
        if (fila.get(index) != null) {
            Musica novaMusica = this.fila.remove(index);
            for (ServerCommandThread sT : leitoresDeComando)
                sT.remetenteDeDados.println("\n" + novaMusica.getNome() + " foi removida na posicao " + num + "\n");
        } else {
            for (ServerCommandThread sT : leitoresDeComando) {
                sT.remetenteDeDados.println("\nIndice invalido\n");
                mostraFila(output);
            }
        }
    }

    // Retorna próxima a musica da fila
    public void passaMusica() {
        this.fila.removeFirst();
    }

    // Retorna para o inicio da musica
    // tocador
    public void pausaMusica() {

    }

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
