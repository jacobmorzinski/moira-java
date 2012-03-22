package edu.mit.moira;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import edu.mit.hesiod.Hesiod;
import edu.mit.hesiod.HesiodException;
import edu.mit.hesiod.HesiodResult;
import edu.mit.moira.internal.Constants;

/**
 * This class tests out a Netty driver for Moira.
 * 
 * 
 * 
 * @author Jacob
 *
 */
public class MoiraNetty {

	private final String host;
    private final int port;
    private static final Logger logger = Logger.getLogger(
            MoiraNetty.class.getName());
    
    
	/**
	 * @param host Hostname to connect to
	 * @param port2 Port to connect to
	 */
	public MoiraNetty(String host, int port) {
		this.host = host;
		this.port = port;
		logger.log(Level.INFO, "Host is {0}, port is {1}\n", 
				new Object[] {this.host, this.port});
		
	}

    public void run() {

    	final ChannelFactory factory = new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        // Configure the client.
		ClientBootstrap bootstrap = new ClientBootstrap(factory);

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new MoiraNettyPipelineFactory());

        // Make a new connection.
        ChannelFuture connectFuture =
            bootstrap.connect(new InetSocketAddress(host, port));

        // Wait until the connection is made successfully.
        connectFuture.awaitUninterruptibly();
        if (!connectFuture.isSuccess()) {
        	connectFuture.getCause().printStackTrace();
        }
        Channel channel = connectFuture.getChannel();

        // Get the handler instance to retrieve the answer.
        MoiraNettyHandler handler =
            (MoiraNettyHandler) channel.getPipeline().getLast();

        
        // Done.

        // Must close channel before calling factory.releaseExternalResources()
        ChannelFuture closeFuture = channel.getCloseFuture();
        closeFuture.addListener(new ChannelFutureListener() {
        	public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("About to releaseExternalResources...");
		        // Shut down all thread pools to exit.
				factory.releaseExternalResources();
				System.out.println("Done with releaseExternalResources.");
			}
		});
        channel.close();
        closeFuture.awaitUninterruptibly();
        if (!closeFuture.isSuccess()) {
        	closeFuture.getCause().printStackTrace();
        }

    }




	/**
	 * This reproduces server discovery rules from the existing Moira clients.
	 * The moira server can be specified by environmental variable MOIRASERVER,
	 * or by a Hesiod lookup for the hesiod name "moira" and hesiod name type
	 * "sloc", or {@link Constants#MOIRA_SERVER a compiled-in default}.
	 * 
	 * @return The server specifier
	 */
	private static String determineServer() {
		String server = null;
		try {
			server = System.getenv("MOIRASERVER");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (server == null) {
			try {
				Hesiod h = Hesiod.getInstance();
				HesiodResult hr = h.lookup("moira", "sloc");
				server = hr.getResults(0);
			} catch (HesiodException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		if (server == null) {
			server = Constants.MOIRA_SERVER;
		}
		return server;
	}
	
	/**
	 * If {@code server} contains a colon (":"), {@code host} is set from the
	 * string that precedes the colon. Else, {@code host} is set to the full
	 * input string.
	 * 
	 * @param server A string in the form <host> or <host>:<port>
	 * @return
	 */
	private static String determineHost(String server) {
		int i = server.lastIndexOf(":");
		if (i >= 0) {
			return server.substring(0, i);
		} else {
			return server;
		}
	}
	
	/**
	 * If {@code server} contains a colon (":"), {@code port} is set from the
	 * string that follows the colon. Else, {@code port} is taken from
	 * {@link Constants#MOIRA_SERVER}, or from hesiod lookup, or from a
	 * compiled-in default {@link Constants#DEFAULT_PORT}.
	 * 
	 * @param server A string in the form <host> or <host>:<port>
	 * @return
	 */
	private static int determinePort(String server) {
		String port = null;
		
		int i = server.lastIndexOf(":");
		if (i >= 0) {
			port = server.substring(i + 1);
		}
		else {
			i = Constants.MOIRA_SERVER.lastIndexOf(":");
			port = Constants.MOIRA_SERVER.substring(i + 1);
		}

		int portNum = -1;
		if (port.startsWith("#")) {
			portNum = Integer.parseInt(port.substring(1));
		} else {
			// $ hesinfo moira_db service
			// moira_db tcp 775
			try {
				Hesiod h = Hesiod.getInstance();
				HesiodResult hr = h.lookup(port, "service");
				String result = hr.getResults(0);
				String[] parts = result.split("\\s");
				portNum = Integer.parseInt(parts[2]);
			} catch (HesiodException e) {
				// ignore
			} catch (NamingException e) {
				// ignore
			}
			
			// TODO /etc/services
			// getportbyname(moira_db, tcp) => 775

			// If no other matches, check whether port string matches default
			if (portNum == -1 && port.equals(Constants.DEFAULT_SERVICE)) {
				portNum = Constants.DEFAULT_PORT;
			}
			if (portNum == -1) {
				throw new RuntimeException(
						"Could not determine server port number from port string: " + port);
			}
		}


		return portNum;
	}

	/**
	 * @param args
	 *            An optional server specifier, in the form host:port. Both host
	 *            and port may be symbolic or numeric.
	 */
	public static void main(String[] args) {
		String server = null;
		String host = null;
		int port = -1;
		
		args = new String[] {"ttsp.mit.edu:moira_db"};
		
		if (args.length > 1) {
            System.err.println(
                    "Usage: " + MoiraNetty.class.getSimpleName() +
                    " <host>:<port>");
            return;
        }
		if (args.length == 1) {
			server = args[0];
		} else {
			server = determineServer();
		}
        
		host = determineHost(server);
		port = determinePort(server);
		
        new MoiraNetty(host,port).run();


	}





}
