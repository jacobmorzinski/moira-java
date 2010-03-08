package mit.moira;

public class MoiraException extends Exception {

	/*
	 * Compiler-generated serialVersionUID 
	 */
	private static final long serialVersionUID = -5620037730628897913L;

	/**
	 * Constructs an {@code MoiraException} with {@code null}
	 * as its error detail message.
	 */
	public MoiraException() {
		super();
	}

	/**
	 * Constructs an {@code MoiraException} with the specified detail message.
	 *
	 * @param message
	 *        The detail message (which is saved for later retrieval
	 *        by the {@link #getMessage()} method)
	 */
	public MoiraException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code MoiraException} with the specified detail message
	 * and cause.
	 *
	 * <p> Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated into this exception's detail
	 * message.
	 *
	 * @param message
	 *        The detail message (which is saved for later retrieval
	 *        by the {@link #getMessage()} method)
	 *
	 * @param cause
	 *        The cause (which is saved for later retrieval by the
	 *        {@link #getCause()} method).  (A null value is permitted,
	 *        and indicates that the cause is nonexistent or unknown.)
	 */
	public MoiraException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code MoiraException} with the specified cause and a
	 * detail message of {@code (cause==null ? null : cause.toString())}
	 * (which typically contains the class and detail message of {@code cause}).
	 * This constructor is useful for exceptions that are little more
	 * than wrappers for other throwables.
	 *
	 * @param cause
	 *        The cause (which is saved for later retrieval by the
	 *        {@link #getCause()} method).  (A null value is permitted,
	 *        and indicates that the cause is nonexistent or unknown.)
	 */
	public MoiraException(Throwable cause) {
		super(cause);
	}

}
