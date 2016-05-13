package com.tenaj.junkiesoup;
/*
 * Juan Tena
 * NMSU-Grants CS187-G01 Java Programming
 * Project Name: junkie-soup4
 */

/*
 * This is the client end of the capitalize program
 * founded @ http://cs.lmu.edu/~ray/notes/javanetexamples/#tictactoe 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CapitalizeClient {
	
		//displays JFrame window
	 	private BufferedReader in;
	    private PrintWriter out;
	    private JFrame frame = new JFrame("Capitalize Client");
	    private JTextField dataField = new JTextField(100);
	    private JTextArea messageArea = new JTextArea(50, 80);
	    
	   /*
	    * This is where the client machine is declared
	    * This is also where the GUI build is.
	    */
	    public CapitalizeClient() {

	        // Layout GUI
	        messageArea.setEditable(false);
	        frame.getContentPane().add(dataField, "North");
	        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

	        // Add Listeners
	        dataField.addActionListener(new ActionListener() {
	        	
	            /*
	             * The method + try-catch clause is used to allow text field input
	             * The text is sent to the junkie-server after hitting the [ENTER] key
	             * The program terminates by telling junkie to: buzzoff (one word) 
	             */
	            public void actionPerformed(ActionEvent e) {
	                out.println(dataField.getText());
	                   String response;
	                try {
	                    response = in.readLine();
	                    if (response == null || response.equals("")) {
	                          System.exit(0);
	                      }
	                } catch (IOException ex) {
	                       response = "Error: " + ex;
	                }
	                messageArea.append(response + "\n");
	                dataField.selectAll();
	            }
	        });
	    }

	    /*
	     * The method below is to prompt the user for the junkie-server's IP address
	     * This allows the client to accept the server's greetings (3) lines
	     * of code following the initial connection. 
	     */
	    public void connectToServer() throws IOException {

	        // JFrame window asking for Junkie IP
	        String serverAddress = JOptionPane.showInputDialog(
	            frame,
	            "Enter IP Address of the Junkie-Server:", // same as host address: 127.0.0.1
	            "Welcome Junkie!!",
	            JOptionPane.QUESTION_MESSAGE);

	        // establish the communication thread between server/client
	        Socket socket = new Socket(serverAddress, 9898);
	        in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);

	        // Allows for (3) lines of code to be received by the client
	        // from the server, after a connection has been established.
	        for (int i = 0; i < 3; i++) {
	            messageArea.append(in.readLine() + "\n");
	        }
	    }

	    /*
	     * Client Main Method
	     */
	    public static void main(String[] args) throws Exception {
	        CapitalizeClient client = new CapitalizeClient();
	        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        client.frame.pack();
	        client.frame.setVisible(true);
	        client.connectToServer();
	    }

}

