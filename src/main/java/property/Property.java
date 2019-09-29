package property;

import org.json.JSONArray;
import org.json.JSONObject;

import validator.ValidationWrapper;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:26 PM
 */
@SuppressWarnings("WeakerAccess")
public abstract class Property<T> {

    protected String keyName;
    protected Type type;
    protected String description;

    // Common Constraints
    protected boolean nullable = true;
    protected JSONArray in;

    protected Property(String keyName, JSONObject property) {
        this.keyName = keyName;
        if (property.has("nullable")) {
            nullable = property.getBoolean("nullable");
        }
        if (property.has("in")) {
            in = property.getJSONArray("in");
        }
    }

    /**
     * Validate provided value cross given schema
     */
    public abstract ValidationWrapper validate(Object value);

    /**
     * Validates data type of value cross schema
     */
    protected abstract boolean validateType(Object value);

    /**
     * Validates data cross schema
     */
    protected abstract ValidationWrapper validateData(T value);

    /**
     * Common validation checks
     */
    protected final ValidationWrapper baseValidate(T value) {
        ValidationWrapper wrapper = new ValidationWrapper();
        if (value == null && !nullable) {
            return wrapper.setPassed(false)
                .addMessage(keyName + " is null");
        }
        if (in != null) {
            wrapper.setPassed(false)
                .addMessage(keyName + " is invalid");
            in.forEach(val -> {
                if (val.equals(value)) {
                    wrapper.setPassed(true);
                }
            });
        }
        return wrapper;
    }

    public Property setType(Type type) {
        this.type = type;
        return this;
    }
}
