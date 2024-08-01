package churilla.mark.toolrental.logic;

import churilla.mark.toolrental.exception.DiscountPercentageRangeException;
import churilla.mark.toolrental.exception.InvalidRentalDurationException;
import churilla.mark.toolrental.exception.UnknownToolCodeException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.model.RentalAgreement;
import churilla.mark.toolrental.model.ToolType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

/**
 * The Checkout class handles the process of renting tools and creating rental agreements.
 * It maintains a catalog of rentable tools and provides functionality to generate a {@link RentalAgreement}
 * based on the tool selected, the rental duration, and any applicable discounts.
 */
public class Checkout {
    // The toolsForRent map provides a way to store the rentable tools, retrievable by the tool code.
    private Map<String, RentableTool> toolsForRent;

    /**
     * Constructor.
     */
    public Checkout() {
        // Initialize a map of tools to be rented.
        this.toolsForRent = Map.ofEntries(
                Map.entry("CHNS", new RentableTool("CHNS", ToolType.CHAINSAW, "Stihl")),
                Map.entry("LADW", new RentableTool("LADW", ToolType.LADDER, "Werner")),
                Map.entry("JAKD", new RentableTool("JAKD", ToolType.JACKHAMMER, "DeWalt")),
                Map.entry("JAKR", new RentableTool("JAKR", ToolType.JACKHAMMER, "Ridgid"))
        );
    }

    /**
     * Checks out a tool for rent and creates the rental agreement.
     *
     * @param toolCode Determines which tool is being rented.
     * @param checkoutDate The date when the rental begins
     * @param rentalDuration How many days the customer will have the tool.
     * @param discount The percentage discount applied to the rental.
     *
     * @return A {@link RentalAgreement} with details about the rental.
     */
    public RentalAgreement checkout(final String toolCode,
                                    final LocalDate checkoutDate,
                                    final int rentalDuration,
                                    final int discount) {
        // Ensure that the rental duration and discount percentage values are valid.
        if (rentalDuration < 1) {
            throw new InvalidRentalDurationException("The duration of the rental is invalid. Please re-enter a value of 1 or greater.");
        }

        if (discount < 0 || discount > 100) {
            throw new DiscountPercentageRangeException("The discount that was entered is invalid. Please re-enter a value from 0 to 100.");
        }

        // Handle the case where an invalid product/tool code is entered.
        if (!toolsForRent.containsKey(toolCode)) {
            throw new UnknownToolCodeException("An unknown tool code was entered. Please check the tool code and try again.");
        }

        // Get the tool from the map that is being rented.
        RentableTool tool = toolsForRent.get(toolCode);

        // Determine how many days of the rental duration will be charged (some tools are not charged on certain days).
        int chargedDays = rentalDuration;
        for (int i=1; i<=rentalDuration; i++) {
            if (isToolFreeForDate(tool.getToolType(), checkoutDate.plusDays(i))) {
                chargedDays--;
            }
        }

        return new RentalAgreement(tool, rentalDuration, checkoutDate,chargedDays, discount);
    }

    /**
     * Determines if the given date falls on either the Labor Day holiday or the Independence Day holiday.
     *
     * @param date The date to compare against.
     *
     * @return True if it falls on a holiday, false otherwise.
     */
    private boolean isHoliday(final LocalDate date) {
        return (isLaborDayHoliday(date) || isIndependenceDayHoliday(date));
    }

    /**
     * Determines if the date given falls on the Labor Day holiday. This falls on the first Monday of September.
     *
     * @param date The date to compare against.
     *
     * @return True if the date falls on the first Monday of September, false otherwise.
     */
    private boolean isLaborDayHoliday(final LocalDate date) {
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
    private boolean isIndependenceDayHoliday(final LocalDate date) {
        // If the July 4th holiday has already occurred, no need to continue. Return false.
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

    /**
     * Checks if the tool type should be free of charge for the given day.
     *
     * @param toolType The {@link ToolType} of the tool. This is used to determine which scenarios, if any,
     *                 the tool is free or charge.
     * @param date  The date to check.
     *
     * @return True if the tool should be free of charge on the given date, false if the customer should be charged.
     */
    private boolean isToolFreeForDate(final ToolType toolType, final LocalDate date) {
        // A tool is potentially free in the following scenarios:
        // Weekdays (Mon - Fri), Weekends (Sat - Sun), specific holidays.

        // First check if the tool is free for a holiday.
        if (isHoliday(date)) {
            return !toolType.hasHolidayCharge();
        }

        // Next check if the tool is free on the weekends or weekdays.
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            // Check if the tool is free (not charged) on weekends.
            return !toolType.hasWeekendChange();
        }
        else {
            // Check if the tool is free on the weekdays.
            return !toolType.hasWeekdayCharge();
        }
    }
}
