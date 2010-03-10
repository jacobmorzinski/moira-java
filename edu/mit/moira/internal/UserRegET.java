package edu.mit.moira.internal;

import java.util.Hashtable;

public class UserRegET {
	private static Hashtable<Long, String> errMsgList;

	public static String getErrorMessage(long i) {
	    return errMsgList.get(i);
	}
	
	public static final long UREG_ALREADY_REGISTERED = -1094196992L;
	public static final long UREG_USER_NOT_FOUND = -1094196991L;
	public static final long UREG_UNKNOWN_HOST = -1094196990L;
	public static final long UREG_UNKNOWN_SERVICE = -1094196989L;
	public static final long UREG_UNKNOWN_REQUEST = -1094196988L;
	public static final long UREG_BROKEN_PACKET = -1094196987L;
	public static final long UREG_WRONG_VERSION = -1094196986L;
	public static final long UREG_LOGIN_USED = -1094196985L;
	public static final long UREG_INVALID_UNAME = -1094196984L;
	public static final long UREG_NO_PASSWD_YET = -1094196983L;
	public static final long UREG_NO_LOGIN_YET = -1094196982L;
	public static final long UREG_DELETED = -1094196981L;
	public static final long UREG_NOT_ALLOWED = -1094196980L;
	public static final long UREG_KRB_TAKEN = -1094196979L;
	public static final long UREG_MISC_ERROR = -1094196978L;
	public static final long UREG_ENROLLED = -1094196977L;
	public static final long UREG_ENROLL_NOT_ALLOWED = -1094196976L;
	public static final long UREG_HALF_ENROLLED = -1094196975L;
	
	static {
		errMsgList = new Hashtable<Long, String>();
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
