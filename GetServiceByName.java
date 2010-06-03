//package com.drydog;

/**
 * GetServiceByName.java
 *
 * Copyright © 2001 by Dan Anderson, San Diego, California. All rights reserved.
 *
 * $Id$
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

// Java Import
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;


/**
 * The <code>GetServiceByName</code> class provides lookup of port numbers in
 * the <tt>/etc/services</tt> file.
 * NIS service lookup isn't provided (at least yet).
 *
 * @author Dan Anderson
 * @version $Id$
 */
public class GetServiceByName {
	final static private String SERVICES_FILENAME
		= "/etc/services"; // aka /etc/inet/services
		// c:\winnt\system32\drivers\etc\services on Win NT/2000
	

	/**
	 * The <code>parseServicesLine()</code> method
	 * is called by <code>getPortNumberForTcpIpService()</code> to parse
	 * a non-comment line in the <tt>/etc/services</tt> file
	 * and save the values.
	 *
	 * @param line
	 * A line to compare from the <tt>/etc/services</tt> file.
	 *
	 * @param tcpipService
	 * The name of a TCP/IP "well-known" service found in the
	 * <tt>/etc/services</tt> file
	 *
	 * @param tcpipClass
	 * Either "tcp" or "udp", depending on the TCP/IP service desired.
	 *
	 * @return
	 * A port number for a TCP or UDP service (depending on tcpipClass).
	 * Return -1 on error.
	 */
	static private int parseServicesLine(String line,
			String tcpipService,
			String tcpipClass) {
		// Parse line
		StringTokenizer st = new
			StringTokenizer(line, " \t/#");

		// First get the name on the line (parameter 1):
		if (! st.hasMoreTokens()) {
			return -1; // error
		}
		String name = st.nextToken().trim();

		// Next get the service name on the line (parameter 2):
		if (! st.hasMoreTokens()) {
			return -1; // error
		}
		String portValue = st.nextToken().trim();

		// Finally get the class on the line (parameter 3):
		if (! st.hasMoreTokens()) {
			return -1; // error
		}
		String classValue = st.nextToken().trim();

		//System.out.println("DEBUG: name: "
		// + name + ", portValue: " + portValue
		// + ", serviceValue: " + serviceValue);

		// Class doesn't match--reject:
		if (! classValue.equals(tcpipClass)) {
			return -1; // error
		}

		// Return port number, if name on this line matches:
		if (name.equals(tcpipService)) {
			try { // Convert the port number string to integer
				return (Integer.parseInt(portValue));
			} catch (NumberFormatException nfe) {
				// Ignore corrupt /etc/services lines:
				return -1; // error
			}
		} else {
			return -1; // no match
		}
	}	// parseServicesLine()


	/**
	 * The <code>getServiceByName()</code> method
	 * Search the /etc/services file for a service name and class.
	 * Return the port number.
	 * <p>
	 * For example, given this line in <tt>/etc/services</tt>,
	 * <pre>
	 * farkle		4545/udp
	 * </pre>
	 * In this example, a search for service "farkle" and class "udp"
	 * will return 4545.
	 *
	 * @param tcpipService
	 * The name of a TCP/IP "well-known" service found in the
	 * <tt>/etc/services</tt> file
	 *
	 * @param tcpipClass
	 * Either "tcp" or "udp", depending on the TCP/IP service desired.
	 *
	 * @return
	 * A port number for a TCP or UDP service
	 * (depending on tcpipClass).
	 * Return -1 on error.
	 */
	static public int getServiceByName(String tcpipService,
			String tcpipClass) {
		int	port = -1;

		// Look for our service, line-by-line:
		try {
			String line;
			BufferedReader br = new BufferedReader(
						new InputStreamReader(
						   new FileInputStream(
							SERVICES_FILENAME)));

			// Read /etc/services file.
			// Skip comments and empty lines.
			while (((line = br.readLine()) != null)
					&& (port == -1)) {
				if ((line.length() != 0)
						&& (line.charAt(0) != '#')) {
					port = parseServicesLine(line,
						tcpipService, tcpipClass);
				}
			}	// while
			br.close();

			return (port); // port number or -1 (on error)

		} catch (IOException ioe) {
			// File doesn't exist or is otherwise not available.
			// Keep defaults
			return -1; // error
		}
	}	// getServiceByName


	/**
	 * The <code>main()</code> method
	 * is for use as an internal test driver only.
	 * <p>
	 * To execute: set <tt>$CLASSPATH</tt> and run:<br>
	 * <tt>java GetServiceByName</tt>
	 *	[<i>tcpipService</i> <i>tcpipClass</i>]
	 * <br>
	 *	where <i>service</i> is a TCP/IP service and <i>class</i> is a
	 *	 TCP/IP class (either "tcp" or "udp").
	 * <p>
	 * For example:
	 *	<tt>java GetServiceByName ftp tcp</tt>
	 * <p>
	 * Exit code is 0 on success, non-zero on failure.
	 *
	 * @param tcpipService
	 *	The service name for which the port number is desired.
	 * @param tcpipClass
	 *	The TCP/IP class (either "tcp" or "udp")
	 */
	public static void main(String args[]) {
		int	pok, png;
		int	errorCount = 0;

		try {
			// Check for arguments on command line
			if ((args.length != 0) && (args.length != 2)) {
				System.err.println(
					"Test driver for this class\n"
					+ "Usage: "
					+ "java GetServiceByName "
					+ "[<tcpipService> <tcpipclass>]\n"
					+ "Example: "
					+ "java GetServiceByName "
					+ "ftp tcp");
				System.exit(1);
			}

			System.out.println("Testing GetServiceByName");

			// Optional user test
			if (args.length == 2) {
				pok = getServiceByName(args[0], args[1]);
				System.out.println("getServiceByName(\""
					+ args[0] + "\", \"" + args[1]
					+ "\")    returned port " + pok);
			}

			pok = getServiceByName("ntp", "udp");
			if (pok == 123) {
				System.out.println(
					"getServiceByName(\"ntp\", \"udp\")"
					+ " PASSED");
			} else {
				System.out.println(
					"getServiceByName(\"ntp\", \"udp\")"
					+ " FAILED: "
					+ " returned " + pok);
				++errorCount;
			}

			/* This verifies error checking works: */
			png = getServiceByName("nonexistentservicetest", "udp");
			if (png == -1) {
				System.out.println("getServiceByName()"
					+ " error test  PASSED");
			} else {
				System.out.println("getServiceByName()"
					+ " error test  FAILED: "
					+ " returned " + png);
				++errorCount;
			}

		} catch (Exception e) {
			System.err.println("Java Exception: " + e
				+ " (exiting)");
			e.printStackTrace(System.err);
			++errorCount;
		}

			System.exit(errorCount);
	}	// main()

}	// class GetServiceByName
