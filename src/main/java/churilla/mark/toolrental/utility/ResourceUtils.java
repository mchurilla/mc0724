package churilla.mark.toolrental.utility;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility class for accessing resources such as data files from the classpath.
 */
public class ResourceUtils {

    /**
     * Retrieves an {@link InputStream} from the classpath, typically from the `src/main/resources` directory.
     * <p>
     * If the resource is not found, this method will throw back a {@link FileNotFoundException}.
     * </p>
     *
     * @param resourceFilename the name of the resource file to retrieve, relative to the classpath.
     * @return a {@link InputStream} object used to read the contents of the resource.
     */
    public static InputStream getResource(final String resourceFilename) throws FileNotFoundException {
        InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(resourceFilename);

        if (inputStream == null) {
            throw new FileNotFoundException("Could not find the resource named: " + resourceFilename);
        }

        return inputStream;
    }
}
