
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

/**
 * Implementa thread para recebimento da musica enviada pelo servidor
 */
public class ClientMusicRunnable implements Runnable {
    private Socket socket;

    public ClientMusicRunnable() throws UnknownHostException, IOException {

        // Conecta com o socket de musica na porta 6364
        this.socket = new Socket(InetAddress.getLocalHost(), 6364);
    }

    @Override
    public void run() {

        try {
            // Enquanto o socket estiver conectado
            while (this.socket.isConnected()) {

                // Cria um leitor para receber os dados do servidor
                InputStream leitorDeDados = new BufferedInputStream(this.socket.getInputStream());

                // Cria um rementente para enviar dados para o servidor
                OutputStream remententeDeDados = socket.getOutputStream();

                // Toca a musica recebida como bytes
                tocaMusica(leitorDeDados, remententeDeDados);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Toca musica dado um InputStream
     * 
     * @param in
     * @throws Exception
     */
    private static synchronized void tocaMusica(final InputStream leitorDeDados, final OutputStream remententeDeDados)
            throws Exception {

        // Implementa o leitor de dados genérico como um leitor especifico de audio
        AudioInputStream leitorDeAudio = AudioSystem.getAudioInputStream(leitorDeDados);

        try {

            // Instancia a classe Clip que consegue tocar o audio de um arquivo
            // pre-conhecido
            // (diferente da captura de um microfone por exemplo).
            Clip clip = AudioSystem.getClip();

            // Abre o clipe com as informacoes contidas no leitor de audio
            clip.open(leitorDeAudio);

            // Comeca a tocar o clipe
            clip.start();
            
            // Espera 100ms e limpa os dados do buffer
            Thread.sleep(100);
            clip.drain();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
