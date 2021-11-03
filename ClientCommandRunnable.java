import java.net.*;
import java.io.*;

public class ClientCommandRunnable implements Runnable {

	private Socket socket;
	private BufferedReader input;
	// private PrintWriter output;

	public ClientCommandRunnable(Socket s) throws IOException {
		this.socket = s;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// this.output = new PrintWriter(socket.getOutputStream(),true);
	}

	@Override
	public void run() {

		while (true) {
			try{
				String response = input.readLine();
				System.out.println(response);
			}catch(IOException e){
				System.out.println("\nServidor foi desconectado :c\n");
				break;
			}

		}


	}

}