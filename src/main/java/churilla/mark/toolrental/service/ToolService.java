package churilla.mark.toolrental.service;

import churilla.mark.toolrental.exception.ToolDataInitializationException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.repository.ToolRepository;

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
    private final ToolRepository toolRepo;

    /**
     * Constructor. Reads the values from the ToolDb.json file initializes the tool map using the tool codes as keys.
     * <p>
     * In the event that the {@link ToolRepository} cannot be instantiated, then the constructor will bubble
     * up a {@link ToolDataInitializationException} to the caller.
     * </p>
     */
    public ToolService() {
        this.toolRepo = new ToolRepository();
    }

    /**
     * Retrieves a {@link RentableTool} object corresponding to the specified tool code.
     * <p>
     * Returns an Optional containing the {@link RentableTool} if found. An empty Optional is returned
     * when no tool with the given tool code exists in the repository.
     * </p>
     *
     * @param toolCode The unique code of the tool to retrieve. If the tool code does not exist in the repository,
     *                 an empty Option is returned.
     * @return An Optional containing the {@link RentableTool} if found by the repository, or empty otherwise.
     */
    public Optional<RentableTool> getRentableTool(final String toolCode) {
        return toolRepo.getRentableToolByCode(toolCode);
    }
}
