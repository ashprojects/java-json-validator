import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import schema.SchemaSpec;
import validator.JsonValidator;

/**
 * Developed by Ashish Gupta on 9/8/2019 12:54 PM
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String schema = new String(Files.readAllBytes(Paths.get("application_schema.json")));
        String sample = new String(Files.readAllBytes(Paths.get("application_json.json")));

        JSONObject sampleJson = new JSONObject(sample);

        SchemaSpec schemaSpec = new SchemaSpec();
        schemaSpec.parseSchema(schema);
        System.out.println(JsonValidator.validate(schemaSpec, sampleJson));
    }
}
