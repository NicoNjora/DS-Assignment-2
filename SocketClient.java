/**
 * This class connects to the server and communicates with it according to
 * the Client Protocol
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        Scanner scanner = new Scanner(System.in);
        int portNumber = scanner.nextInt();
        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String server_recieved;
            String fromUser;
            String _msg;
            while ((server_recieved = in.readLine()) != null) {

//                ClientProtocol clientProtocol = new ClientProtocol("My toy", "the description", "My company",
//                        "my street", "my zip", "my country", 1234, 1000, 12345678, "7/7/1997");
//                _msg = clientProtocol.clientProcessInput(server_recieved);
                System.out.println("Server: " +server_recieved);
                if (server_recieved.equals("Bye."))
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