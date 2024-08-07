package churilla.mark.toolrental.service;

import churilla.mark.toolrental.exception.ToolDataInitializationException;
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
 * Service class that provides access to and manages rentable tool data.
 * <p>
 * The ToolService class acts as an gateway for interacting with the tool data stored in
 * the `ToolDb.json` file. This file serves as a stand-in for a document database, such as MongoDB,
 * and simulates a collection of rentable tools.
 * </p>
 */
public class ToolService {

    // Internally used HashMap to store the rentable tool instances that are read in from the ToolDb.json file.
    private final Map<String, RentableTool> rentableToolMap = new HashMap<>();

    /**
     * Constructor. Reads the values from the ToolDb.json file initializes the tool map using the tool codes as keys.
     *
     * @throws IOException In the event that the ToolsDb.json file cannot be read, or is not present, then an
     *                     IOException will be throw back to the caller. This ensures that the service is not
     *                     in a partially constructed state. The caller should handle the error appropriately.
     */
    public ToolService() {
        // JSON serializer / deserializer.
        ObjectMapper mapper = new ObjectMapper();

        try {
            java.io.File resourceFile = ResourceUtils.getResourceFile("ToolDb.json");
            List<RentableTool> rentableToolList = mapper.readValue(resourceFile, new TypeReference<>() {});

            // Cycle through the list of tools and add each on to the map, using the tool code as the key.
            rentableToolList.forEach(tool -> rentableToolMap.putIfAbsent(tool.getToolCode(), tool));
        } catch (IOException ex) {
            throw new ToolDataInitializationException("", ex);
        }
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
            throw new UnknownToolCodeException(String.format("An unknown tool code \"%s\". Please check the tool code and try again.", toolCode));
        }

        return rentableToolMap.get(toolCode);
    }
}
