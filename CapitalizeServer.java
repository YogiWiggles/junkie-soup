package com.tenaj.junkiesoup;
/*
 * Juan Tena
 * NMSU-Grants CS187-G01 Java Programming
 * Project Name: junkie-soup4
 */

/*
 * This is the server end of the capitalize program
 * founded @ http://cs.lmu.edu/~ray/notes/javanetexamples/#tictactoe
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class CapitalizeServer {

	public static void main(String[] args) throws Exception {
		/* 
		 * calls to UDPPortScanner class
		 * runs occupied UDP Ports before requesting Junkie IP
		 * Junkie IP: 127.0.0.1
		 */
	
		UDPPortScanner.UDP();
		
		// Acknowledgement that junkie is awake
		System.out.println("Junkie-Server is up & running...");
		
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
	}

    /*
     * Below is the the threading that conducts the capitalization on the server's end.
     * The client terminates the active port by sending a one-liner: buzzoff
     * remember!! remember!! the 'junkie' won't buzzoff unless you tell it to.
     * it's programmed to keep asking you for input to CAP until you close the port.
     */
    private static class Capitalizer extends Thread {
    	
    	// socket and integer declarations
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with Client# " + clientNumber + " at " + "[[[[" + socket + "]]]]");
        }

        /*
         * Once the connection has been established the server will send 
         * a greeting, followed by prompting the user to write strings.
         * @ this point the server is simply listening waiting to capitalize
         * whatever strings the user inputs. 
         */
        public void run() {
            try {

                 // This is reading the input
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // This is our server's welcoming party:
                out.println("Hello, Client # " + clientNumber + "!");
                out.println();
                out.println("I can buzzoff... Just say so.");

                // Get messages from the client, line by line;
                //  return them capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("buzzoff")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error handling Client # " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Junkie can't sleep, what's going on?!?");
                }
                // exe. after a terminated session
                // requests the log details if applicable
                log("Connection with client# " + clientNumber + " closed");
                log("Give junkie the details: ");
                //log details here
            }
        }

        
        //Allows for user input within the console.  
        private void log(String message) {
            System.out.println(message);
        }
	}
}



