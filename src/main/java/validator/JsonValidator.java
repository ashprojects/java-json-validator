package validator;

import org.json.JSONObject;

import java.util.List;

import property.Property;
import schema.SchemaSpec;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:57 PM
 */
public class JsonValidator {

    public static ValidationWrapper validate(SchemaSpec schemaSpec, JSONObject jsonObject) {
        List<String> keys = schemaSpec.getKeys();
        ValidationWrapper validationWrapper = new ValidationWrapper();
        for (String key : keys) {
            Object object = jsonObject.opt(key);
            if (object == JSONObject.NULL) {
                object = null;
            }
            Property property = schemaSpec.getProperty(key);
            ValidationWrapper propertyValidationWrapper = property.validate(object);
            if (propertyValidationWrapper.failed()) {
                validationWrapper.mergeIn(propertyValidationWrapper);
            }
        }
        return validationWrapper;
    }

}
