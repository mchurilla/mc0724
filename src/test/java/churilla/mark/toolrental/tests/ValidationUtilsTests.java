package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.exception.RequiredFieldNullException;
import churilla.mark.toolrental.model.ToolType;
import churilla.mark.toolrental.utility.ValidationUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTests {
    @Test
    void givenNullValue_whenNonNullIsRequired_thenThrows_RequiredFieldNullException() {
      ToolType type = null;

      assertThrows(
              RequiredFieldNullException.class,
              () -> ValidationUtils.requireNonNull(type, "field")
      );
    }

    @Test
    void givenNonNullObject_whenNonNullIsRequired_thenSameObjectIsReturned() {
      ToolType fakeToolType = new ToolType("FAKE", BigDecimal.valueOf(1.99), true, false, true);

      ToolType testToolType = ValidationUtils.requireNonNull(fakeToolType, "FAKE");

      assertNotNull(testToolType);
      assertEquals(fakeToolType, testToolType);
    }
}
