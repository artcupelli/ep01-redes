import java.io.IOException;
import java.util.Scanner;

/**
 * Main
 */
public class Main {


    public static void main(String[] args) throws IOException {

        // A classe menu e responsavel pelas interacoes
        Menu menu = new Menu();

        // Inicia o menu que pergunta se o inicia como cliente ou servidor
        menu.exibeMenuInicial();

    }    
}
