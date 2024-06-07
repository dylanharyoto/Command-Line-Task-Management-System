package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * The Criterion class represents a criterion in the Task Management System (TMS).
 * Each criterion has a name, property, operation, and value.
 * The name, property, operation, and value of a criterion cannot be null or empty.
 */
public class Criterion {
    private String name;
    private String property;
    private String op;
    private String value;
    private boolean isPrimitive;
    private boolean isBasic;
    private boolean isNegated;
    private String[] subCriterion = new String[3];

    /**
     * Constructs a new Criterion with the specified name.
     * If the name is "IsPrimitive", it sets up the criterion to be a basic one with no property and value.
     *
     * @param name The name of the criterion.
     */
    public Criterion(String name) {
        if (name.equals("IsPrimitive")) {
            this.name = name;
            this.op = name;
            this.setIsBasic(true);
            this.setIsNegated(false);
            this.setIsPrimitive(true);
        }
        if (name.equals("IsCompos")) {
            this.name = name;
            this.op = "IsComposite";
            this.setIsBasic(true);
            this.setIsNegated(true);
            this.setIsPrimitive(false);
        }
    }
    /**
     * Returns the 'isPrimitive' status of this object.
     *
     * @return A boolean indicating whether the object is of a primitive type.
     */
    public boolean getIsPrimitive() {
        return isPrimitive;
    }

    /**
     * Sets the 'isPrimitive' status for this object.
     *
     * @param isPrimitive A boolean value indicating whether the object should be marked as a primitive type.
     */
    public void setIsPrimitive(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    /**
     * Constructs a new Criterion with the specified name, property, operation, and value.
     *
     * @param name1    The name of the criterion.
     * @param property The property for the criterion.
     * @param op       The operation for the criterion.
     * @param value    The value for the criterion.
     */
    public Criterion(String name1, String property, String op, String value) {
        this.name = name1;
        this.property = property;
        this.op = op;
        this.value = value;
    }

    /**
     * Returns the name of this criterion.
     *
     * @return The name of this criterion.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the property of this criterion.
     *
     * @return The property of this criterion.
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * Returns the operation of this criterion.
     *
     * @return The operation of this criterion.
     */
    public String getOp() {
        return this.op;
    }

    /**
     * Returns the value of this criterion.
     *
     * @return The value of this criterion.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns the 'isBasic' status of this object.
     *
     * @return A boolean indicating whether the object is of a basic type.
     */
    public boolean getIsBasic() {
        return isBasic;
    }

    /**
     * Returns the 'isNegated' status of this object.
     *
     * @return A boolean indicating whether the object is in a negated state.
     */
    public boolean getIsNegated() {
        return isNegated;
    }

    /**
     * Returns the sub-criterion of this object.
     *
     * @return An array of Strings representing the sub-criterion.
     */
    public String[] getSubCriterion() {
        return subCriterion;
    }

    /**
     * Sets the 'isBasic' status of this object.
     *
     * @param isBasic A boolean value indicating whether the object should be marked as a basic type.
     */
    public void setIsBasic(boolean isBasic) {
        this.isBasic = isBasic;
    }

    /**
     * Sets the 'isNegated' status of this object.
     *
     * @param isNegated A boolean value indicating whether the object should be marked as negated.
     */
    public void setIsNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    /**
     * Sets the sub-criterion of this object.
     *
     * @param subCriterion An array of Strings representing the new sub-criterion.
     */
    public void setSubCriterion(String[] subCriterion) {
        this.subCriterion = subCriterion;
    }
}
