
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	public PrintWriter output;
	private Menu menu;
	private Fila fila;

	public ServerThread(Socket socket, ArrayList<ServerThread> threads, Menu menu, Fila fila) {
		this.socket = socket;
		this.threadList = threads;
		this.menu = menu;
		this.fila = fila;
	}

	@Override
	public void run() {
		try {
			// Reading the input from Client
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// returning the output to the client : true statement is to flush the buffer
			// otherwise
			// we have to do it manuallyy
			output = new PrintWriter(socket.getOutputStream(), true);

			// inifite loop for server
			while (true) {
				String outputString = input.readLine();
				// if user types exit command
				if (outputString.equals("exit")) {
					break;
				}
			
				if(!outputString.contains(") ")) menu.menuComandos(outputString, fila, output);
				else printToALlClients(outputString);
				// output.println("Server says " + outputString);
				if (outputString.contains(") ")) System.out.println("Server recebeu " + outputString);
				else System.out.println("Server executou " + outputString);

			}

		} catch (Exception e) {
			System.out.println("Error occured " + e.getStackTrace());
		}
	}

	private void printToALlClients(String outputString) {
		for (ServerThread sT : threadList) {
			sT.output.println(outputString);
		}
	}
}