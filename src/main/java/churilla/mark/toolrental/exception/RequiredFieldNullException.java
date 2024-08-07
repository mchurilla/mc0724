package churilla.mark.toolrental.exception;

/**
 * Exception thrown when a required field is null.
 * <p>
 * This exception indicates that a field that is expected to be non-null (i.e., a required field)
 * is found to be null. It is typically used in scenarios where certain fields must be provided
 * for the proper functioning of an operation or object instantiation, and a null value for such
 * fields is considered a critical error.
 * </p><p>
 * This exception requires that the name of the required field be passed to the constructor and can be
 * accessed via a getter for use in custom messages or for logging purposes.
 * </p>
 */
public class RequiredFieldNullException extends RuntimeException {

    private final String requiredFieldName;

    /**
     * Constructor with the required field name and a message.
     *
     * @param requiredFieldName The name of the missing required field.
     * @param message A message detailing the problem that has occurred.
     */
    public RequiredFieldNullException(final String requiredFieldName, final String message) {
        super(message);
        this.requiredFieldName = requiredFieldName;
    }

    /**
     * Constructor with the missing required field name, a message and source exception.
     *
     * @param requiredFieldName The name of the missing required field.
     * @param message A Message detailing the problem that has occurred.
     * @param cause The initial cause of the exception.
     */
    public RequiredFieldNullException(final String requiredFieldName, final String message, final Exception cause) {
        super(message, cause);
        this.requiredFieldName = requiredFieldName;
    }

    /**
     * Constructor with the missing required field name and provides a default message containing the field name.
     *
     * @param requiredFieldName The name of the missing required field.
     */
    public RequiredFieldNullException(final String requiredFieldName) {
        super(String.format("Required field \"%s\" is null.", requiredFieldName));
        this.requiredFieldName = requiredFieldName;
    }

    /**
     * Constructor with the missing required field name and the source exception. This constructor will supply
     * a default message containing the field name.
     *
     * @param requiredFieldName The name of the missing required field.
     * @param cause The initial cause of the exception.
     */
    public RequiredFieldNullException(final String requiredFieldName, final Exception cause) {
        super(String.format("Required field \"%s\" is null.", requiredFieldName), cause);
        this.requiredFieldName = requiredFieldName;
    }

    /**
     * Gets the name of the missing required field.
     *
     * @return A string of the missing required field name.
     */
    public String getRequiredFieldName() {
        return requiredFieldName;
    }
}
