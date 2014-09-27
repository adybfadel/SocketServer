import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CaptureClient {

	private PrintWriter out;
	private JFrame frame = new JFrame("Capitalize Client");
	private JTextField dataField = new JTextField(40);
	private JTextArea messageArea = new JTextArea(8, 60);

	public CaptureClient() {

		messageArea.setEditable(false);
		frame.getContentPane().add(dataField, "North");
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");

		dataField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				out.println(dataField.getText());
			}
		});
	}

	public void connectToServer() throws IOException {

		String serverAddress = JOptionPane.showInputDialog(frame,
				"Enter IP Address of the Server:", "Camera Socket",
				JOptionPane.QUESTION_MESSAGE);

		@SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 9898);
		out = new PrintWriter(socket.getOutputStream(), true);

	}

	public static void main(String[] args) throws Exception {
		CaptureClient client = new CaptureClient();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.pack();
		client.frame.setVisible(true);
		client.connectToServer();
	}
}
