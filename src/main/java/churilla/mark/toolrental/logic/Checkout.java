package churilla.mark.toolrental.logic;

import churilla.mark.toolrental.exception.DiscountPercentageRangeException;
import churilla.mark.toolrental.exception.InvalidRentalDurationException;
import churilla.mark.toolrental.exception.UnknownToolCodeException;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.model.RentalAgreement;
import churilla.mark.toolrental.model.ToolType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import static churilla.mark.toolrental.utility.LocalDateUtils.isHoliday;

/**
 * The Checkout class handles the process of renting tools and creating rental agreements.
 * It maintains a catalog of rentable tools and provides functionality to generate a {@link RentalAgreement}
 * based on the tool selected, the rental duration, and any applicable discounts.
 */
public class Checkout {
    // The toolsForRent map provides a way to store the rentable tools, retrievable by the tool code.
    private final Map<String, RentableTool> toolsForRent;

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
