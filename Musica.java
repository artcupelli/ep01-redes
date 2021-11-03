/**
 * Classe que guarda as informacoes de uma musica
 */
public class Musica {

    private String nome;        // Nome do arquivo na pasta
    private String caminho;     // Caminho no diretorio
    private int numero;         // ID da musica (dado por ordem de leitura)
    
    public Musica(String nome, String caminho, int numero){
        this.nome = nome.replace(".wav","");
		this.caminho = caminho;
		this.numero = numero;
    }

    public String getNome(){
        return this.nome;
    }
    
    public String getCaminho(){
        return this.caminho;
    }
    
    public int getNumero(){
        return this.numero;
    }
    
}
