import java.util.*;
import java.io.*;

public class Fila {

    // Todas as músicas
    private Map<Integer, Musica> musicasDisponiveis;
    // Fila para a reprodução
    private LinkedList<Musica> fila;

    public Fila(){

        this.musicasDisponiveis = new HashMap<Integer, Musica>();
        this.fila = new LinkedList<Musica>();

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
    public void mostraMusicas(){
        // Imprime a relação de número e música
        System.out.println("MÚSICAS DISPONÍVEIS PARA REPRODUÇÃO - "+ this.musicasDisponiveis.size() +":");
        for (int j = 1; this.musicasDisponiveis.containsKey(j); j++) {
            System.out.println(this.musicasDisponiveis.get(j).getNumero() + ": " + this.musicasDisponiveis.get(j).getNome());
        }
        System.out.println();
    }

    //Mostra as músicas presentes na fila
    public void mostraFila(){
        // Imprime a relação de número e música
        if(this.fila.isEmpty()) System.out.println("*LISTA DE REPRODUÇÃO VAZIA*\n");
        else{
            Iterator<Musica> it = this.fila.iterator();
            int i = 1;
            System.out.println("LISTA DE REPRODUÇÃO - "+ this.fila.size() +":");
            while(it.hasNext()){
                Musica aux = it.next();
                System.out.println(i + ": " + aux.getNome() + " (" + aux.getNumero() + ")");
                i++;
            }
            System.out.println();
        }
    }

    // Adiciona música no final da fila
    public void adicionaMusica(int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        if(novaMusica!=null){
            this.fila.addLast(novaMusica);
            System.out.println(novaMusica.getNome() + " foi adicionado no fim da fila.\n"); 
        }
        else{
            System.out.println("Número inválido para adição\n"); 
            mostraMusicas();
        }
    }

    // Remove primeira incidêcia da música
    public void removeMusica(int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        int index = fila.indexOf(novaMusica);
        if (novaMusica != null && fila.contains(novaMusica)){
            this.fila.remove(novaMusica);
            System.out.println(novaMusica.getNome() + " foi removida no índice " + (index+1) + "\n");
        }
        else if(!fila.contains(novaMusica) && musicasDisponiveis.containsValue(novaMusica)){
            System.out.println("Música não presente na fila\n");
            mostraFila();
        }
        else{
            System.out.println("Número inválido para remoção\n");
            mostraMusicas();
        }
    }
    // Vale a pena fazer um remover por indice
    public void removeMusicaIndex(int num) {
        int index = num-1;
        if(fila.get(index)!=null){
            Musica novaMusica = this.fila.remove(index);
            System.out.println(novaMusica.getNome() + " foi removida na posição " + num + "\n");
        }
        else{
            System.out.println("Índice inválido\n");
            mostraFila();
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

    public void limparFila(){
        this.fila.clear();
        mostraFila();
    }
    
    public Map<Integer, Musica> getMusicasDisponiveis(){
        return this.musicasDisponiveis;
    }

    public LinkedList<Musica> getFila(){
        return this.fila;
    }
    
}
