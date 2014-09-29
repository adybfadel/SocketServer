import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CaptureEndpoint {

	private static Robot robot;

	public static void main(String[] args) throws Exception {

		robot = new Robot();

		System.out.println("The socket server is running.");
		System.out.println("Options: 'camera1', 'camera2', 'on', 'exit'");

		ServerSocket listener = new ServerSocket(9898);

		try {

			while (true) {

				Socket socket = listener.accept();

				log("Conected with: " + socket);

				try {

					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					while (true) {
						String input = in.readLine();
						log("Message: " + input);

						if (input == null || input.equals("exit"))
							break;

						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.delay(100);
						if ("on".equalsIgnoreCase(input.trim().toLowerCase())) {
							robot.keyPress(KeyEvent.VK_Q);
							robot.delay(100);
							robot.keyRelease(KeyEvent.VK_Q);
						} else if ("camera1".equalsIgnoreCase(input.trim()
								.toLowerCase())) {
							robot.keyPress(KeyEvent.VK_A);
							robot.delay(100);
							robot.keyRelease(KeyEvent.VK_A);
						} else if ("camera2".equalsIgnoreCase(input.trim()
								.toLowerCase())) {
							robot.keyPress(KeyEvent.VK_B);
							robot.delay(100);
							robot.keyRelease(KeyEvent.VK_B);
						}
						robot.delay(100);
						robot.keyRelease(KeyEvent.VK_CONTROL);

					}

				} catch (IOException e) {
					log("Error: " + e);
				}

				try {
					socket.close();
				} catch (IOException e) {
					log("Couldn't close a socket!!");
				}
				log("Connection closed.");

			}

		} finally {
			listener.close();
		}

	}

	private static void log(String message) {
		System.out.println(message);
	}

}