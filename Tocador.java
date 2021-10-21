import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Tocador {

    Fila fila;
    AudioInputStream audioInput;
    Clip clip;

    void tocaMusica(String caminho){
        try{
            File caminhoMusica = new File(caminho); 
            
            if(caminhoMusica.exists()){
                audioInput = AudioSystem.getAudioInputStream(caminhoMusica);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                JOptionPane.showMessageDialog(null, "Alo alo");
            }else{
                System.out.println("Poxa, não encontramos essa música :(");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void pausaMusica(){
        long clipPosicaoPausada = clip.getMicrosecondPosition();
        clip.stop();
    }
    
    void trocaMusica(){}

    
}
