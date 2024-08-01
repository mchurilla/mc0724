package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.utility.LocalDateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateUtilsTests {

    @Test
    void test_FirstMondayInSept_isLaborDayHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 7)));
    }

    @Test
    void test_NotFirstMondayInSept_isLaborDayHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 14)));
    }

    @Test
    void test_NotMondayInSept_isLaborDayHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isLaborDayHoliday(LocalDate.of(2015, 9, 4)));
    }

    @Test
    void test_July4thOnSaturday_GivenJuly3rd_isIndependenceDayHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2015, 7, 3)));
    }

    @Test
    void test_July4thOnSunday_GivenJuly5th_isIndependenceDayHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2021, 7, 5)));
    }

    @Test
    void test_July4thOnWeekday_GivenJuly4th_isIndependenceDayHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2016, 7, 4)));
    }

    @Test
    void test_GivenNonJuly4thDate_isIndependenceDayHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isIndependenceDayHoliday(LocalDate.of(2015, 7, 9)));
    }

    @Test
    void test_GivenJuly4thHoliday_isHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isHoliday(LocalDate.of(2015, 7, 3)));
    }

    @Test
    void test_GivenLaborDayHoliday_isHoliday_ReturnsTrue() {
        assertTrue(LocalDateUtils.isHoliday(LocalDate.of(2015, 9, 7)));
    }

    @Test
    void test_GivenNoHoliday_isHoliday_ReturnsFalse() {
        assertFalse(LocalDateUtils.isHoliday(LocalDate.of(2015, 3, 15)));
    }
}
