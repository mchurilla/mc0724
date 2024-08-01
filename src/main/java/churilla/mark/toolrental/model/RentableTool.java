package churilla.mark.toolrental.model;

/**
 * Represents a tool in the system that is available for rent. Each tool has a code to uniquely identify it,
 * a type that indicates what it is, and a brand name to specify the company and manufacturer of the tool.
 */
public class RentableTool {

    private String toolCode;

    private ToolType toolType;

    private String brandName;

    /**
     * Constructor.
     * @param toolCode The 4-character code used to uniquely identify the tool.
     * @param toolType {@link ToolType} to specify the type of tool. Used to determine the rental charge.
     * @param brandName The manufacturer / brand name.
     */
    public RentableTool(final String toolCode, final ToolType toolType, final String brandName) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brandName = brandName;
    }

    /**
     * Gets the unique tool code.
     *
     * @return A string representing the unique code.
     */
    public String getToolCode() { return toolCode; }

    /**
     * Gets the tool's type, which contains various other information.
     *
     * @return A {@link ToolType} enum value.
     */
    public ToolType getToolType() { return toolType; }

    /**
     * Gets the brand name of the tool.
     *
     * @return A string value of the tool's brand name.
     */
    public String getBrandName() { return brandName; }
}
