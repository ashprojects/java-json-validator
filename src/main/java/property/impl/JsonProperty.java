package property.impl;

import org.json.JSONObject;

import java.text.ParseException;

import property.Property;
import property.Type;
import schema.SchemaSpec;
import validator.ValidationWrapper;
import validator.JsonValidator;

/**
 * Developed by Ashish Gupta on 9/8/2019 10:16 PM
 */
public class JsonProperty extends Property<JSONObject> {

    private SchemaSpec innerSchemaSpec;

    public JsonProperty(String keyName, JSONObject property) throws ParseException {
        super(keyName, property);
        this.innerSchemaSpec = new SchemaSpec();
        innerSchemaSpec.parseSchema(property.toString());
    }

    @Override
    public ValidationWrapper validate(Object value) {
        if (!validateType(value)){
            return new ValidationWrapper().setPassed(false)
                .addMessage(keyName + " is not a JsonObject");
        }
        return validateData((JSONObject) value);
    }

    @Override
    protected boolean validateType(Object value) {
        return value == null || value instanceof JSONObject;
    }

    @Override
    protected ValidationWrapper validateData(JSONObject value) {
        ValidationWrapper validationWrapper = baseValidate(value);
        if (validationWrapper.failed()){
            return validationWrapper;
        }
        if (value == null)
            return validationWrapper;
        return JsonValidator.validate(innerSchemaSpec, value);
    }

    @Override
    public Property setType(Type type) {
        return super.setType(type);
    }
}
