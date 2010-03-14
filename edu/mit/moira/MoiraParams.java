package edu.mit.moira;

public class MoiraParams {
	int moiraProcNo;    // C: unsigned long
	int moiraStatus;    // C: unsigned long
	byte[][] args;      // array of byte[]
	int[] argl = null;  // can precompute lengths of each args?
	byte[] buffer;      // for receiving data that takes multiple recv() calls.
	int bufferFilled;
}
