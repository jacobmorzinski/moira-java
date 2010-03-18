package edu.mit.moira;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class MoiraParams {
	int moiraProcNo; // C: unsigned long
	int moiraStatus; // C: unsigned long (sometimes holds signed data)
	byte[][] args;   // array of null-terminated byte-arrays: {{'h','i','\0'}, ...} 
	int[] argl;      // optional - precomputed length of each child array of the args array
	ByteBuffer buf;  // holding space for raw received data
}
