package property.impl;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import property.Property;
import property.Type;
import validator.ValidationWrapper;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:29 PM
 */
public class StringProperty extends Property<String> {

    private int minLength = Integer.MAX_VALUE;
    private int maxLength = Integer.MIN_VALUE;
    private String pattern = "";

    public StringProperty(String keyName, JSONObject property){
        super(keyName, property);
        setType(Type.STRING);
        if (property.has("maxLength"))
            maxLength = property.getInt("maxLength");
        if (property.has("minLength"))
            minLength = property.getInt("minLength");
        if (property.has("pattern"))
            pattern = property.getString("pattern");

    }

    @Override
    public ValidationWrapper validate(Object value) {
        ValidationWrapper validationWrapper = new ValidationWrapper();
        if (!validateType(value))
            return validationWrapper
                .setPassed(false)
                .addMessage(keyName + " is not a string");
        return validateData((String) value);
    }

    @Override
    public boolean validateType(Object value) {
        return value == null || value instanceof String;
    }


    @Override
    protected ValidationWrapper validateData(String value) {

        ValidationWrapper baseValidationWrapper = super.baseValidate(value);
        if (baseValidationWrapper.failed()){
            return baseValidationWrapper;
        }
        ValidationWrapper validationWrapper = new ValidationWrapper();

        int length = value.length();
        if (minLength != Integer.MAX_VALUE){
            if (length < minLength)
                return validationWrapper.setPassed(false).addMessage(keyName +" is too short");
        }

        if (maxLength != Integer.MIN_VALUE){
            if (length > maxLength)
                return validationWrapper.setPassed(false).addMessage(keyName +" exceeds maximum length allowed " + maxLength);
        }
        if (StringUtils.isNotBlank(pattern)){
            Pattern patternInstance = Pattern.compile(pattern);
            Matcher matcher = patternInstance.matcher(value);
            if (!matcher.find())
                return validationWrapper.setPassed(false).addMessage(keyName + " is in invalid format");
        }

        return validationWrapper;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public StringProperty setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public StringProperty setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}
