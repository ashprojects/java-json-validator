package property;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:27 PM
 */
public enum Type {
    STRING("string"),
    DATE("date_string"),
    NUMBER("number"),
    OBJECT("object"),
    LIST("list");


    private String type;

    Type(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
