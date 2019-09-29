package schema;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import property.Property;
import property.PropertyFactory;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:45 PM
 */
public final class SchemaSpec {

    private List<String> keys;
    private Map<String, Property> propertyMap;


    public void parseSchema(String schema) throws ParseException {
        JSONObject schemaJson = new JSONObject(schema).optJSONObject(SchemaElementType.SCHEMA.getKey());
        keys = new LinkedList<>(schemaJson.keySet());
        propertyMap = new HashMap<>();
        for (String key: keys){
            JSONObject propertySchema = schemaJson.getJSONObject(key).getJSONObject(SchemaElementType.PROPERTY.getKey());
            propertyMap.put(key, PropertyFactory.get(key, propertySchema));
        }
    }

    public Property getProperty(String key){
        return propertyMap.get(key);
    }

    public List<String> getKeys() {
        return keys;
    }
}
