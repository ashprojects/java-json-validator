package property.impl;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import property.Type;
import validator.ValidationWrapper;

/**
 * Developed by Ashish Gupta on 9/8/2019 1:04 PM
 */
public class DateProperty extends StringProperty {

    private String format = "";

    private int minYears = Integer.MAX_VALUE;
    private int maxYears = Integer.MIN_VALUE;

    public DateProperty(String keyName, JSONObject property) {
        super(keyName, property);
        setType(Type.DATE);
        if (property.has("format")) {
            format = property.getString("format");
        }
        if (property.has("minYears")) {
            minYears = property.getInt("minYears");
        }
        if (property.has("maxYears")) {
            maxYears = property.getInt("maxYears");
        }
    }

    @Override
    public boolean validateType(Object value) {
        return super.validateType(value);
    }

    @Override
    public ValidationWrapper validateData(String value) {
        ValidationWrapper baseValidationWrapper = baseValidate(value);
        if (baseValidationWrapper.failed()) {
            return baseValidationWrapper;
        }
        ValidationWrapper validationWrapper = new ValidationWrapper();

        LocalDate dateOfBirth = null;
        if (StringUtils.isNotBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                dateOfBirth = LocalDate.parse(value, formatter);
            } catch (Exception e) {
                return validationWrapper.setPassed(false)
                    .addMessage(keyName + " format is incorrect");
            }
        }

        LocalDate currentDate = LocalDate.now();
        int diff = Period.between(dateOfBirth, currentDate)
            .getYears();

        if (maxYears != Integer.MIN_VALUE) {
            if (diff > maxYears) {
                return validationWrapper.setPassed(false)
                    .addMessage(keyName + " age is > " + maxYears);
            }
        }

        if (minYears != Integer.MAX_VALUE) {
            if (diff < minYears) {
                return validationWrapper.setPassed(false)
                    .addMessage(keyName + " age is < " + minYears);
            }
        }
        return validationWrapper;
    }
}
