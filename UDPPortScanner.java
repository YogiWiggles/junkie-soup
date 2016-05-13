package com.tenaj.junkiesoup;
/*
 * Juan Tena
 * NMSU-Grants CS187-G01 Java Programming
 * Project Name: junkie-soup4
 */

/*
 * UDP port Scanner
 */

import java.net.*;
import java.io.IOException;

public class UDPPortScanner {

	public static void UDP() {
		 boolean rootaccess = false;
		    for (int port = 1; port < 1024; port += 50) {
		      try {
		        ServerSocket ss = new ServerSocket(port);
		        // if successful
		        rootaccess = true;
		        ss.close();
		        break;
		      }
		      catch (IOException ex) {
		      }
		    }
		    
		    int startport = 1;
		    if (!rootaccess) startport = 1024;
		    int stopport = 65535;
		    
		    System.out.println ("Checking UDP ports... ");
		    System.out.println();
		    System.out.println("-------------------------------------------------------");
		    System.out.println("-------------------------------------------------------");
		    System.out.println("-------------------------------------------------------");
		    for (int port = startport; port <= stopport; port++) {
		      try {
		        DatagramSocket ds = new DatagramSocket(port);
		        ds.close();
		      }
		      catch (IOException ex) {
		        System.out.println("\tUDP Port " + "*****[" + port + "]******" + " is occupied.");
		        System.out.println();
		        //System.out.print(ex);
		      }
		    }
	}
}

