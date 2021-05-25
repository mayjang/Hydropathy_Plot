package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimaryProteinAnalyzerTest {

    private PrimaryProteinAnalyzer testAnalyzer;

    @BeforeEach
    void runBefore(){
        testAnalyzer = new PrimaryProteinAnalyzer();
        testAnalyzer.setMinimumLinearCount(18);
        testAnalyzer.createAminoAcidMap();
    }

    @Test
    void testTransmembraneDomainAnalysisOneSequence(){
        PrimaryProteinSequence sequence = new PrimaryProteinSequence("MVVFCMAFCM" + "LLAMCCFIVI" + "STTGVAMHTS"
        + "TSSSVTKSYI" + "SSQTNDTHKR" + "IVFCCMLMMA" + "AMCCFIVLFI" + "AIVLLFCMMA" + "VQLAHHFSEP" + "EITLIIFGVM"
        + "AGVIGTILLI" + "SYGIRRLIKK" + "SPSDVKPLSPS");
        testAnalyzer.addSequenceToList(sequence);
        testAnalyzer.setThreshold(1.6);
        testAnalyzer.setIndexOfList(0);
        int numberOfDomains = testAnalyzer.transmembraneDomainAnalysis();
        PrimaryProteinSequence firstSequence = testAnalyzer.getSpecificSequence(0);
        assertEquals(firstSequence, testAnalyzer.getSequenceList().get(0));
        assertEquals(1, testAnalyzer.getSequenceList().size());
        assertEquals(2, numberOfDomains);
    }

    @Test
    void testTransmembraneDomainAnalysisMultipleSequences(){

        PrimaryProteinSequence sequence1 = new PrimaryProteinSequence("QDNKRPYWKSLDSFNDSKFNDKFKNFSDF");
        PrimaryProteinSequence sequence2 = new PrimaryProteinSequence("IVLFCMAGTSWYPHQDNK");
        PrimaryProteinSequence sequence3 = new PrimaryProteinSequence("MVVFCMAFCM" + "LLAMCCFIVI" + "STTGVAMHTS"
                + "TSSSVTKSYI" + "SSQTNDTHKR" + "IVFCCMLMMA" + "AMCCFIVLFI" + "AIVLLFCMMA" + "VQLAHHFSEP" + "EITLIIFGVM"
                + "AGVIGTILLI" + "SYGIRRLIKK" + "SPSDVKPLSPS");
        testAnalyzer.addSequenceToList(sequence1);
        testAnalyzer.addSequenceToList(sequence2);
        testAnalyzer.addSequenceToList(sequence3);
        testAnalyzer.setThreshold(1.6);
        testAnalyzer.setIndexOfList(2);
        PrimaryProteinSequence firstSequence = testAnalyzer.getSpecificSequence(0);
        PrimaryProteinSequence secondSequence = testAnalyzer.getSpecificSequence(1);
        PrimaryProteinSequence thirdSequence = testAnalyzer.getSpecificSequence(2);
        assertEquals(firstSequence, testAnalyzer.getSequenceList().get(0));
        assertEquals(secondSequence, testAnalyzer.getSequenceList().get(1));
        assertEquals(thirdSequence, testAnalyzer.getSequenceList().get(2));
        assertEquals(3, testAnalyzer.getSequenceList().size());

        int numberOfDomains = testAnalyzer.transmembraneDomainAnalysis();
        assertEquals(2, numberOfDomains);
    }

    @Test
    void testTransmembraneDomainAnalysisEndsOVerThresHold(){

        PrimaryProteinSequence sequence = new PrimaryProteinSequence("MAVILFCMAVILVILVILFCIVIVLF"
                + "QDNKRDQEHPYWSTSTSTSTST" + "IVYPHQDTQCFEQDNKRKRMAG" + "FCMAGTSWIVLIVLFCQDNKRKRKRKR"
        + "MAVILFCMAVILVILVILFCIVIVIVLF");
        testAnalyzer.addSequenceToList(sequence);
        testAnalyzer.setThreshold(1.5);
        testAnalyzer.setIndexOfList(0);
        PrimaryProteinSequence firstSequence = testAnalyzer.getSpecificSequence(0);
        assertEquals(firstSequence, testAnalyzer.getSequenceList().get(0));
        assertEquals(1, testAnalyzer.getSequenceList().size());

        int numberOfDomains = testAnalyzer.transmembraneDomainAnalysis();
        assertEquals(2, numberOfDomains);
    }


    @Test
    void testTransmembraneDomainAnalysisNone(){
        PrimaryProteinSequence sequence = new PrimaryProteinSequence("GTSGTSGTSWYPHPPHEQDNKRGTSGTSWYPKRKRKRKRKRKRKR");
        testAnalyzer.addSequenceToList(sequence);
        testAnalyzer.setThreshold(1.0);
        testAnalyzer.setIndexOfList(0);
        int numberOfDomains = testAnalyzer.transmembraneDomainAnalysis();
        assertEquals(0, numberOfDomains);

    }

    @Test
    void testAminoAcidMap(){

        HashMap<String, Double> expected = new HashMap<>();
        expected.put("M", 1.9);
        expected.put("A", 1.8);
        expected.put("R", -4.5);
        expected.put("N", -3.5);
        expected.put("D", -3.5);
        expected.put("C", 2.5);
        expected.put("E", -3.5);
        expected.put("Q", -3.5);
        expected.put("G", -0.4);
        expected.put("H", -3.2);
        expected.put("I", 4.5);
        expected.put("L", 3.8);
        expected.put("K", -3.9);
        expected.put("F", 2.8);
        expected.put("P", -1.6);
        expected.put("S", -0.8);
        expected.put("T", -0.7);
        expected.put("W", -0.9);
        expected.put("Y", -1.3);
        expected.put("V", 4.2);

        HashMap<String, Double> aminoAcidMap = testAnalyzer.getAminoAcidMap();
        assertEquals(aminoAcidMap, expected);
        assertEquals(20, expected.size());
    }

    @Test
    void testHydropathyScoreAnalysisHydrophilic(){
        assertEquals("hydrophilic", testAnalyzer.hydropathyScoreAnalysis("G"));
    }

    @Test
    void testHydropathyScoreAnalysisHydrophobic(){
        assertEquals("hydrophobic", testAnalyzer.hydropathyScoreAnalysis("A"));
    }

    @Test
    void removeSequenceTestTrue() {
        PrimaryProteinSequence sequence1 = new PrimaryProteinSequence("QDNKRPYWKSLDSFNDSKFNDKFKNFSDF");
        PrimaryProteinSequence sequence2 = new PrimaryProteinSequence("IVLFCMAGTSWYPHQDNK");
        testAnalyzer.addSequenceToList(sequence1);
        testAnalyzer.addSequenceToList(sequence2);
        testAnalyzer.removeSequence(0);
        assertEquals(1, testAnalyzer.getSequenceList().size());
    }

    @Test
    void removeSequenceTestFalse() {
        testAnalyzer.removeSequence(0);
        assertEquals(0, testAnalyzer.getSequenceList().size());
    }
}
