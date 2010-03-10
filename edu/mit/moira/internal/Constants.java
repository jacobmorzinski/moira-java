package edu.mit.moira.internal;

public class Constants {

    /*
     * moira.h
     *
     */

    /* return values from queries (and error codes) */

    public static final int MR_SUCCESS = 0;		/* Query was successful */

    public static final int MR_VERSION_1 = 1;		/* Version in use from 7/87 to 4/88 */
    public static final int MR_VERSION_2 = 2;		/* After 4/88, new query lib */

    /* return values for Moira server calls, used by clients */

    public static final int MR_CONT = 0;		/* Everything okay, continue sending values. */
    public static final int MR_ABORT = -1;		/* Something went wrong don't send anymore values. */

    public static final String MOIRA_SNAME = "moira";	/* Kerberos service key to use */

    /* Protocol operations */
    public static final int MR_NOOP = 0;
    public static final int MR_AUTH = 1;
    public static final int MR_SHUTDOWN = 2;
    public static final int MR_QUERY = 3;
    public static final int MR_ACCESS = 4;
    public static final int MR_DO_UPDATE = 5;
    public static final int MR_MOTD = 6;
    public static final int MR_PROXY = 7;
    public static final int MR_SETVERSION = 8;
    public static final int MR_KRB5_AUTH = 9;
    public static final int MR_MAX_PROC = 9;

    /* values used in NFS physical flags */
    public static final int MR_FS_STUDENT = 0x0001;
    public static final int MR_FS_FACULTY = 0x0002;
    public static final int MR_FS_STAFF = 0x0004;
    public static final int MR_FS_MISC = 0x0008;
    public static final int MR_FS_GROUPQUOTA = 0x0010;

    /* magic values to pass for list and user queries */
    public static final String UNIQUE_GID = "create unique GID";
    public static final String UNIQUE_UID = "create unique UID";
    public static final String UNIQUE_LOGIN = "create unique login ID";

    /*
     * mrclient.h
     * 
     */

    public static final int MRCL_SUCCESS = 0;
    public static final int MRCL_FAIL = 1;
    public static final int MRCL_REJECT = 2;
    public static final int MRCL_WARN = 3;
    public static final int MRCL_ENOMEM = 4;
    public static final int MRCL_MOIRA_ERROR = 5;
    public static final int MRCL_AUTH_ERROR = 6;


    /*
     * mr_krb.h
     *
     */

    public static final int ANAME_SZ = 40;
    public static final int INST_SZ = 40;
    public static final int REALM_SZ = 40;
    /* include space for '.' and '@' */
    public static final int MAX_K_NAME_SZ = (ANAME_SZ + INST_SZ + REALM_SZ + 2);

    public static final String KRB_REALM = "ATHENA.MIT.EDU";



    /*
     * moira_site.h
     *
     */

    /* Default Moira server to connect to */
    public static final int DEFAULT_PORT = 775;
    public static final String DEFAULT_SERVICE = "moira_db";
    public static final String MOIRA_SERVER = "MOIRA.MIT.EDU:" + DEFAULT_SERVICE;


    /* get_ace_use */

    public static final int ACE_TYPE = 0;
    public static final int ACE_NAME = 1;
    public static final int ACE_END = 2;

    /* acl queries */

    public static final int ACL_HOST = 0;
    public static final int ACL_TARGET = 1;
    public static final int ACL_KIND = 2;
    public static final int ACL_LIST = 3;
    public static final int ACL_MODTIME = 4;
    public static final int ACL_MODBY = 5;
    public static final int ACL_MODWITH = 6;
    public static final int ACL_END = 7;

    /* alias queries */

    public static final int ALIAS_NAME = 0;
    public static final int ALIAS_TYPE = 1;
    public static final int ALIAS_TRANS = 2;
    public static final int ALIAS_END = 3;

    /* Cluster information queries */

    public static final int C_NAME = 0;
    public static final int C_DESCRIPT = 1;
    public static final int C_LOCATION = 2;
    public static final int C_MODTIME = 3;
    public static final int C_MODBY = 4;
    public static final int C_MODWITH = 5;
    public static final int C_END = 6;

    /* Cluster Data information queries */

    public static final int CD_NAME = 0;
    public static final int CD_LABEL = 1;
    public static final int CD_DATA = 2;
    public static final int CD_END = 3;

    /* Delete Member from list queries. */

    public static final int DM_LIST = 0;
    public static final int DM_TYPE = 1;
    public static final int DM_MEMBER = 2;
    public static final int DM_END = 3;

    /* Finger queries */

    public static final int F_NAME = 0;
    public static final int F_FULLNAME = 1;
    public static final int F_NICKNAME = 2;
    public static final int F_HOME_ADDR = 3;
    public static final int F_HOME_PHONE = 4;
    public static final int F_OFFICE_ADDR = 5;
    public static final int F_OFFICE_PHONE = 6;
    public static final int F_MIT_DEPT = 7;
    public static final int F_MIT_AFFIL = 8;
    public static final int F_MODTIME = 9;
    public static final int F_MODBY = 10;
    public static final int F_MODWITH = 11;
    public static final int F_END = 12;

    /* Filesys queries */

    public static final int FS_NAME = 0;
    public static final int FS_TYPE = 1;
    public static final int FS_MACHINE = 2;
    public static final int FS_PACK = 3;
    public static final int FS_M_POINT = 4;
    public static final int FS_ACCESS = 5;
    public static final int FS_COMMENTS = 6;
    public static final int FS_OWNER = 7;
    public static final int FS_OWNERS = 8;
    public static final int FS_CREATE = 9;
    public static final int FS_L_TYPE = 10;
    public static final int FS_MODTIME = 11;
    public static final int FS_MODBY = 12;
    public static final int FS_MODWITH = 13;
    public static final int FS_END = 14;

    /* Get List Of Member queries. */

    public static final int GLOM_NAME = 0;
    public static final int GLOM_ACTIVE = 1;
    public static final int GLOM_PUBLIC = 2;
    public static final int GLOM_HIDDEN = 3;
    public static final int GLOM_MAILLIST = 4;
    public static final int GLOM_GROUP = 5;
    public static final int GLOM_END = 6;

    /* Kerberos/User Map */

    public static final int KMAP_USER = 0;
    public static final int KMAP_PRINCIPAL = 1;
    public static final int KMAP_END = 2;

    /* General List information Queries, v4. */

    public static final int L_NAME = 0;
    public static final int L_ACTIVE = 1;
    public static final int L_PUBLIC = 2;
    public static final int L_HIDDEN = 3;
    public static final int L_MAILLIST = 4;
    public static final int L_GROUP = 5;
    public static final int L_GID = 6;
    public static final int L_NFSGROUP = 7;
    public static final int L_MAILMAN = 8;
    public static final int L_MAILMAN_SERVER = 9;
    public static final int L_ACE_TYPE = 10;
    public static final int L_ACE_NAME = 11;
    public static final int L_MEMACE_TYPE = 12;
    public static final int L_MEMACE_NAME = 13;
    public static final int L_DESC = 14;
    public static final int L_MODTIME = 15;
    public static final int L_MODBY = 16;
    public static final int L_MODWITH = 17;
    public static final int L_END = 18;

    /* List Member information queries. */

    public static final int LM_LIST = 0;
    public static final int LM_TYPE = 1;
    public static final int LM_MEMBER = 2;
    public static final int LM_END = 3;

    public static final int LM_TAG = 3;
    public static final int LM_TAG_END = 4;

    /* Machine information queries */

    public static final int M_NAME = 0;
    public static final int M_VENDOR = 1;
    public static final int M_TYPE = 1;
    public static final int M_MODEL = 2;
    public static final int M_OS = 3;
    public static final int M_LOC = 4;
    public static final int M_CONTACT = 5;
    public static final int M_BILL_CONTACT = 6;
    public static final int M_ACCT_NUMBER = 7;
    public static final int M_USE = 8;
    public static final int M_STAT = 9;
    public static final int M_STAT_CHNG = 10;
    public static final int M_SUBNET = 11;
    public static final int M_ADDR = 12;
    public static final int M_OWNER_TYPE = 13;
    public static final int M_OWNER_NAME = 14;
    public static final int M_ACOMMENT = 15;
    public static final int M_OCOMMENT = 16;
    public static final int M_CREATED = 17;
    public static final int M_CREATOR = 18;
    public static final int M_INUSE = 19;
    public static final int M_MODTIME = 20;
    public static final int M_MODBY = 21;
    public static final int M_MODWITH = 22;
    public static final int M_END = 23;

    /*  Machine to Cluster mapping */

    public static final int MAP_MACHINE = 0;
    public static final int MAP_CLUSTER = 1;
    public static final int MAP_END = 2;

    /*  NFS phys. queries. */

    public static final int NFS_NAME = 0;
    public static final int NFS_DIR = 1;
    public static final int NFS_DEVICE = 2;
    public static final int NFS_STATUS = 3;
    public static final int NFS_ALLOC = 4;
    public static final int NFS_SIZE = 5;
    public static final int NFS_MODTIME = 6;
    public static final int NFS_MODBY = 7;
    public static final int NFS_MODWITH = 8;
    public static final int NFS_END = 9;

    /* Printer queries */

    public static final int PRN_NAME = 0;
    public static final int PRN_TYPE = 1;
    public static final int PRN_HWTYPE = 2;
    public static final int PRN_DUPLEXNAME = 3;
    public static final int PRN_HOSTNAME = 4;
    public static final int PRN_LOGHOST = 5;
    public static final int PRN_RM = 6;
    public static final int PRN_RP = 7;
    public static final int PRN_RQ = 8;
    public static final int PRN_KA = 9;
    public static final int PRN_PC = 10;
    public static final int PRN_AC = 11;
    public static final int PRN_LPC_ACL = 12;
    public static final int PRN_BANNER = 13;
    public static final int PRN_LOCATION = 14;
    public static final int PRN_CONTACT = 15;
    public static final int PRN_MODTIME = 16;
    public static final int PRN_MODBY = 17;
    public static final int PRN_MODWITH = 18;
    public static final int PRN_END = 19;

    public static final int PRN_BANNER_NONE = 0;
    public static final int PRN_BANNER_FIRST = 1;
    public static final int PRN_BANNER_LAST = 2;

    /* Print Server queries */

    public static final int PRINTSERVER_HOST = 0;
    public static final int PRINTSERVER_KIND = 1;
    public static final int PRINTSERVER_TYPES = 2;
    public static final int PRINTSERVER_OWNER_TYPE = 3;
    public static final int PRINTSERVER_OWNER_NAME = 4;
    public static final int PRINTSERVER_LPC_ACL = 5;
    public static final int PRINTSERVER_MODTIME = 6;
    public static final int PRINTSERVER_MODBY = 7;
    public static final int PRINTSERVER_MODWITH = 8;
    public static final int PRINTSERVER_END = 9;

    /* PO box information queries */

    public static final int PO_NAME = 0;
    public static final int PO_TYPE = 1;
    public static final int PO_BOX = 2;
    public static final int PO_ADDR = 3;
    public static final int PO_END = 4;

    /* Quota queries */

    public static final int Q_FILESYS = 0;
    public static final int Q_TYPE = 1;
    public static final int Q_NAME = 2;
    public static final int Q_QUOTA = 3;
    public static final int Q_DIRECTORY = 4;
    public static final int Q_MACHINE = 5;
    public static final int Q_MODTIME = 6;
    public static final int Q_MODBY = 7;
    public static final int Q_MODWITH = 8;
    public static final int Q_END = 9;

    /* Service info */

    public static final int SVC_SERVICE = 0;
    public static final int SVC_INTERVAL = 1;
    public static final int SVC_TARGET = 2;
    public static final int SVC_SCRIPT = 3;
    public static final int SVC_DFGEN = 4;
    public static final int SVC_DFCHECK = 5;
    public static final int SVC_TYPE = 6;
    public static final int SVC_ENABLE = 7;
    public static final int SVC_INPROGRESS = 8;
    public static final int SVC_HARDERROR = 9;
    public static final int SVC_ERRMSG = 10;
    public static final int SVC_ACE_TYPE = 11;
    public static final int SVC_ACE_NAME = 12;
    public static final int SVC_MODTIME = 13;
    public static final int SVC_MODBY = 14;
    public static final int SVC_MODWITH = 15;
    public static final int SVC_END = 16;

    /* Service add/update */

    public static final int SC_SERVICE = 0;
    public static final int SC_INTERVAL = 1;
    public static final int SC_TARGET = 2;
    public static final int SC_SCRIPT = 3;
    public static final int SC_TYPE = 4;
    public static final int SC_ENABLE = 5;
    public static final int SC_ACE_TYPE = 6;
    public static final int SC_ACE_NAME = 7;
    public static final int SC_END = 8;

    /* Service/host tuples */

    public static final int SH_SERVICE = 0;
    public static final int SH_MACHINE = 1;
    public static final int SH_ENABLE = 2;
    public static final int SH_OVERRIDE = 3;
    public static final int SH_SUCCESS = 4;
    public static final int SH_INPROGRESS = 5;
    public static final int SH_HOSTERROR = 6;
    public static final int SH_ERRMSG = 7;
    public static final int SH_LASTTRY = 8;
    public static final int SH_LASTSUCCESS = 9;
    public static final int SH_VALUE1 = 10;
    public static final int SH_VALUE2 = 11;
    public static final int SH_VALUE3 = 12;
    public static final int SH_MODTIME = 13;
    public static final int SH_MODBY = 14;
    public static final int SH_MODWITH = 15;
    public static final int SH_END = 16;

    /* Service/host tuple add & updates */

    public static final int SHI_SERVICE = 0;
    public static final int SHI_MACHINE = 1;
    public static final int SHI_ENABLE = 2;
    public static final int SHI_VALUE1 = 3;
    public static final int SHI_VALUE2 = 4;
    public static final int SHI_VALUE3 = 5;
    public static final int SHI_END = 6;

    /* Subnet info */

    public static final int SN_NAME = 0;
    public static final int SN_DESC = 1;
    public static final int SN_STATUS = 2;
    public static final int SN_CONTACT = 3;
    public static final int SN_ACCT_NUMBER = 4;
    public static final int SN_ADDRESS = 5;
    public static final int SN_MASK = 6;
    public static final int SN_LOW = 7;
    public static final int SN_HIGH = 8;
    public static final int SN_PREFIX = 9;
    public static final int SN_ACE_TYPE = 10;
    public static final int SN_ACE_NAME = 11;
    public static final int SN_MODTIME = 12;
    public static final int SN_MODBY = 13;
    public static final int SN_MODWITH = 14;
    public static final int SN_END = 15;

    /* Subnet statuses */
    public static final int SNET_STATUS_RESERVED = 0;
    public static final int SNET_STATUS_BILLABLE = 1;
    public static final int SNET_STATUS_PRIVATE_10MBPS = 2;
    public static final int SNET_STATUS_PRIVATE_100MBPS = 3;
    public static final int SNET_STATUS_PRIVATE_OTHER = 4;
    public static final int SNET_STATUS_RESNET_DORM = 5;
    public static final int SNET_STATUS_INFRASTRUCTURE = 6;
    public static final int SNET_STATUS_PRIVATE_1000MBPS = 7;
    public static final int SNET_STATUS_RESNET_FSILG = 8;

    /* User Information queries, v11 */

    public static final int U_NAME = 0;
    public static final int U_UID = 1;
    public static final int U_SHELL = 2;
    public static final int U_WINCONSOLESHELL = 3;
    public static final int U_LAST = 4;
    public static final int U_FIRST = 5;
    public static final int U_MIDDLE = 6;
    public static final int U_STATE = 7;
    public static final int U_MITID = 8;
    public static final int U_CLASS = 9;
    public static final int U_COMMENT = 10;
    public static final int U_SIGNATURE = 11;
    public static final int U_SECURE = 12;
    public static final int U_WINHOMEDIR = 13;
    public static final int U_WINPROFILEDIR = 14;
    public static final int U_SPONSOR_TYPE = 15;
    public static final int U_SPONSOR_NAME = 16;
    public static final int U_EXPIRATION = 17;
    public static final int U_MODTIME = 18;
    public static final int U_MODBY = 19;
    public static final int U_MODWITH = 20;
    public static final int U_CREATED = 21;
    public static final int U_CREATOR = 22;
    public static final int U_END = 23;

    /* User states (the value of argv[U_STATE] from a user query) */

    public static final int US_NO_LOGIN_YET = 0;
    public static final int US_REGISTERED = 1;
    public static final int US_NO_PASSWD = 2;
    public static final int US_DELETED = 3;
    public static final int US_NOT_ALLOWED = 4;
    public static final int US_ENROLLED = 5;
    public static final int US_ENROLL_NOT_ALLOWED = 6;
    public static final int US_HALF_ENROLLED = 7;
    public static final int US_NO_LOGIN_YET_KERBEROS_ONLY = 8;
    public static final int US_REGISTERED_KERBEROS_ONLY = 9;
    public static final int US_END = 10;

    /* User shell queries */

    public static final int USH_NAME = 0;
    public static final int USH_SHELL = 1;
    public static final int USH_END = 2;

    /* Zephyr ACL queries */

    public static final int ZA_CLASS = 0;
    public static final int ZA_XMT_TYPE = 1;
    public static final int ZA_XMT_ID = 2;
    public static final int ZA_SUB_TYPE = 3;
    public static final int ZA_SUB_ID = 4;
    public static final int ZA_IWS_TYPE = 5;
    public static final int ZA_IWS_ID = 6;
    public static final int ZA_IUI_TYPE = 7;
    public static final int ZA_IUI_ID = 8;
    public static final int ZA_OWNER_TYPE = 9;
    public static final int ZA_OWNER_ID = 10;
    public static final int ZA_MODTIME = 11;
    public static final int ZA_MODBY = 12;
    public static final int ZA_MODWITH = 13;
    public static final int ZA_END = 14;

    /* Container queries */

    public static final int CON_NAME = 0;
    public static final int CON_PUBLIC = 1;
    public static final int CON_DESCRIPT = 2;
    public static final int CON_LOCATION = 3;
    public static final int CON_CONTACT = 4;
    public static final int CON_OWNER_TYPE = 5;
    public static final int CON_OWNER_NAME = 6;
    public static final int CON_MEMACE_TYPE = 7;
    public static final int CON_MEMACE_NAME = 8;
    public static final int CON_MODTIME = 9;
    public static final int CON_MODBY = 10;
    public static final int CON_MODWITH = 11;
    public static final int CON_END = 12;

}
