package churilla.mark.toolrental.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * The LocalDateUtils class provides static utility methods for dealing with the {@link LocalDate} objects.
 * The functionality currently provides means to determine if a given {@link LocalDate} falls on one of the
 * recognized holidays.
 */
public class LocalDateUtils {

    /**
     * Determines if the given date falls on either the Labor Day holiday or the Independence Day holiday.
     *
     * @param date The date to compare against.
     *
     * @return True if it falls on a holiday, false otherwise.
     */
    public static boolean isHoliday(final LocalDate date) {
        return (isLaborDayHoliday(date) || isIndependenceDayHoliday(date));
    }

    /**
     * Determines if the date given falls on the Labor Day holiday. This falls on the first Monday of September.
     *
     * @param date The date to compare against.
     *
     * @return True if the date falls on the first Monday of September, false otherwise.
     */
    public static boolean isLaborDayHoliday(final LocalDate date) {
        // If the date is not in September, or if it is not a Monday,
        // then it cannot be Labor Day.
        if (date.getMonth() != Month.SEPTEMBER ||
                date.getDayOfWeek() != DayOfWeek.MONDAY) {
            return false;
        }

        // The date is on a Monday in September, so subtract 7 days (one week) to see if the date ends up in September,
        // or August. If it is September then this cannot be the first Monday and therefore is not Labor Day.
        return date.minusDays(7).getMonth() == Month.AUGUST;
    }

    /**
     * Determines if the date given falls on the Independence Day holiday. This holiday is on July 4th. If that date
     * falls on a weekend, then it is observed on the closest weekday (Friday if it falls on Saturday, Monday if it
     * falls on Sunday).
     *
     * @param date The date to compare against.
     *
     * @return True if the date falls on the observed holiday date, false otherwise.
     */
    public static boolean isIndependenceDayHoliday(final LocalDate date) {
        // If the July 4th holiday has already occurred (July 5th is the latest possible date), no need to continue.
        if (date.isAfter(LocalDate.of(date.getYear(), 7, 5))) {
            return false;
        }

        // Calculate which day the holiday will be observed on. Start by assuming it will be on the 4th.
        LocalDate observedHolidayDate = LocalDate.of(date.getYear(), 7, 4);

        if (observedHolidayDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                observedHolidayDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            // If it falls on Saturday, subtract a day and set it to Friday
            // If it falls on Sunday, add a day to set to Monday.
            switch(observedHolidayDate.getDayOfWeek()) {
                case SATURDAY -> observedHolidayDate = observedHolidayDate.minusDays(1);
                case SUNDAY ->  observedHolidayDate = observedHolidayDate.plusDays(1);
            }
        }

        // Check if the date passed in falls on the observed holiday date.
        return date.equals(observedHolidayDate);
    }
}
