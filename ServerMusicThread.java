
import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Implementa a thread para envio da musica para os clientes
 */
public class ServerMusicThread extends Thread {

    public ServerSocket socketApresentacao;
    public DatagramSocket socket;
    public Fila fila;
    public Socket socketConexao;

    public ServerMusicThread(Fila fila) throws UnknownHostException, IOException {
        this.fila = fila;

        // Cria o socket de apresentação
        this.socketApresentacao = new ServerSocket(6364);

    }

    @Override
    public void run() {
        try {

            // Aceita um cliente novo
            this.socketConexao = socketApresentacao.accept();

            // Cria o outputStream para enviar dados
            OutputStream remetenteDeDados = socketConexao.getOutputStream();

            // Loop infinito do server
            while (true) {

                // Inativa esta thread por 100ms para ter certeza que a thread de comandos iniciou primeiro
                Thread.sleep(100);

                // Verifica se a fila de musicas nao esta vazia
                if (!this.fila.getFila().isEmpty()) {

                    // Pega a primeira musica da fila
                    Musica musica = this.fila.getFila().getFirst();

                    // Pega o arquivo da musica na memoria em seu caminho (ex: 'C:/musicas/teste.wav')
                    FileInputStream arquivoMusica = new FileInputStream(new File(musica.getCaminho()));

                    // Retira a musica da fila
                    this.fila.removeMusica(new PrintWriter(remetenteDeDados, true), musica.getNumero());

                    // Cria um buffer para enviar a musica 'n' bytes por vez
                    int n = 2048; // Tamanho 'n' do buffer
                    byte buffer[] = new byte[n];

                    // Cria contador que vai ser utilizado para saber qual a quantidade de bytes que
                    //  vai ser enviada (o intervalo de bytes escritos vai do byte 'b' ate o 'b+count-1')
                    int count;

                    // Cria o offset para o envio dos bytes, entra na conta acima somando em ambos os
                    //  lados do primeiro e ultimo byte (do 'b + offset' ate o 'b + offset + count - 1').
                    //  Neste caso nao usamos offset, ou seja, igual a 0.
                    int offset = 0;

                    // Enquanto houver bytes a serem lidos
                    while ((count = arquivoMusica.read(buffer)) != -1){

                        // Envia os dados dentro do intervalo estabelecido
                        remetenteDeDados.write(buffer, offset, count);
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
