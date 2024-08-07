package churilla.mark.toolrental.repository;

import churilla.mark.toolrental.exception.ToolDataInitializationException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.utility.ResourceUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ToolRepository {

    // Internally used HashMap to store the rentable tool instances that are read in from the ToolDb.json file.
    private final Map<String, RentableTool> rentableToolMap = new HashMap<>();

    public ToolRepository() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            java.io.File resourceFile = ResourceUtils.getResourceFile("ToolDb.json");
            List<RentableTool> rentableToolList = mapper.readValue(resourceFile, new TypeReference<>() {});

            // Cycle through the list of tools and add each on to the map, using the tool code as the key.
            rentableToolList.forEach(tool -> rentableToolMap.putIfAbsent(tool.getToolCode(), tool));
        } catch (IOException ex) {
            throw new ToolDataInitializationException("ToolDb.json file not found.", ex);
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
