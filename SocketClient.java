/**
 * This class connects to the server and communicates with it according to
 * the Client Protocol
 */
import javax.swing.*;
import java.io.*;
import java.net.*;

public class SocketClient extends JFrame {

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 4444;

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;


            ClientProtocol clientProtocol = new ClientProtocol("My toy","the description","MY company",
                    "the street", "the code", "the country", 1234, 10000,12345678, "23/05/1997");
            while ((fromServer = in.readLine()) != null) {
                String server_received = clientProtocol.clientProcessInput(fromServer);
                System.out.println("Server: " + server_received);
                if (fromServer.equals("Successful communication"))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

}

