package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.repository.ToolRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ToolRepositoryTests {

    private ToolRepository repo;

    @BeforeAll
    void setup() {
        repo = new ToolRepository();
    }

    @Test
    void givenValidToolCode_whenRetrievingToolByCode_thenCorrectToolIsReturned() {
        Optional<RentableTool> tool = repo.getRentableToolByCode("CHNS");

        assertTrue(tool.isPresent());
        assertEquals("Stihl", tool.get().getBrandName());
        assertEquals("Chainsaw", tool.get().getToolType().getName());
    }

    @Test
    void givenInvalidToolCode_whenRetrievingToolByCode_thenEmptyOptionalIsReturned() {
        Optional<RentableTool> tool = repo.getRentableToolByCode("ABCD");

        assertFalse(tool.isPresent());
    }
}
