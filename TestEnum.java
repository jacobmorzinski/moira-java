import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TestEnum {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		System.out.println(args.length);
		System.out.println(smallET.UREG_UNKNOWN_HOST);
		System.out.println(smallET.get(-1094196992));
		ByteBuffer b = ByteBuffer.allocate(40);
		b.putInt(0).putInt(smallET.UREG_ALREADY_REGISTERED.code);
		b.flip();
		System.out.println(b.getLong());
	}
	
	public enum smallET {
		UREG_ALREADY_REGISTERED(-1094196992, "User already registered"),
		UREG_USER_NOT_FOUND(-1094196991, "Unable to locate user in database"),
		UREG_UNKNOWN_HOST(-1094196990, "Unknown host Moira"),
		;

		final int code;
		final String message;
		private static final Map<Integer,smallET> lookup;
		static {
			lookup = new HashMap<Integer,smallET>();
			for (smallET et : EnumSet.allOf(smallET.class)) {
				lookup.put(et.code, et);
			}
		}
		
		private smallET(int code, String message) {
			this.code = code;
			this.message = message;
		}
		
		public static smallET get(int code) {
			return lookup.get(code); 
		}
		
		@Override
		public String toString() {
			return super.name() + "(" + code + ") : " + message;
		}
		
		
	}

}
