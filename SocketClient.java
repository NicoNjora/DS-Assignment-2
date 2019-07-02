/**
 * This class connects to the server and communicates with it according to
 * the Client Protocol
 */
import java.io.*;
import java.net.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

public class SocketClient extends JFrame{

	private JTextArea tarea;
	private JTextField tfield;
	private JScrollPane spane;

	public String fromServer;
	public String fromUser;

	private PrintWriter out = null;

	public void Client() throws IOException {

		//user set port for communication
		Scanner scanner = new Scanner(System.in);
		String hostName = "localhost";
		int portNumber = scanner.nextInt();

		Socket kkSocket = null;
		BufferedReader in = null;
		BufferedReader stdIn =  new BufferedReader(new InputStreamReader(System.in));

		try {
				kkSocket = new Socket(hostName, portNumber);
				out = new PrintWriter(kkSocket.getOutputStream(), true);
				in = new BufferedReader(
						new InputStreamReader(kkSocket.getInputStream()));
		
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
		tarea.setLineWrap(true);
		spane = new JScrollPane(tarea);

		tfield = new JTextField(10);
		tfield.requestFocusInWindow();

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JLabel clientLabel = new JLabel("Client: ");
		JLabel outputLabel = new JLabel("Output: ");        
		
		contentPane.add(outputLabel);
		contentPane.add(spane);

		contentPane.add(clientLabel);
		contentPane.add(tfield);

		// Start of ports stuff

		JLabel serverPortLabel = new JLabel("Server Port: ");
		JLabel clientPortLabel = new JLabel("Client Port: "); 

		JTextField serverPortField = new JTextField(10);;
		JTextField clientPortField = new JTextField(10);;

		// Server Port Components
		contentPane.add(serverPortLabel);
		contentPane.add(serverPortField);

		// Client Port Components
		contentPane.add(clientPortLabel);
		contentPane.add(clientPortField);

		// serverPortField
		layout.putConstraint(SpringLayout.WEST, serverPortField,
					 5,
					 SpringLayout.EAST, serverPortLabel);

		layout.putConstraint(SpringLayout.NORTH, clientPortField,
					 5,
					 SpringLayout.SOUTH, serverPortField);

		layout.putConstraint(SpringLayout.NORTH, outputLabel,
					 5,
					 SpringLayout.SOUTH, serverPortField);

		// clientPortLabel
		layout.putConstraint(SpringLayout.WEST, clientPortField,
					 5,
					 SpringLayout.EAST, clientPortLabel);

		layout.putConstraint(SpringLayout.NORTH, spane,
					 5,
					 SpringLayout.SOUTH, clientPortField);

		layout.putConstraint(SpringLayout.NORTH, serverPortLabel,
					 5,
					 SpringLayout.SOUTH, clientPortField);

		layout.putConstraint(SpringLayout.NORTH, outputLabel,
					 5,
					 SpringLayout.SOUTH, clientPortLabel);

		layout.putConstraint(SpringLayout.NORTH, contentPane,
					 5,
					 SpringLayout.NORTH, serverPortLabel);

		// End of ports stuff


		/*
		* Start of layout constraints
		*/

		// Placing the output label at the west of the content pane
		layout.putConstraint(SpringLayout.WEST, outputLabel,
					 15,
					 SpringLayout.WEST, contentPane);
		
		// Placing the output label at the north of the content pane


		//  Placing the client label to the west of the content pane
		layout.putConstraint(SpringLayout.WEST, clientLabel,
					 15,
					 SpringLayout.WEST, contentPane);
					 
		// Placing the client label to the south of the content pane
		layout.putConstraint(SpringLayout.SOUTH, clientLabel,
					 -15,
					 SpringLayout.SOUTH, contentPane);


		// Placing the output label to the west of the spane(text area)
		layout.putConstraint(SpringLayout.WEST, spane,
					 15,
					 SpringLayout.EAST, outputLabel);

		// Placing the spane(text area) to the north of the content pane


		// Placing the client label to the west of the text field
		layout.putConstraint(SpringLayout.WEST, tfield,
					 15,
					 SpringLayout.EAST, clientLabel);

		// Placing the text field to the south of the spane(text area)
		layout.putConstraint(SpringLayout.NORTH, tfield,
					 15,
					 SpringLayout.SOUTH, spane);

	
		/*
		* To initialize window correctly
		*/

		// Ensuring that the window fits the spane's width
		layout.putConstraint(SpringLayout.EAST, contentPane,
					 15,
					 SpringLayout.EAST, spane);

		// Ensuring that the window fits the content's height
		layout.putConstraint(SpringLayout.SOUTH, contentPane,
					 15,
					 SpringLayout.SOUTH, tfield);

		/*
		* End of layout constraints
		*/

		setContentPane(contentPane);
		pack();
		setVisible(true); 

		Thread receiveMessage = new Thread(new ReceiveChat(in, stdIn, out));    
		receiveMessage.start();

		// Server Port Field 
		// serverPortField.addActionListener(new ActionListener()
		// {
		// 	public void actionPerformed(ActionEvent ae)
		// 	{
		// 		// // Here you will send it to the server side too, put that code here
		// 		// fromUser = tfield.getText() + "\n";
		// 		// if (fromUser != null) 
		// 		// {
		// 		//     System.out.println("Client: " + fromUser);
		// 		//     tarea.append(fromUser);
		// 		//     out.println(fromUser);
		// 		//     tfield.setText("");
		// 		// }
		// 	}
		// });
		

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
				"the street", "the code", "the country", 1234, 10000, 12345678, "23/05/1997");
		  
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
					client.setPreferredSize(new Dimension(300, 450));
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