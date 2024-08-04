package churilla.mark.toolrental.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * A Utility class for accessing resources like data files, etc.
 */
public class ResourceUtils {

    /**
     * Returns a file in the src/main/resources directory.
     * If the resource is not found, this method will throw back a {@link FileNotFoundException}.
     */
    public static File getResourceFile(final String resourceFilename) throws FileNotFoundException {
        URL resource = ResourceUtils.class.getClassLoader().getResource(resourceFilename);

        if (resource == null) {
            throw new FileNotFoundException("Could not find the resource named: " + resourceFilename);
        }

        return new File(resource.getFile());
    }
}
