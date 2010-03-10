package mit.moira;

import mit.moira.internal.KrbET;
import mit.moira.internal.MoiraET;
import mit.moira.internal.UserRegET;
import mit.moira.internal.Constants;
import mit.moira.internal.MITCopyright;


public class Moira {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int result = MoiraConnection.mr_connect("ttsp.mit.edu");
		System.out.format("result = %d\n", result);
	}
}
