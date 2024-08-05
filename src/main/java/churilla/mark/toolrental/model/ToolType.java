package churilla.mark.toolrental.model;

import churilla.mark.toolrental.exception.RequiredFieldNullException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An immutable class that represents the type of tool(s) that can be rented. It contains information pertaining
 * to the daily rate charged to rent it, and whether it is free during certain timeframes (i.e., weekday, weekend, holiday).
 */
public class ToolType {
    private final String name;
    private final BigDecimal dailyCharge;
    private final boolean hasWeekdayCharge;
    private final boolean hasWeekendCharge;
    private final boolean hasHolidayCharge;

    /**
     * Serializable / deserializable constructor.
     *
     * @param name The name of the tool.
     * @param dailyCharge The amount this tool costs for each day it is rented.
     * @param hasWeekdayCharge Whether to charge for this tool on a weekday.
     * @param hasWeekendCharge Whether to charge for this tool on a weekend.
     * @param hasHolidayCharge Whether to charge for this tool on an observed holiday.
     */
    @JsonCreator
    public ToolType(@JsonProperty("name") final String name,
                    @JsonProperty("dailyCharge") final BigDecimal dailyCharge,
                    @JsonProperty("hasWeekdayCharge") final boolean hasWeekdayCharge,
                    @JsonProperty("hasWeekendCharge") final boolean hasWeekendCharge,
                    @JsonProperty("hasHolidayCharge") final boolean hasHolidayCharge) {

        this.name = name;
        this.dailyCharge = dailyCharge;
        this.hasWeekdayCharge = hasWeekdayCharge;
        this.hasWeekendCharge = hasWeekendCharge;
        this.hasHolidayCharge = hasHolidayCharge;

        validateInput();
    }

    // Getters

    /**
     * Gets the name of the tool type (i.e., Chainsaw)
     *
     * @return A String representation of the tool type name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the amount that the tool is charged for each day it is rented.
     *
     * @return A {@link BigDecimal} representation of the amount to be charged per day.
     */
    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    /**
     * Specifies if this tool type has a daily charge applied during a weekday.
     *
     * @return True if it is charged on a weekday, false if it is free.
     */
    public boolean hasWeekdayCharge () {
        return hasWeekdayCharge;
    }

    /**
     * Specifies if this tool type has a daily charge applied during the weekend.
     *
     * @return True if it is charged on the weekend, false if it is free.
     */
    public boolean hasWeekendCharge() {
        return hasWeekendCharge;
    }

    /**
     * Specifies if this tool type has a daily charge applied during an observed holiday.
     *
     * @return True if it is charged on a holiday, false if it is free.
     */
    public boolean hasHolidayCharge() {
        return hasHolidayCharge;
    }

    /**
     * Validates that all input required by the ToolType class is provided. If any fields are
     * null, then a {@link RequiredFieldNullException} is thrown.
     */
    private void validateInput() {
        if (name == null) {
            throw new RequiredFieldNullException("name");
        }
        if (dailyCharge == null) {
            throw new RequiredFieldNullException("dailyCharge");
        }
    }

    //
    // Overrides
    //
    @Override
    public String toString() {
        return """
                Name: %s
                Daily charge: $%s
                Weekday charge: %s
                Weekend charge: %s
                Holiday charge: %s
                """
                .formatted(name, dailyCharge, hasWeekdayCharge, hasWeekendCharge, hasHolidayCharge);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof ToolType)) {
            return false;
        }

        ToolType other = (ToolType) obj;
        return name.equals(other.getName()) &&
                dailyCharge.equals(other.getDailyCharge()) &&
                hasWeekdayCharge == other.hasWeekdayCharge() &&
                hasWeekendCharge == other.hasWeekendCharge() &&
                hasHolidayCharge == other.hasHolidayCharge();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dailyCharge, hasWeekdayCharge, hasWeekendCharge, hasHolidayCharge);
    }
}
