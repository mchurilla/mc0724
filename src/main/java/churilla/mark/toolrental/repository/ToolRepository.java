package churilla.mark.toolrental.repository;

import churilla.mark.toolrental.exception.ToolDataInitializationException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.utility.ResourceUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository class that handles reading the ToolDb.json file and internally storing the values for lookup.
 */
public class ToolRepository {

    // Internally used HashMap to store the rentable tool instances that are read in from the ToolDb.json file.
    private final Map<String, RentableTool> rentableToolMap = new ConcurrentHashMap<>();

    /**
     * Constructor that attempts to read the values from the ToolDb.json list and place them into
     * the internal storage map.
     * <p>
 *     If any errors occur while trying to populate the map (e.g., the stream cannot be created) Then
     * a {@link ToolDataInitializationException} will be thrown back to the caller.
     * </p>
     */
    public ToolRepository() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream resourceStream = ResourceUtils.getResource("ToolDb.json")) {
            List<RentableTool> rentableToolList = mapper.readValue(resourceStream, new TypeReference<>() {});

            // Cycle through the list of tools and add each on to the map, using the tool code as the key.
            rentableToolList.forEach(tool -> rentableToolMap.putIfAbsent(tool.getToolCode(), tool));
        } catch (IOException ex) {
            throw new ToolDataInitializationException("Failed to initialize tool data from ToolDb.json.", ex);
        }
    }

    /**
     * Returns a tool based on the provide tool code.
     * <p>
     *  This method looks up the tool associated with the provided tool code. If the tool code is null, or if no tool
     *  exists with the specified code, then an empty Optional is returned.
     * </p>
     * @param toolCode The unique identifier for the tool to retrieve.
     *
     * @return An Optional of the {@link RentableTool} associated with the code, or empty otherwise.
     */
    public Optional<RentableTool> getRentableToolByCode(final String toolCode) {
        if (toolCode == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(rentableToolMap.get(toolCode));
    }
}
