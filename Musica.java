public class Musica {

    private String nome;
    private String caminho;
    private int numero;
    
    public Musica(String nome, String caminho, int numero){
        this.nome = nome;
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
