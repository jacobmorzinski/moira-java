package edu.mit.moira;

import java.io.UnsupportedEncodingException;

public class MoiraParams {
	int moiraProcNo;    // C: unsigned long
	int moiraStatus;    // C: unsigned long (sometimes holds signed data)
	byte[][] args;      // array of null-terminated byte-arrays: {{'h','i','\0'}, ...} 
	int[] argl = null;  // optional - precomputed length of each child array of the args array
	byte[] buffer;      // for receiving data that takes multiple recv() calls.
	int bufferFilled;
}
