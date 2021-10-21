import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Tocador {

    void tocaMusica(String caminho){
        InputStream musica;

        try{
            musica = new FileInputStream(new File(caminho));
            AudioStream audio = new AudioStream(musica);
            AudioPlayer.player.start(audio);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Poxa, deu erro."); 
        }
    }

    void pausaMusica(){}
    
    void trocaMusica(){}

    
}
