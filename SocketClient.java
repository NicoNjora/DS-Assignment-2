/**
 * This class connects to the server and communicates with it according to
 * the Client Protocol
 */
import java.io.*;
import java.net.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class SocketClient extends JFrame{

    private JTextArea tarea;
    private JTextField tfield;
    private JScrollPane spane;

    public String fromServer;
    public String fromUser;

    private PrintWriter out = null;

    public void Client() throws IOException {

        String hostName = "localhost";
        int portNumber = 4444;

        Socket kkSocket = null;
        //PrintWriter out = null;  Shifted this to the instance variable
        BufferedReader in = null;

        BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            

        try {
                kkSocket = new Socket(hostName, portNumber);
                out = new PrintWriter(kkSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        
            

            // /**
            //  * String toyName, String descrription, String company, String street,
            //  *                           String zipCode, String country, int toy_code, int price, int batchNumber, String date_of_manufacture
            //  */
            // ClientProtocol clientProtocol = new ClientProtocol("My toy","the description","MY company",
            //                                 "the street", "the code", "the country", 1234, 10000,12345678, "23/05/1997");
            // while ((fromServer = in.readLine()) != null) {
            //     String server_received = clientProtocol.clientProcessInput(fromServer);
            //     System.out.println("Server: " + server_received);
            //     if (fromServer.equals("Successful communication"))
            //         break;

            //     fromUser = stdIn.readLine();
            //     if (fromUser != null) {
            //         System.out.println("Client: " + fromUser);
            //         out.println(fromUser);
            //     }
            // }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }    
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();

        tarea = new JTextArea(20, 20);
        spane = new JScrollPane(tarea);
        tarea.setLineWrap(true);
        tfield = new JTextField(10);

        tfield.requestFocusInWindow();

        contentPane.setLayout(new BorderLayout());
        contentPane.add(spane, BorderLayout.CENTER);
        contentPane.add(tfield, BorderLayout.PAGE_END);

        setContentPane(contentPane);
        pack();
        setVisible(true); 

        Thread receiveMessage = new Thread(new ReceiveChat(in, stdIn, out));    
        receiveMessage.start();

        tfield.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                // Here you will send it to the server side too, put that code here
                fromUser = tfield.getText() + "\n";
                if (fromUser != null) 
                {
                    System.out.println("Client: " + fromUser);
                    tarea.append(fromUser);
                    out.println(fromUser);
                    tfield.setText("");
                }
            }
        });
        //out.close();
        //in.close();
        //stdIn.close();
        //kkSocket.close();
    }    

    private class ReceiveChat implements Runnable
    {
        private BufferedReader in;
        private BufferedReader stdIn;
        private PrintWriter out;

        public ReceiveChat(BufferedReader in, BufferedReader stdIn, PrintWriter out)
        {
            this.in = in;
            this.stdIn = stdIn;
            this.out = out;
        }

        public void run()
        {
            try
            {
                /**
                * String toyName, String descrription, String company, String street,
                *                           String zipCode, String country, int toy_code, int price, int batchNumber, String date_of_manufacture
                */
                ClientProtocol clientProtocol = new ClientProtocol("My toy","the description","MY company",
                "the street", "the code", "the country", 1234, 10000,12345678, "23/05/1997");
          
                while ((fromServer = in.readLine()) != null) {
                    //System.out.println("Server: " + fromServer);
                    String server_received = clientProtocol.clientProcessInput(fromServer);
                    tarea.append(server_received);
                    tarea.setCaretPosition(tarea.getDocument().getLength());

                    if (fromServer.equals("Successful communication"))
                        break;
                    // fromUser = stdIn.readLine();
                    // if (fromUser != null) {
                    //     System.out.println("Client: " + fromUser);
                    //     out.println(fromUser);
                    // }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
     }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                SocketClient client = new SocketClient();
                try
                {
                    client.Client();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}