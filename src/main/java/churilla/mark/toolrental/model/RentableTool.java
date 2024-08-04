package churilla.mark.toolrental.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An immutable class that represents a tool in the system that is available for rent. Each tool has a code to uniquely
 * identify it, a type that indicates what it is, and a brand name to specify the company and manufacturer of the tool.
 */
public class RentableTool {

    private final String toolCode;

    private final ToolType toolType;

    private final String brandName;

    /**
     * Serializable / deserializable constructor.
     *
     * @param toolCode The 4-character code used to uniquely identify the tool.
     * @param toolType {@link ToolType} to specify the type of tool. Used to determine the rental charge.
     * @param brandName The manufacturer / brand name.
     */
    public RentableTool(@JsonProperty("toolCode") final String toolCode,
                        @JsonProperty("toolType") final ToolType toolType,
                        @JsonProperty("brandName") final String brandName) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brandName = brandName;
    }

    /**
     * Gets the unique tool code.
     *
     * @return A string representing the unique code.
     */
    public String getToolCode() {
        return toolCode;
    }

    /**
     * Gets the tool's type.
     *
     * @return A {@link ToolType}.
     */
    public ToolType getToolType() {
        return toolType;
    }

    /**
     * Gets the brand name of the tool.
     *
     * @return A string value of the tool's brand name.
     */
    public String getBrandName() {
        return brandName;
    }
}
