package edu.mit.moira;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.mit.hesiod.Hesiod;
import edu.mit.hesiod.HesiodResult;
import edu.mit.moira.internal.*;


public class MoiraConnection {

	/*
	 * mrgdb compatibility magic
	 * 
	 * GDB was a generic database library.
	 * It faded away, but Moira clients and servers
	 * still greet each other with a legacy GDB exchange.
	 * 
	 * The data looks like this:
	 * 

   client -> server
     00000036 				[length of rest of packet]
     00000004 				[number of fields]
     01 01 01 01			[types of fields: 4 strings]
     "server_id\0parms\0host\0user\0"	[field names]
     00000001				[length of null-terminated server_id]
     "\0"				[server_id: ignored anyway]
     00000001				[length of null-terminated parms]
     "\0"				[parms: ignored anyway]
     00000001				[length of null-terminated client host]
     "\0"				[host: ignored anyway]
     00000001				[length of null-terminated client name]
     "\0"				[user: ignored anyway]

   server -> client
     00000031 				[length of rest of packet]
     00000003 				[number of fields]
     00 01 01 				[types of fields: int and 2 strings]
     "disposition\0server_id\0parms\0"	[field names]
     00000001				[GDB_ACCEPTED]
     00000001				[length of null-terminated server_id]
     "\0"				[server_id: ignored anyway]
     00000001				[length of null-terminated parms]
     "\0"				[parms: ignored anyway]

	 *
	 */

	static String challengeStr = "\0\0\0\066\0\0\0\004\001\001\001\001server_id\0parms\0host\0user\0\0\0\0\001\0\0\0\0\001\0\0\0\0\001\0\0\0\0\001\0";
	static String responseStr = "\0\0\0\061\0\0\0\003\0\001\001disposition\0server_id\0parms\0\0\0\0\001\0\0\0\001\0\0\0\0\001\0";
	static byte[] chal = new byte[] { 
		0x00, 0x00, 0x00, 0x36, 
		0x00, 0x00, 0x00, 0x04, 
		0x01, 0x01, 0x01, 0x01,
		's', 'e', 'r', 'v', 'e', 'r', '_', 'i', 'd', '\0',
		'p', 'a', 'r', 'm', 's', '\0', 
		'h', 'o', 's', 't', '\0',
		'u', 's', 'e', 'r', '\0', 
		0x00, 0x00, 0x00, 0x01, 
		'\0', 
		0x00, 0x00, 0x00, 0x01,
		'\0', 
		0x00, 0x00, 0x00, 0x01, 
		'\0', 
		0x00, 0x00, 0x00, 0x01, 
		'\0', };
	static byte[] resp = new byte[] {
		0x00, 0x00, 0x00, 0x31, 
		0x00, 0x00, 0x00, 0x03,
		0x00, 0x01, 0x01,
		'd', 'i', 's', 'p', 'o', 's', 'i', 't', 'i', 'o', 'n', '\0',
		's', 'e', 'r', 'v', 'e', 'r', '_', 'i', 'd', '\0',
		'p', 'a', 'r', 'm', 's', '\0', 
		0x00, 0x00, 0x00, 0x01, 
		0x00, 0x00, 0x00, 0x01,
		'\0',
		0x00, 0x00, 0x00, 0x01, 
		'\0', };

	private static Socket conn = null;

	public static int mr_connect (String server) {
		String port = null;
		
		if (server == null) {
			server = System.getenv("MOIRASERVER");
		}
		if (server == null) {
			try {
				Hesiod h = Hesiod.getInstance();
				HesiodResult hr = h.lookup("moira", "sloc");
				server = hr.getResults(0);
			} catch (Exception e) {
				// ignore
			}
		}
		if (server == null) {
			server = Constants.MOIRA_SERVER;
		}
		
		int i = server.lastIndexOf(":");
		if (i >= 0) {
			port = server.substring(i + 1);
		}
		else {
			i = Constants.MOIRA_SERVER.lastIndexOf(":");
			port = Constants.MOIRA_SERVER.substring(i + 1);
		}

		conn  = mr_connect_internal(server, port);
		if (conn == null) {
			return (int) MoiraET.MR_CANT_CONNECT;
		}
		return Constants.MR_SUCCESS;
	}
	
	static Socket mr_connect_internal(String server, String port) {
		int portNum = -1;
		if (port.startsWith("#")) {
			portNum = Integer.parseInt(port.substring(1));
		} else {
			// $ hesinfo moira_db service
			// moira_db tcp 775
			try {
				Hesiod h = Hesiod.getInstance();
				HesiodResult hr = h.lookup("moira_db", "service");
				String result = hr.getResults(0);
				String[] parts = result.split("\\s");
				portNum = Integer.parseInt(parts[2]);
			} catch (Exception e) {
				// ignore
			}
			
			// TODO /etc/services
			// getportbyname(moira_db, tcp) => 775

			// If no other matches, check whether port string matches default
			if (portNum == -1 && port.equals(Constants.DEFAULT_SERVICE)) {
				portNum = Constants.DEFAULT_PORT;
			}
			if (portNum == -1) {
				return null;
			}
		}

		/* Do magic mrgdb initialization */
		
		Socket mrSock = null;
		try {
			// Re-interpret the challenge and response strings as plain ASCII bytes.
			byte[] challenge = challengeStr.getBytes("ISO-8859-1");
			challenge = chal;
			byte[] response = responseStr.getBytes("ISO-8859-1");
			response = resp;
//			challenge[10] += 1;
//			response[10] += 1;
			byte[] actualresponse = new byte[response.length];
			int size, more;

			mrSock = new Socket(server, portNum);
			mrSock.setKeepAlive(true);


			OutputStream outS = mrSock.getOutputStream();
			outS.write(challenge);
			outS.flush();

			InputStream inS = mrSock.getInputStream();
			for (size = 0; size < actualresponse.length; size += more) {
				more = inS.read(actualresponse, size, actualresponse.length - size);
				if (more <= 0) {
					break;
				}
			}

			String actualresponseStr = new String(actualresponse, "ISO-8859-1");
			if (! responseStr.equals(actualresponseStr)) {
				throw new MoiraException("Challenge/response failure during initial connection.");
			}
		} catch (UnsupportedEncodingException e) {
			// This should never happen, the Java spec says that "ISO-8859-1"
			// should always be a supported encoding.
			throw new java.lang.InternalError(
					"Internal error during MRGDB handshake: " + e);
		} catch (UnknownHostException e) {
			System.err.println("Couldn't look up the Moira server's address.");
			System.err.println(e);
		} catch (SocketException e) {
			System.err.println("Connection problem.");
			System.err.println(e);
		} catch (IOException e) {
			System.err.println("I/O error while establishing connection.");
			System.err.println(e);
		} catch (MoiraException e) {
			System.err.println(e);
		}

		return mrSock;
	}
	
	int mr_do_call(MoiraParams params, MoiraParams reply) {
		int status;
		status = mr_send(params);
		if (status == Constants.MR_SUCCESS) {
			status = mr_receive(reply);
		}
		return status;
	}
	
	int mr_send(MoiraParams params) {
		ByteBuffer buf;
		int rel, padding;
		int bufLength = 0;
		List<Integer> argl = new ArrayList<Integer>();
		
		bufLength += 16; // length + version + opcode/status + arg count
		
		// For each arg, buf need to be long enough to contain
		// arg length + the arg itself + a few bytes of padding
		if (params.argl != null) {
			for (int i : params.argl) {
				argl.add(i);
				bufLength += 4 + i + 4;
			}
		} else {
			for (byte[] b : params.args) {
				argl.add(b.length);
				bufLength += 4 + b.length + 4;
			}
		}
		byte[] bArray = new byte[bufLength];
		Arrays.fill(bArray, (byte) 0);
		buf = ByteBuffer.wrap(bArray);
		buf.position(4);                 // write bufLength later.
		buf.putInt(Constants.MR_VERSION_2);
		buf.putInt(params.moiraProcNo);
		buf.putInt(params.args.length);
		for (int i=0; i<params.args.length; i++) {
			buf.putInt(argl.get(i));
			buf.put(params.args[i]);
			int p = buf.position();
			rel = p % 4;              // 0,1,2,3
			padding = (4 - rel) % 4;  // 0,3,2,1
			buf.position( p+padding );
		}
		// The initial estimate for bufLength allocated 4 bytes for the
		// padding for each arg, but we really only used 0-3 bytes each.
		bufLength = buf.position();   // get true bufLength
		buf.putInt(bufLength,0);      // write bufLength
		buf.limit(bufLength);
		buf.rewind();

		OutputStream outS = null;
		try {
			outS = conn.getOutputStream();
			outS.write(bArray);
			outS.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MoiraET.MR_ABORTED;
		}

		return Constants.MR_SUCCESS;
	}

	int mr_receive(MoiraParams reply) {
		int status = -1;

		while (status == -1) {
		    status = mr_cont_receive(reply);
		}
		return status;
	}

	int mr_cont_receive(MoiraParams reply) {
		int size, more;
		int length;
		ByteBuffer rbuf;
		InputStream inS;

		try {
			inS = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return MoiraET.MR_NOT_CONNECTED;
		}

		if (reply.buf == null) {
			byte[] lbuf = new byte[4];
			try {
				size = inS.read(lbuf, 0, 4);
				if (size != 4) {
					if (size == 0)
						return MoiraET.MR_NOT_CONNECTED;
					else
						return MoiraET.MR_ABORTED;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return MoiraET.MR_NOT_CONNECTED;
			}
			ByteBuffer lbbuf = ByteBuffer.wrap(lbuf);
			length = lbbuf.getInt();
			if (length > 8192)
				return MoiraET.MR_INTERNAL;
			reply.buf = ByteBuffer.allocate(length);
			reply.buf.putInt(length);
			return -1;
		}
		else {
			rbuf = reply.buf;
			length = rbuf.getInt(0);
		}
		
		byte[] backing = rbuf.array();
		int p = rbuf.position();
		int limit = rbuf.limit();
		try {
			more = inS.read(backing, p, limit - p);
			rbuf.position( p+more );
			if (p+more < length)
				return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return MoiraET.MR_ABORTED;
		}
		
		// If we reach here, then all data has been read
		// and stored into rbuf.
		rbuf.rewind();
		rbuf.position(4);  // no need to re-read length

		if (rbuf.getInt() != Constants.MR_VERSION_2)
			return MoiraET.MR_VERSION_MISMATCH;
		
		reply.moiraStatus = rbuf.getInt();
		int argc = rbuf.getInt();
		if (argc > (rbuf.remaining())/8 )
			return MoiraET.MR_INTERNAL;
		
		reply.argl = new int[argc];
		reply.args = new byte[argc][];
		for (int i=0; i<argc; i++) {
			int len = rbuf.getInt();
			byte[] b = new byte[len];
			rbuf.get(b, 0, len);
			reply.args[i] = b;
			reply.argl[i] = len;
			
			p = rbuf.position();
			int rel = p % 4;              // 0,1,2,3
			int padding = (4 - rel) % 4;  // 0,3,2,1
			rbuf.position( p+padding );

		}
			
		return Constants.MR_SUCCESS;
	}

	void run(String[] args) {
		;
	}
	
	public static void main(String[] args) {
        MoiraConnection mr_conn = new MoiraConnection();
        mr_conn.run(args);

		int result = MoiraConnection.mr_connect("ttsp.mit.edu");
		System.out.format("result = %d\n", result);
	}

}
