import java.io.*;
import java.net.*;
import java.util.Vector;

public class ChatServer {
	static Vector ClientSockets;
	static Vector loginNames;
	
	ChatServer() throws IOException {
		ServerSocket server = new ServerSocket(5217);
		ClientSockets = new Vector();
		loginNames = new Vector();
		
		while(true) {
			Socket client = server.accept();
			AcceptClient acceptClient = new AcceptClient(client);
		}
	}
	
	class AcceptClient extends Thread {
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;
		
		AcceptClient(Socket client) throws IOException{
			ClientSocket = client;
			din = new DataInputStream(ClientSocket.getInputStream());
			dout = new DataOutputStream(ClientSocket.getOutputStream());
			
			String LoginName = din.readUTF();
			
			loginNames.add(LoginName);
			ClientSockets.add(ClientSocket);
			
			start();
		}
		
		public void run() {
			while(true) {
				
			}
		}
	}

}
