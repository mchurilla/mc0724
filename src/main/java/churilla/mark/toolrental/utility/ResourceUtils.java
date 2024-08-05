package churilla.mark.toolrental.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Utility class for accessing resources such as data files from the classpath.
 */
public class ResourceUtils {

    /**
     * Retrieves a file from the classpath, typically from the `src/main/resources` directory.
     *
     * If the resource is not found, this method will throw back a {@link FileNotFoundException}.
     *
     * @param resourceFilename the name of the resource file to retrieve, relative to the classpath.
     * @return a {@link File} object representing the resource file.
     */
    public static File getResourceFile(final String resourceFilename) throws FileNotFoundException {
        URL resource = ResourceUtils.class.getClassLoader().getResource(resourceFilename);

        if (resource == null) {
            throw new FileNotFoundException("Could not find the resource named: " + resourceFilename);
        }

        return new File(resource.getFile());
    }
}
