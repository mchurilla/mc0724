package churilla.mark.toolrental.service;

import churilla.mark.toolrental.exception.UnknownToolCodeException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.utility.ResourceUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ToolService is the interface to the rentable tool data contained within the ToolDb.json file.
 * The ToolDb.json file is a stand-in for a document database such as MongoDB and is simulating a collection
 * of rentable tools.
 */
public class ToolService {

    // Internally used HashMap to store the rentable tool instances that are read in from the ToolDb.json file.
    private final Map<String, RentableTool> rentableToolMap = new HashMap<>();

    // JSON serializer / deserializer.
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor. Reads the values from the ToolDb.json file initializes the tool map using the tool codes as keys.
     *
     * @throws IOException In the event that the ToolsDb.json file cannot be read, or is not present, then an
     *                     IOException will be throw back to the caller. This ensures that the service is not
     *                     in a partially constructed state. The caller should handle the error appropriately.
     */
    public ToolService() throws IOException {
        // Attempt to read the values from ToolDb.json.
        // If the file is not available or cannot be read, an IOException will be thrown back to the caller.
        java.io.File resourceFile = ResourceUtils.getResourceFile("ToolDb.json");
        List<RentableTool> rentableToolList = mapper.readValue(resourceFile,
                                                                new TypeReference<List<RentableTool>>() {});

        // Cycle through the list of tools and add each on to the map, using the tool code as the key.
        rentableToolList.stream()
                .forEach(tool -> rentableToolMap.putIfAbsent(tool.getToolCode(), tool));
    }

    /**
     * Retrieves a {@link RentableTool} object corresponding to the specified tool code.
     *
     * @param toolCode The unique code of the tool to retrieve. If the code does not exist in the map,
     *                 a {@link UnknownToolCodeException} is thrown.
     * @return The {@link RentableTool} corresponding to the tool code provided.
     */
    public RentableTool getRentableTool(final String toolCode) {
        // Throw an UnknownToolCodeException if the code supplied is not in the HashMap.
        if (!rentableToolMap.containsKey(toolCode)) {
            throw new UnknownToolCodeException("An unknown tool code was entered. Please check the tool code and try again.");
        }

        return rentableToolMap.get(toolCode);
    }
}
