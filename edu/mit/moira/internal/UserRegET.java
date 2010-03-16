package edu.mit.moira.internal;

import java.util.Hashtable;

public class UserRegET {
	private static Hashtable<Integer, String> errMsgList;

	public static String getErrorMessage(long i) {
	    return errMsgList.get(i);
	}
	
	public static final int UREG_ALREADY_REGISTERED = -1094196992;
	public static final int UREG_USER_NOT_FOUND = -1094196991;
	public static final int UREG_UNKNOWN_HOST = -1094196990;
	public static final int UREG_UNKNOWN_SERVICE = -1094196989;
	public static final int UREG_UNKNOWN_REQUEST = -1094196988;
	public static final int UREG_BROKEN_PACKET = -1094196987;
	public static final int UREG_WRONG_VERSION = -1094196986;
	public static final int UREG_LOGIN_USED = -1094196985;
	public static final int UREG_INVALID_UNAME = -1094196984;
	public static final int UREG_NO_PASSWD_YET = -1094196983;
	public static final int UREG_NO_LOGIN_YET = -1094196982;
	public static final int UREG_DELETED = -1094196981;
	public static final int UREG_NOT_ALLOWED = -1094196980;
	public static final int UREG_KRB_TAKEN = -1094196979;
	public static final int UREG_MISC_ERROR = -1094196978;
	public static final int UREG_ENROLLED = -1094196977;
	public static final int UREG_ENROLL_NOT_ALLOWED = -1094196976;
	public static final int UREG_HALF_ENROLLED = -1094196975;
	
	static {
		errMsgList = new Hashtable<Integer, String>();
		errMsgList.put(UREG_ALREADY_REGISTERED, "User already registered");
		errMsgList.put(UREG_USER_NOT_FOUND, "Unable to locate user in database");
		errMsgList.put(UREG_UNKNOWN_HOST, "Unknown host Moira");
		errMsgList.put(UREG_UNKNOWN_SERVICE, "Unknown service");
		errMsgList.put(UREG_UNKNOWN_REQUEST, "Unknown request to userreg server");
		errMsgList.put(UREG_BROKEN_PACKET, "Unable to parse request packet");
		errMsgList.put(UREG_WRONG_VERSION, "Wrong version of protocol");
		errMsgList.put(UREG_LOGIN_USED, "That login name is already in use");
		errMsgList.put(UREG_INVALID_UNAME, "Not valid as a login name");
		errMsgList.put(UREG_NO_PASSWD_YET, "Password not yet set.");
		errMsgList.put(UREG_NO_LOGIN_YET, "Cannot set password when no login name set.");
		errMsgList.put(UREG_DELETED, "Account is marked for deletion.");
		errMsgList.put(UREG_NOT_ALLOWED, "You may not register at this time.");
		errMsgList.put(UREG_KRB_TAKEN, "Authentication setup failed.");
		errMsgList.put(UREG_MISC_ERROR, "System error.");
		errMsgList.put(UREG_ENROLLED, "Enrolled in campus namespace.");
		errMsgList.put(UREG_ENROLL_NOT_ALLOWED, "Enrolled in campus namespace, not eligable for an Athena account.");
		errMsgList.put(UREG_HALF_ENROLLED, "Halfway enrolled in campus namespace.");
	}
}
