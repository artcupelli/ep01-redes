import java.util.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Tocador {

    AudioInputStream audioInput;
    Clip clip;
    long clipPosicaoPausa;

    void tocaMusica(String caminho){
        System.out.println("Entrei no tocaMusica :)");
        
        try{
            File caminhoMusica = new File(caminho);
            
            if(caminhoMusica.exists()){
                audioInput = AudioSystem.getAudioInputStream(caminhoMusica);

                AudioFormat format = audioInput.getFormat();

                // Calculando a duração da musica para setar o Thread.sleep()
                long tamanhoAudio = caminhoMusica.length();
                int tamanhoFrame = format.getFrameSize();
                float frameRate = format.getFrameRate();
                long duracaoEmMilissegundos = (long) (tamanhoAudio / (tamanhoFrame * frameRate)) * 1000;

                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(-1);
                Thread.sleep(duracaoEmMilissegundos);
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

    public static void main(String[] args) throws Exception{
        Tocador tocador = new Tocador();

        tocador.tocaMusica("[caminhodamusica].wav");

        tocador.pausaMusica();
    }
    
}
