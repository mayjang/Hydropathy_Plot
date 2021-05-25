package persistence;

import model.PrimaryProteinAnalyzer;
import model.PrimaryProteinSequence;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.*;

// Represents a writer that writes JSON representation of sequence analyzer to file
//Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of protein analyzer to file
    public void write(PrimaryProteinAnalyzer proteinAnalyzer) {
        JSONObject json = proteinAnalyzer.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
