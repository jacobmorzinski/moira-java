package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

public class Test {
	public static void main(String[] args) {
		String s = "Hello World";
		byte[] b = null;
		String csn;
		csn = "US-ASCII";
		try {
			b = s.getBytes(csn);
			System.out.println(new String(b, csn));
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("internal error " + e);
		}
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("one", "1");
		ht.put("two", "2");
		Enumeration<String> enm = ht.elements();
		ArrayList<String> al = Collections.list(enm);
		LinkedList<String> ll = new LinkedList<String>(al);
		System.out.println("al = " + al);
		System.out.println("ll = " + ll);
	}
}
