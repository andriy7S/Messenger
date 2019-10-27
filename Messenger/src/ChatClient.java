
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class ChatClient extends JFrame implements Runnable {

	Socket socket;
	JTextArea ta;
	JButton send, logout;
	JTextField tf;

	Thread thread;

	DataInputStream din;
	DataOutputStream dout;

	String LoginName;

	ChatClient(String login) throws UnknownHostException, IOException {
		super(login);
		LoginName = login;

		ta = new JTextArea(18, 50);
		tf = new JTextField(50);

		send = new JButton("Send");
		logout = new JButton("Logout");

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dout.writeUTF(LoginName + " " + "DATA " + tf.getText().toString());
					tf.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dout.writeUTF(LoginName + " " + "LOGOUT");
					System.exit(1);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		socket = new Socket("localhost", 5217);

		din = new DataInputStream(socket.getInputStream());
		dout = new DataOutputStream(socket.getOutputStream());

		dout.writeUTF(LoginName);
		dout.writeUTF(LoginName + " " + "LOGIN");

		thread = new Thread(this);
		thread.start();
		setup();
	}

	private void setup() {
		setSize(600, 400);

		JPanel panel = new JPanel();
		panel.add(new JScrollPane(ta));
		panel.add(tf);
		panel.add(send);
		panel.add(logout);
		add(panel);

		setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				ta.append("\n" + din.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatClient client1 = new ChatClient("User1");
		ChatClient client2 = new ChatClient("User2");
	}

}
