package churilla.mark.toolrental.exception;

/**
 * Exception thrown when an error occurs during the initialization of tool data.
 * <p>
 * This exception typically occurs when the application encounters issues loading
 * tool data from an external source, such as a file or database.
 * </p>
 */
public class ToolDataInitializationException extends RuntimeException {
    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public ToolDataInitializationException(final String message) {
       super(message);
   }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param cause The source exception.
     */
   public ToolDataInitializationException(final String message, final Exception cause) {
       super(message, cause);
   }
}
