import java.net.*;
import java.io.*;

/**
 * Classe que le e imprime as respostas dos comandos do servidor no cliente
 * Comandos enviados por outros clientes tambem sao exibidos por esta classe
 */
public class ClientCommandRunnable implements Runnable {

	private Socket socket;			// Socket do envio/recebimento dos comandos
	private BufferedReader input;	// Leitor das respostas do servidor

	public ClientCommandRunnable(Socket s) throws IOException {
		this.socket = s;

		// Cria BufferedReader para ler resposta do servidor
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {

		// Loop infinito ate a conexão com o servidor cair, ou o usuario desconectar
		while (true) {

			try {
				// Le e imprime resposta do servidor
				String response = input.readLine();
				System.out.println(response);

			} catch (IOException e) {

				// Caso o servidor se desconecte, para a aplicacao
				System.out.println("\nServidor foi desconectado :c\n");
				System.exit(0);
				break;
			}

		}

	}

}