import java.util.*;
import java.io.*;

public class Fila {

    // Todas as músicas
    private Map<Integer, Musica> musicasDisponiveis;
    // Fila para a reprodução
    private LinkedList<Musica> fila;
    private ArrayList<ServerThread> threadList;

    public Fila(ArrayList<ServerThread> threadList){

        this.musicasDisponiveis = new HashMap<Integer, Musica>();
        this.fila = new LinkedList<Musica>();
        this.threadList = threadList;

        File[] caminhoArq = new File("./musicas").listFiles();
        int j=1;

        // Adiciona músicas na lista de músicas disponíveis
        for(int i=0; i<caminhoArq.length; i++){
            if(caminhoArq[i].getName().endsWith(".wav")){
                Musica musicas = new Musica(caminhoArq[i].getName(), caminhoArq[i].getPath(), j);
                this.musicasDisponiveis.put(musicas.getNumero(), musicas);
                j++;
            } else System.out.println("Formato não suportado para " + caminhoArq[i].getName());
        }
    }

    // TO-DO: Colocar quem colocou na fila

    //Mostra as músicas disponíveis para pedir
    public void mostraMusicas(PrintWriter output){
        // Imprime a relação de número e música
        for (ServerThread sT : threadList) sT.output.println("\nMÚSICAS DISPONÍVEIS PARA REPRODUÇÃO - "+ this.musicasDisponiveis.size() +":");
        for (int j = 1; this.musicasDisponiveis.containsKey(j); j++) {
            for (ServerThread sT : threadList) sT.output.println(this.musicasDisponiveis.get(j).getNumero() + ": " + this.musicasDisponiveis.get(j).getNome());
        }
        for (ServerThread sT : threadList) sT.output.println();
    }

    //Mostra as músicas presentes na fila
    public void mostraFila(PrintWriter output){
        // Imprime a relação de número e música
        if(this.fila.isEmpty()) for (ServerThread sT : threadList) sT.output.println("\n*LISTA DE REPRODUÇÃO VAZIA*\n");
        else{
            Iterator<Musica> it = this.fila.iterator();
            int i = 1;
            for (ServerThread sT : threadList) sT.output.println("LISTA DE REPRODUÇÃO - "+ this.fila.size() +":");
            while(it.hasNext()){
                Musica aux = it.next();
                for (ServerThread sT : threadList) sT.output.println(i + ": " + aux.getNome() + " (" + aux.getNumero() + ")");
                i++;
            }
            for (ServerThread sT : threadList) sT.output.println();
        }
    }

    // Adiciona música no final da fila
    public void adicionaMusica(PrintWriter output, int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        if(novaMusica!=null){
            this.fila.addLast(novaMusica);
            for (ServerThread sT : threadList) sT.output.println("\n" + novaMusica.getNome() + " foi adicionado no fim da fila.\n"); 
        }
        else{
            for (ServerThread sT : threadList) sT.output.println("\nNúmero inválido para adição\n"); 
            mostraMusicas(output);
        }
    }

    // Remove primeira incidêcia da música
    public void removeMusica(PrintWriter output, int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        int index = fila.indexOf(novaMusica);
        if (novaMusica != null && fila.contains(novaMusica)){
            this.fila.remove(novaMusica);
            for (ServerThread sT : threadList) sT.output.println("\n" + novaMusica.getNome() + " foi removida no índice " + (index+1) + "\n");
        }
        else if(!fila.contains(novaMusica) && musicasDisponiveis.containsValue(novaMusica)){
            for (ServerThread sT : threadList) sT.output.println("\nMúsica não presente na fila\n");
            mostraFila(output);
        }
        else{
            for (ServerThread sT : threadList) sT.output.println("\nNúmero inválido para remoção\n");
            mostraMusicas(output);
        }
    }
    // Vale a pena fazer um remover por indice
    public void removeMusicaIndex(PrintWriter output, int num) {
        int index = num-1;
        if(fila.get(index)!=null){
            Musica novaMusica = this.fila.remove(index);
            for (ServerThread sT : threadList) sT.output.println("\n" + novaMusica.getNome() + " foi removida na posição " + num + "\n");
        }
        else{
            for (ServerThread sT : threadList){ 
                sT.output.println("\nÍndice inválido\n");
                mostraFila(output);
            }
        }
    }

    // Retorna próxima a música da fila
    public void passaMusica(){
        this.fila.removeFirst();
    }

    // Retorna para o início da música 
    // tocador
    public void pausaMusica(){
        
    }

    public void limparFila(PrintWriter output){
        this.fila.clear();
        mostraFila(output);
    }
    
    public Map<Integer, Musica> getMusicasDisponiveis(){
        return this.musicasDisponiveis;
    }

    public LinkedList<Musica> getFila(){
        return this.fila;
    }
    
}
