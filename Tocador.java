import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Tocador {

    AudioInputStream audioInput;
    Clip clip;
    long clipPosicaoPausa;

    void tocaMusica(String caminho){

        try{
            File caminhoMusica = new File(caminho);
            
            if(caminhoMusica.exists()){
                audioInput = AudioSystem.getAudioInputStream(caminhoMusica);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }else{
                System.out.println("Poxa, não encontramos a música :(");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    void pausaMusica(){
        clipPosicaoPausa = clip.getMicrosecondPosition();
        clip.stop();
    }
    
    void voltaMusica(){
        clip.setMicrosecondPosition(clipPosicaoPausa);
        clip.start();
    }
    
    void trocaMusica(){}
    
}
