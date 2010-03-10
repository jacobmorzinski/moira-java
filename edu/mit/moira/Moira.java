package edu.mit.moira;

import edu.mit.moira.internal.Constants;
import edu.mit.moira.internal.KrbET;
import edu.mit.moira.internal.MITCopyright;
import edu.mit.moira.internal.MoiraET;
import edu.mit.moira.internal.UserRegET;


public class Moira {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int result = MoiraConnection.mr_connect("ttsp.mit.edu");
		System.out.format("result = %d\n", result);
	}
}
