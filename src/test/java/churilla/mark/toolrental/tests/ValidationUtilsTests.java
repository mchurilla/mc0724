package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.exception.RequiredFieldNullException;
import churilla.mark.toolrental.model.ToolType;
import churilla.mark.toolrental.utility.ValidationUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTests {
    @Test
    void test_requireNonNull_GivenNull_ThrowsException() {
      ToolType type = null;

      assertThrows(
              RequiredFieldNullException.class,
              () -> ValidationUtils.requireNonNull(type, "field")
      );
    }

    @Test
    void test_requireNonNull_GivenNonNull_ExpectObjectReturned() {
      ToolType fakeToolType = new ToolType("FAKE", BigDecimal.valueOf(1.99), true, false, true);

      ToolType testToolType = ValidationUtils.requireNonNull(fakeToolType, "FAKE");

      assertNotNull(testToolType);
      assertEquals(fakeToolType, testToolType);
    }
}
