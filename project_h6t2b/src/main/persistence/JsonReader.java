package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.PrimaryProteinAnalyzer;
import model.PrimaryProteinSequence;
import org.json.*;

// Represents a reader that reads sequence analyzer from JSON data stored in file
// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ProteinSequence from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PrimaryProteinAnalyzer read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProteinSequence(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses protein sequence from JSON object and returns it
    private PrimaryProteinAnalyzer parseProteinSequence(JSONObject jsonObject) {
        PrimaryProteinAnalyzer analyzer = new PrimaryProteinAnalyzer();
        addSequenceList(analyzer, jsonObject);
        Double threshold = jsonObject.getDouble("threshold");
        int linearMinimum = jsonObject.getInt("linearMinimum");
        analyzer.setThreshold(threshold);
        analyzer.setMinimumLinearCount(linearMinimum);
        return analyzer;
    }

    // MODIFIES: primary protein sequence
    // EFFECTS: parses primary protein sequence list from JSON object and adds sequences to analyzer
    private void addSequenceList(PrimaryProteinAnalyzer analyzer, JSONObject jsonObject) {
        JSONArray sequenceJsonArray = jsonObject.getJSONArray("sequenceList");
        int l = sequenceJsonArray.length();
        for (int i = 0; i < l; i++) {
            String sequenceString = sequenceJsonArray.getString(i);
            PrimaryProteinSequence primarySequence = new PrimaryProteinSequence(sequenceString);
            analyzer.addSequenceToList(primarySequence);
        }
    }


}
