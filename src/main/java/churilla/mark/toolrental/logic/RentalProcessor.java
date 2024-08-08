package churilla.mark.toolrental.logic;

import churilla.mark.toolrental.exception.FatalException;
import churilla.mark.toolrental.exception.ToolDataInitializationException;
import churilla.mark.toolrental.exception.UnknownToolCodeException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.model.RentalAgreement;
import churilla.mark.toolrental.model.ToolType;
import churilla.mark.toolrental.service.ToolService;
import churilla.mark.toolrental.utility.ValidationUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static churilla.mark.toolrental.utility.LocalDateUtils.isHoliday;

/**
 * The RentalProcessor class handles the process of renting tools and creating rental agreements.
 * It provides functionality to generate a {@link RentalAgreement} based on the tool selected, the rental duration,
 * rental period, and any applicable discounts.
 */
public class RentalProcessor {
    private final ToolService toolService;

    /**
     * Constructor. Creates a {@link ToolService} object, which reads in the tool data. If the service class cannot
     * read in the tool data for any reason it will throw a {@link ToolDataInitializationException}. If this occurs, the
     * program will not be able to function properly so a {@link FatalException} is thrown to the caller.
     *
     * @throws FatalException if the tool service cannot be instantiated.
     */
    public RentalProcessor() throws FatalException {
        try {
            toolService = new ToolService();
        } catch (ToolDataInitializationException ex) {
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
        ValidationUtils.requireNonNull(toolCode, "toolCode");
        ValidationUtils.requireNonNull(checkoutDate, "checkoutDate");

        RentableTool tool = toolService.getRentableTool(toolCode)
                .orElseThrow(() -> new UnknownToolCodeException(String.format("Tool code \"%s\" not found.", toolCode)));

        int chargeableDays = calculateChargeableDays(tool, checkoutDate, rentalDuration);

        return new RentalAgreement(tool, rentalDuration, checkoutDate, chargeableDays, discount);
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

    /**
     * Calculates the number of days during the rental period that are chargeable.
     *
     * @param tool The tool being rented.
     * @param checkoutDate The date that the tool is being checked out. The first day of the rental is the day following
     *                     the checkout date.
     * @param rentalDuration The number of days that the tool is being rented.
     *
     * @return A number in days that the tool will be charged for, taking into account any days that tool is free.
     */
    private int calculateChargeableDays(final RentableTool tool, final LocalDate checkoutDate, final int rentalDuration) {
        // Determine how many days of the rental duration will be charged (some tools are not charged on certain days).
        // Rental charges start on the day after the checkout date, so skip the checkout date when calculating.
        int chargeableDays = rentalDuration;
        for (int i=1; i<=rentalDuration; i++) {
            if (isToolFreeForDate(tool.getToolType(), checkoutDate.plusDays(i))) {
                chargeableDays--;
            }
        }

        return chargeableDays;
    }
}
