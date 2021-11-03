
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
	private Server server;
	private ArrayList<ServerCommandThread> threadList;
	public PrintWriter remetenteDeDados;
	private BufferedReader leitorDeDados;
	private Socket socketComando;
	private Socket socketMusica;


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

			// inifite loop for server
			while (true) {
				try{
					// Pega proximo comando do cliente
					outputString = this.leitorDeDados.readLine();

				}catch(IOException e){
					
					// Caso dê erro (desconexao do cliente)
					System.out.println("\nCliente " + nomeDoCliente + " se desconectou\n"); 
					this.server.activeClients.remove(portaDoCliente);
					this.server.imprimeClientes();
					break;
				}

				// if user types exit command
				if (outputString.equals("exit")) {
					break;
				}

				if (!outputString.contains(") "))
					this.server.menu.menuComandos(outputString, this.server.filaDeMusica, this.remetenteDeDados);
				else
					printToALlClients(outputString);
				// output.println("Server says " + outputString);
				if (outputString.contains(") "))
					System.out.println("Server recebeu " + outputString);
				else
					System.out.println("Server executou " + outputString);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printToALlClients(String outputString) {
		for (ServerCommandThread sT : threadList) {
			sT.remetenteDeDados.println(outputString);
		}
	}
}