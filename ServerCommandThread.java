
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
	private Socket socket;
	private ArrayList<ServerCommandThread> threadList;
	private Menu menu;
	private Fila fila;
	public PrintWriter remetenteDeDados;
	private BufferedReader leitorDeDados;

	public ServerCommandThread(Socket socket, ArrayList<ServerCommandThread> threads, Menu menu, Fila fila,
			Server server) throws IOException {
		this.server = server;
		this.socket = socket;
		this.threadList = threads;
		this.menu = menu;
		this.fila = fila;

		// Le os dados enviados pelo cliente
		this.leitorDeDados = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Instancia um PrintWriter com o outputStream do cliente para retornar dados
		this.remetenteDeDados = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {

			// Pega nome, IP e porta do cliente
			String nomeDoCliente = this.leitorDeDados.readLine();
			InetAddress enderecoIPdoCliente = this.socket.getInetAddress();
			int portaDoCliente = this.socket.getPort();

			// Adiciona cliente na lista de ativos do servidor
			this.server.adicionaCliente(enderecoIPdoCliente, nomeDoCliente, portaDoCliente);


			// inifite loop for server
			while (true) {
				String outputString = this.leitorDeDados.readLine();
				// if user types exit command
				if (outputString.equals("exit")) {
					break;
				}

				if (!outputString.contains(") "))
					menu.menuComandos(outputString, fila, this.remetenteDeDados);
				else
					printToALlClients(outputString);
				// output.println("Server says " + outputString);
				if (outputString.contains(") "))
					System.out.println("Server recebeu " + outputString);
				else
					System.out.println("Server executou " + outputString);

			}

		} catch (Exception e) {
			System.out.println("Error occured " + e.getStackTrace());
		}
	}

	private void printToALlClients(String outputString) {
		for (ServerCommandThread sT : threadList) {
			sT.remetenteDeDados.println(outputString);
		}
	}
}