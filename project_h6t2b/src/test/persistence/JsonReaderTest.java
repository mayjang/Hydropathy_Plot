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

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PrimaryProteinAnalyzer primaryProteinAnalyzer = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyProteinList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProteinList.json");
        try {
            PrimaryProteinAnalyzer proteinAnalyzer = reader.read();
            assertEquals(0, proteinAnalyzer.getSequenceList().size());
            assertEquals(0, proteinAnalyzer.getIndexOfList());
            assertEquals(0, proteinAnalyzer.getThreshold());
            assertEquals(0, proteinAnalyzer.getIndexOfList());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralProteinAnalyzer() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProteinAnalyzer.json");
        try {
            PrimaryProteinAnalyzer proteinAnalyzer = reader.read();
            assertEquals(2, proteinAnalyzer.getSequenceList().size());
            assertEquals("MAMAMAMAMA", proteinAnalyzer.getSequenceList().get(0).getAminoAcidSequence());
//            assertEquals("ACCIMHEI", proteinAnalyzer.getSequenceList().get(1).getAminoAcidSequence());

            assertEquals(1, proteinAnalyzer.getThreshold());

            proteinAnalyzer.setIndexOfList(1);
            assertEquals(1, proteinAnalyzer.getIndexOfList());

            assertEquals(20, proteinAnalyzer.getMinimumLinearCount());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
