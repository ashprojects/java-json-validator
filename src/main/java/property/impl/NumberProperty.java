package property.impl;

import org.json.JSONObject;

import property.Property;
import property.Type;
import validator.ValidationWrapper;

/**
 * Developed by Ashish Gupta on 9/8/2019 2:21 PM
 */
public class NumberProperty extends Property<Number> {

    private double min = Integer.MAX_VALUE;
    private double max = Integer.MIN_VALUE;

    public NumberProperty(String keyName, JSONObject property) {
        super(keyName, property);
        setType(Type.NUMBER);

        if (property.has("min")){
            min = getAsDouble("min", property);
        }
        if (property.has("max")){
            max = getAsDouble("max", property);
        }
    }

    @Override
    public ValidationWrapper validate(Object value) {
        if (!validateType(value)){
            return new ValidationWrapper().setPassed(false)
                .addMessage(keyName + " is not a number");
        }
        return validateData((Number) value);
    }

    @Override
    public boolean validateType(Object value) {
        return value == null || value instanceof Number;
    }

    @Override
    public ValidationWrapper validateData(Number value) {
        ValidationWrapper baseValidationWrapper = baseValidate(value);
        if (baseValidationWrapper.failed()){
            return baseValidationWrapper;
        }
        ValidationWrapper validationWrapper = baseValidate(value);

        if (value != null){
            double doubleValue = objectToDouble(value);

            if (min != Integer.MAX_VALUE){
                if (doubleValue < min){
                    return validationWrapper.setPassed(false).addMessage(keyName + " is less than " + min);
                }
            }

            if (max != Integer.MIN_VALUE){
                if (doubleValue > max){
                    return validationWrapper.setPassed(false).addMessage(keyName + " is greater than " + max);
                }
            }
        }

        return validationWrapper;
    }

    protected double getAsDouble(String key, JSONObject object){
        Object value = object.get(key);
        return objectToDouble(value);
    }

    protected double objectToDouble(Object obj){
        if (obj instanceof Integer || obj instanceof Long){
            Long longValue = Long.valueOf(obj.toString());
            return longValue.doubleValue();
        } else {
            // Float or double
            return (Double) obj;
        }
    }
}
