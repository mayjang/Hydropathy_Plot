package persistence;

import model.PrimaryProteinAnalyzer;
import model.PrimaryProteinSequence;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PrimaryProteinAnalyzer proteinAnalyzer = new PrimaryProteinAnalyzer();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyProteinList() {
        try {
            PrimaryProteinAnalyzer proteinAnalyzer = new PrimaryProteinAnalyzer();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyProteinList.json");
            writer.open();
            writer.write(proteinAnalyzer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyProteinList.json");
            proteinAnalyzer = reader.read();
            assertEquals(0, proteinAnalyzer.getSequenceList().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralProteinAnalyzer() {
        try {
            PrimaryProteinAnalyzer proteinAnalyzer = new PrimaryProteinAnalyzer();

            PrimaryProteinSequence primarySequence1 = new PrimaryProteinSequence("AMAMAMAMAMAMA");
            PrimaryProteinSequence primarySequence2 = new PrimaryProteinSequence("THSKDJFBDLFN");
            proteinAnalyzer.addSequenceToList(primarySequence1);
            proteinAnalyzer.addSequenceToList(primarySequence2);

            proteinAnalyzer.setMinimumLinearCount(20);
            proteinAnalyzer.setIndexOfList(0);
            proteinAnalyzer.setThreshold(1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProteinAnalyzer.json");
            writer.open();
            writer.write(proteinAnalyzer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralProteinAnalyzer.json");
            proteinAnalyzer = reader.read();

            ArrayList<PrimaryProteinSequence> sequenceList = proteinAnalyzer.getSequenceList();
            PrimaryProteinSequence readSequence1 = sequenceList.get(0);
            String readSequenceString1 = readSequence1.getAminoAcidSequence();
            assertEquals(primarySequence1.getAminoAcidSequence(), readSequenceString1);

            PrimaryProteinSequence readSequence2 = sequenceList.get(1);
            String readSequenceString2 = readSequence2.getAminoAcidSequence();
            assertEquals(primarySequence2.getAminoAcidSequence(), readSequenceString2);

            assertEquals(2, proteinAnalyzer.getSequenceList().size());
            assertEquals(20, proteinAnalyzer.getMinimumLinearCount());
            assertEquals(0, proteinAnalyzer.getIndexOfList());
            assertEquals(1, proteinAnalyzer.getThreshold());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
