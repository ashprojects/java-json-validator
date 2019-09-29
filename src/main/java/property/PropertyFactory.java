package property;

import org.json.JSONObject;

import java.text.ParseException;

import property.impl.DateProperty;
import property.impl.JsonProperty;
import property.impl.NumberProperty;
import property.impl.StringProperty;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:47 PM
 */
public class PropertyFactory {

    public static Property get(String keyName, JSONObject property) {
        String type = property.getString("type");
        if (type.equalsIgnoreCase(Type.STRING.getType())) {
            return new StringProperty(keyName, property);
        }

        if (type.equalsIgnoreCase(Type.DATE.getType())) {
            return new DateProperty(keyName, property);
        }

        if (type.equalsIgnoreCase(Type.NUMBER.getType())){
            return new NumberProperty(keyName, property);
        }

        if (type.equalsIgnoreCase(Type.OBJECT.getType())){
            try {
                return new JsonProperty(keyName, property);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
