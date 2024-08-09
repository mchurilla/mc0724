package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.utility.LocalDateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateUtilsTests {

    @Test
    void givenFirstMondayInSept_whenCheckingForLaborDayHoliday_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 7)));
    }

    @Test
    void givenSecondMondayInSept_whenCheckingForLaborDayHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 14)));
    }

    @Test
    void givenNonMondayInSept_whenCheckingForLaborDayHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 4)));
    }

    @Test
    void givenJuly3rd_whenJuly4thIsOnSaturday_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2015, 7, 3)));
    }

    @Test
    void givenJuly5th_whenJuly4thIsOnSunday_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2021, 7, 5)));
    }

    @Test
    void givenJuly4th_whenJuly4thIsOnAWeekday_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2016, 7, 4)));
    }

    @Test
    void givenNonJuly4thDate_thenReturnsFalse() {
        assertFalse(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2015, 7, 9)));
    }

    @Test
    void givenJuly4thHoliday_whenCheckingForAllHolidays_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isHoliday(LocalDate.of(2015, 7, 3)));
    }

    @Test
    void givenLaborDayHoliday_whenCheckingForAllHolidays_thenReturnsTrue() {
        assertTrue(LocalDateUtils.isHoliday(LocalDate.of(2015, 9, 7)));
    }

    @Test
    void givenNonHolidayDate_whenCheckingForAllHolidays_thenReturnsFalse() {
        assertFalse(LocalDateUtils.isHoliday(LocalDate.of(2015, 3, 15)));
    }
}
