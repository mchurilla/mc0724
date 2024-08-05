package churilla.mark.toolrental.logic;

import churilla.mark.toolrental.exception.FatalException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.model.RentalAgreement;
import churilla.mark.toolrental.model.ToolType;
import churilla.mark.toolrental.service.ToolService;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static churilla.mark.toolrental.utility.LocalDateUtils.isHoliday;

/**
 * The Checkout class handles the process of renting tools and creating rental agreements.
 * It provides functionality to generate a {@link RentalAgreement} based on the tool selected, the rental duration,
 * rental period, and any applicable discounts.
 */
public class Checkout {
    private final ToolService toolService;

    /**
     * Constructor.
     */
    public Checkout() throws FatalException {
        try {
            toolService = new ToolService();
        } catch (IOException ex) {
            throw new FatalException("An error occurred while reading the ToolsDb.json file.", ex);
        }
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
        // Get the tool from the map that is being rented.
        RentableTool tool = toolService.getRentableTool(toolCode);

        // Determine how many days of the rental duration will be charged (some tools are not charged on certain days).
        int chargedDays = rentalDuration;
        for (int i=1; i<=rentalDuration; i++) {
            if (isToolFreeForDate(tool.getToolType(), checkoutDate.plusDays(i))) {
                chargedDays--;
            }
        }

        return new RentalAgreement(tool, rentalDuration, checkoutDate, chargedDays, discount);
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

        // First check if the tool is free for a holiday. If the date is a holiday, and the tool is free
        // for holidays, then return true because checking for weekday / weekend is not necessary.
        if (isHoliday(date) &&
                !toolType.hasHolidayCharge()) {
            return true;
        }

        // Next check if the tool is free on the weekends or weekdays.
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            // Check if the tool is free on weekends.
            return !toolType.hasWeekendCharge();
        }
        else {
            // Check if the tool is free on the weekdays.
            return !toolType.hasWeekdayCharge();
        }
    }
}
