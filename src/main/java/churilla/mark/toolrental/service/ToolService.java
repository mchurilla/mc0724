package churilla.mark.toolrental.service;

import churilla.mark.toolrental.exception.UnknownToolCodeException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.repository.ToolRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private final ToolRepository toolRepo;

    /**
     * Constructor. Reads the values from the ToolDb.json file initializes the tool map using the tool codes as keys.
     *
     * @throws IOException In the event that the ToolsDb.json file cannot be read, or is not present, then an
     *                     IOException will be throw back to the caller. This ensures that the service is not
     *                     in a partially constructed state. The caller should handle the error appropriately.
     */
    public ToolService() {
        this.toolRepo = new ToolRepository();
    }

    /**
     * Retrieves a {@link RentableTool} object corresponding to the specified tool code.
     *
     * @param toolCode The unique code of the tool to retrieve. If the code does not exist in the map,
     *                 a {@link UnknownToolCodeException} is thrown.
     * @return The {@link RentableTool} corresponding to the tool code provided.
     */
    public Optional<RentableTool> getRentableTool(final String toolCode) {
        return toolRepo.getRentableToolByCode(toolCode);
    }
}
