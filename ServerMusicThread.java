
/**

    EP01 - REDES - STREAMING DE MÚSICA

    Artur Egidio Launikas e Cupelli 11795738
    Idemar Burssed dos Santos Neto 11857282
    Milleny Caroliny de Almeida Santos 11937979
    Raquel Cristiane da Silva Almeida 11838245 
 
 */

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Implementa a thread para envio da musica para os clientes
 */
public class ServerMusicThread extends Thread {

    public Server server; // Instancia do servidor ativo
    public Fila fila; // Fila de musicas

    public ServerMusicThread(Server server) throws UnknownHostException, IOException {
        this.server = server;
        this.fila = server.filaDeMusica;

    }

    @Override
    public void run() {
        try {

            // Cria o outputStream para enviar dados
            OutputStream remetenteDeDados;

            // Loop infinito do server
            while (true) {

                // Inativa esta thread por 100ms para ter certeza que a thread de comandos
                // iniciou primeiro
                Thread.sleep(100);

                // Enquanto se a fila de musicas nao esta vazia
                while (!this.fila.getFila().isEmpty()) {

                    // Pega a primeira musica da fila
                    Musica musica = this.fila.getFila().getFirst();

                    // Pega o arquivo da musica na memoria em seu caminho (ex:
                    // 'C:/musicas/teste.wav')
                    FileInputStream arquivoMusica = new FileInputStream(new File(musica.getCaminho()));

                    // Cria um buffer para enviar a musica 'n' bytes por vez
                    int n = 2048; // Tamanho 'n' do buffer
                    byte buffer[] = new byte[n];

                    // Cria contador que vai ser utilizado para saber qual a quantidade de bytes que
                    // vai ser enviada (o intervalo de bytes escritos vai do byte 'b' ate o
                    // 'b+count-1')
                    int count;

                    // Cria o offset para o envio dos bytes, entra na conta acima somando em ambos
                    // os lados do primeiro e ultimo byte (do 'b + offset' ate o 'b + offset + count
                    // - 1').
                    // Neste caso nao usamos offset, ou seja, igual a 0.
                    int offset = 0;

                    // Enquanto houver bytes a serem lidos
                    while ((count = arquivoMusica.read(buffer)) != -1) {

                        // Para todos os clientes ativos
                        for (ClientInfo client : this.server.clientesAtivos.values()) {

                            // Troca o remetente para o do cliente atual
                            remetenteDeDados = client.socketMusica.getOutputStream();

                            // Envia os dados dentro do intervalo estabelecido
                            remetenteDeDados.write(buffer, offset, count);
                        }

                    }

                    for (ClientInfo client : this.server.clientesAtivos.values()) {
                        remetenteDeDados = client.socketMusica.getOutputStream();

                        // Retira a musica da fila
                        this.fila.removeMusica(new PrintWriter(remetenteDeDados, true), musica.getNumero());
                    }

                }
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }

    }

}
