
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Implementa a thread para recebimento dos comandos do cliente
 */
public class ServerCommandThread extends Thread {
	private Server server;									// Instancia do servidor ativo
	private ArrayList<ServerCommandThread> threadList;		// Pega lista de threads dos sockets de envio/recebimento de comando
	public PrintWriter remetenteDeDados;					// Le os dados do cliente
	private BufferedReader leitorDeDados;					// Envia os dados para o cliente
	private Socket socketComando;							// Socket de envio/recebimento de comandos
	private Socket socketMusica;							// Socket de envio de musica


	public ServerCommandThread(ArrayList<ServerCommandThread> threads, Server server, Socket socketComando, Socket socketMusica) throws IOException {
		this.server = server;
		this.threadList = threads;
		this.socketComando = socketComando;
		this.socketMusica = socketMusica;

		// Le os dados enviados pelo cliente
		this.leitorDeDados = new BufferedReader(new InputStreamReader(this.socketComando.getInputStream()));

		// Instancia um PrintWriter com o outputStream do cliente para retornar dados
		this.remetenteDeDados = new PrintWriter(this.socketComando.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {

			// Pega nome, IP e porta do cliente
			String nomeDoCliente = this.leitorDeDados.readLine();
			InetAddress enderecoIPdoCliente = this.socketComando.getInetAddress();
			int portaDoCliente = this.socketComando.getPort();

			// Adiciona cliente na lista de ativos do servidor
			this.server.adicionaCliente(enderecoIPdoCliente, nomeDoCliente, portaDoCliente, this.socketComando, this.socketMusica);

			String outputString;

			// Loop infinito do servidor
			while (true) {
				try{
					// Pega proximo comando do cliente
					outputString = this.leitorDeDados.readLine();

				}catch(IOException e){
					
					// Caso de erro, desconecta o cliente
					System.out.println("\nCliente " + nomeDoCliente + " se desconectou\n"); 
					this.server.clientesAtivos.remove(portaDoCliente);
					this.server.imprimeClientes();
					break;
				}

				// Se o cliente digitar 'sair', terminar a thread
				if (outputString.equals("sair")) {
					break;
				}

				// Envia comando para o menu interpretar
				if (!outputString.contains(") "))
					this.server.menu.menuComandos(outputString, this.server.filaDeMusica, this.remetenteDeDados);
				
				// Envia comando recebido para todos os clientes
				else
					this.imprimirParaTodosClientes(outputString);

				// Imprime o comando recebido no terminal do servidor
				if (outputString.contains(") "))
					System.out.println("Server recebeu " + outputString);
				else
					System.out.println("Server executou " + outputString);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envia uma mensagem para todos os clientes ativos
	 * @param outputString
	 */
	private void imprimirParaTodosClientes(String outputString) {

		// Para cada thread de envio/recebimento de comandos
		for (ServerCommandThread sT : threadList) {

			// Imprimir a mensagem
			sT.remetenteDeDados.println(outputString);
		}
	}
}