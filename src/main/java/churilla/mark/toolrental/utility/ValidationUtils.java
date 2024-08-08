package churilla.mark.toolrental.utility;

import churilla.mark.toolrental.exception.RequiredFieldNullException;

/**
 * A utility class providing common validation methods.
 * <p>
 * This class is designed to provide reusable validation logic for common validation tasks such as ensuring
 * that required fields are not null.
 * </p>
 */
public class ValidationUtils {

    /**
     * Validates that the provided object is not null.
     * <p>
     * This method checks if the specified object is null, and if so, it throws a {@link RequiredFieldNullException} with
     * the given field name. This is useful for validating required fields during object construction or method invocation.
     * </p>
     *
     * @param object The object to validate.
     * @param fieldName The name of the field being validated, used in the exception message if the object is null.
     * @param <T> The type of the object being validated.
     *
     * @return The validated object, if it is not null.
     * @throws RequiredFieldNullException if the object is null.
     */
    public static <T> T requireNonNull(T object, String fieldName) {
        if (object == null) {
            throw new RequiredFieldNullException(fieldName);
        }

        return object;
    }
}
