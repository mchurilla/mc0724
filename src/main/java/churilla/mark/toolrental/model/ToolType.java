package churilla.mark.toolrental.model;

import java.math.BigDecimal;

/**
 * An enum that represents the type of tool(s) that can be rented. It also contains information pertaining to
 * the daily rate charged to rent it, and whether it is free during certain timeframes (i.e., weekday, weekend, holiday).
 */
public enum ToolType {

    CHAINSAW("Chainsaw", new BigDecimal("1.49"), true, false, true),
    JACKHAMMER("Jackhammer", new BigDecimal("2.99"), true, false, false),
    LADDER("Ladder", new BigDecimal("1.99"), true, true, false);

    // Represents the name of the tool type.
    private String name;

    // The price for each day the tool is rented.
    private BigDecimal dailyCharge;

    // If the tool has a charge during the weekday (true if it does).
    private boolean hasWeekdayCharge;

    // If the tool has a charge on the weekend (true if it does).
    private boolean hasWeekendChange;

    // If the tool has a charge during a holiday (true if it does).
    private boolean hasHolidayCharge;

     ToolType(final String name,
              final BigDecimal dailyCharge,
              final boolean hasWeekdayCharge,
              final boolean hasWeekendChange,
              final boolean hasHolidayCharge) {
         this.name = name;
         this.dailyCharge = dailyCharge;
         this.hasWeekdayCharge = hasWeekdayCharge;
         this.hasWeekendChange = hasWeekendChange;
         this.hasHolidayCharge = hasHolidayCharge;
     }

    /**
     * Used to get the string representation of the enumeration value.
     *
     * @return String representation of the enum value.
     */
    public String getName() { return name; }

    /**
     * Gets the daily charge for the tool type.
     *
     * @return The charge per day to rent the tool.
     */
    public BigDecimal getDailyCharge() { return dailyCharge; }

    /**
     * Whether this tool is free during the weekdays or has a charge.
     *
     * @return True if there is a charge, false if it is free.
     */
    public boolean hasWeekdayCharge() { return hasWeekdayCharge; }

    /**
     * Whether this tool is free during the weekend or has a charge.
     *
     * @return True if there is a charge, false if it is free.
     */
    public boolean hasWeekendChange() { return hasWeekendChange; }

    /**
     * Whether this tool is free during a  holiday or has a charge.
     *
     * @return True if there is a charge, false if it is free.
     */
    public boolean hasHolidayCharge() { return hasHolidayCharge; }
}
