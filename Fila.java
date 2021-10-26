import java.util.*;
import java.io.*;

public class Fila {

    // Todas as musicas
    private Map<Integer, Musica> musicasDisponiveis;
    // Fila para a reproducao
    private LinkedList<Musica> fila;

    public Fila(){

        this.musicasDisponiveis = new HashMap<Integer, Musica>();
        this.fila = new LinkedList<Musica>();

        File[] caminhoArq = new File("./musicas").listFiles();
        int j=1;

        // Adiciona musicas na lista de musicas disponiveis
        for(int i=0; i<caminhoArq.length; i++){
            if(caminhoArq[i].getName().endsWith(".wav")){
                Musica musicas = new Musica(caminhoArq[i].getName(), caminhoArq[i].getPath(), j);
                this.musicasDisponiveis.put(musicas.getNumero(), musicas);
                j++;
            } else System.out.println("Formato nao suportado para " + caminhoArq[i].getName());
        }
    }

    // TO-DO: Colocar quem colocou na fila

    //Mostra as musicas disponiveis para pedir
    public void mostraMusicas(){
        // Imprime a relacao de numero e musica
        System.out.println("MUSICAS DISPONIVEIS PARA REPRODUCAO - "+ this.musicasDisponiveis.size() +":");
        for (int j = 1; this.musicasDisponiveis.containsKey(j); j++) {
            System.out.println(this.musicasDisponiveis.get(j).getNumero() + ": " + this.musicasDisponiveis.get(j).getNome());
        }
        System.out.println();
    }

    //Mostra as musicas presentes na fila
    public void mostraFila(){
        // Imprime a relacao de numero e musica
        if(this.fila.isEmpty()) System.out.println("*LISTA DE REPRODUCAO VAZIA*\n");
        else{
            Iterator<Musica> it = this.fila.iterator();
            int i = 1;
            System.out.println("LISTA DE REPRODUCAO - "+ this.fila.size() +":");
            while(it.hasNext()){
                Musica aux = it.next();
                System.out.println(i + ": " + aux.getNome() + " (" + aux.getNumero() + ")");
                i++;
            }
            System.out.println();
        }
    }

    // Adiciona musica no final da fila
    public void adicionaMusica(int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        if(novaMusica!=null){
            this.fila.addLast(novaMusica);
            System.out.println(novaMusica.getNome() + " foi adicionado no fim da fila.\n"); 
        }
        else{
            System.out.println("Numero invalido para adicao\n"); 
            mostraMusicas();
        }
    }

    // Remove primeira incidêcia da musica
    public void removeMusica(int num){
        Musica novaMusica = this.musicasDisponiveis.get(num);
        int index = fila.indexOf(novaMusica);
        if (novaMusica != null && fila.contains(novaMusica)){
            this.fila.remove(novaMusica);
            System.out.println(novaMusica.getNome() + " foi removida no indice " + (index+1) + "\n");
        }
        else if(!fila.contains(novaMusica) && musicasDisponiveis.containsValue(novaMusica)){
            System.out.println("Musica nao presente na fila\n");
            mostraFila();
        }
        else{
            System.out.println("Numero invalido para remocao\n");
            mostraMusicas();
        }
    }
    // Vale a pena fazer um remover por indice
    public void removeMusicaIndex(int num) {
        int index = num-1;
        if(fila.get(index)!=null){
            Musica novaMusica = this.fila.remove(index);
            System.out.println(novaMusica.getNome() + " foi removida na posicao " + num + "\n");
        }
        else{
            System.out.println("Indice invalido\n");
            mostraFila();
        }
    }

    // Retorna próxima a musica da fila
    public void passaMusica(){
        this.fila.removeFirst();
    }

    // Retorna para o inicio da musica 
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
