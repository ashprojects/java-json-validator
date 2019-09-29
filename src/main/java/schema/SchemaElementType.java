package schema;

/**
 * Developed by Ashish Gupta on 9/8/2019 1:39 PM
 */
public enum SchemaElementType {
    PROPERTY("$property"),
    SCHEMA("$schema");

    private String key;

    SchemaElementType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
