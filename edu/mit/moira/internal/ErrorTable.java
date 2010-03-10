package edu.mit.moira.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;

public class ErrorTable {

	static String pkg = ErrorTable.class.getName();
	static { 
		final int i = pkg.lastIndexOf(".");
		pkg = pkg.substring(0,i);
		pkg = pkg.replace(".", "/");
	}
	public static ClassLoader cl = ErrorTable.class.getClassLoader();
	public static URL mr_et = cl.getResource(pkg + "/mr_et.et");
	public static URL krb_et = cl.getResource(pkg + "/krb_et.et");
	public static URL ureg_et = cl.getResource(pkg + "/ureg_et.et");

	private static Hashtable<Long, String> errMsgList;

	public static String getErrorMessage(long i) {
		return errMsgList.get(i);
	}

	static {
		InputStream mr_et_stream = null;
		try {
			mr_et_stream = mr_et.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStreamReader reader = new InputStreamReader(mr_et_stream);
		BufferedReader br = new BufferedReader(reader);
		if (br != null) {
			try {
				String line;
				while (true) {
					line = br.readLine();
					if (line == null) {
						break;
					}
//					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
