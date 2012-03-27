package edu.mit.moira;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import edu.mit.moira.internal.Constants;

public class MoiraParams {
	int opcode;      // (C: unsigned long) call procedure number -OR- reply status
	byte[][] args;   // array of null-terminated byte-arrays: {{'h','i','\0'}, ...} 
	int[] argl;      // optional - precomputed length of each child array of the args array
	ByteBuffer buf;  // holding space for raw received data
	
	public MoiraParams(int opcode, String... strings) {
		this.opcode = opcode;
		this.args = new byte[strings.length][];
		this.argl = new int[strings.length];
		
		Charset iso8859 = Charset.forName("ISO-8859-1");
		for (int i=0; i<strings.length; i++) {
			byte[] ba = strings[i].getBytes(iso8859);
			this.args[i] = Arrays.copyOf(ba, ba.length + 1);
			this.argl[i] = ba.length + 1;
		}
	}
	
	
	public MoiraParams(int opcode, byte[][] args) {
		this.opcode = opcode;
		this.args = new byte[args.length][];
		this.argl = new int[args.length];
		for (int i=0; i<args.length; i++) {
			this.argl[i] = args[i].length;
			this.args[i] = new byte[argl[i]];
			System.arraycopy(args[i], 0, this.args[i], 0, args[i].length);
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[" + opcode + "] ");
		for (byte[] ba : args) {
			for (byte b : ba) {
				if (b == 0) { break; }
				sb.append((char) b);
			}
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}



	public static void main(String... args) {
		MoiraParams mp = new MoiraParams(Constants.MR_QUERY, "glin", "gaccounts");
		System.out.println(mp);
	}
}
