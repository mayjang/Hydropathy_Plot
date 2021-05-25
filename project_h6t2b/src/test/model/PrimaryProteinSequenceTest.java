package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryProteinSequenceTest {

    private PrimaryProteinSequence testPrimarySequence;

    @BeforeEach
    void runBefore(){
        testPrimarySequence = new PrimaryProteinSequence("MARNARNDRRANVARMNDCDNCEGHIARNMARNARNDRRANVARDNCMNAARNA");
    }

    @Test
    void initialPrimaryProteinSequence(){
        assertEquals("MARNARNDRRANVARMNDCDNCEGHIARNMARNARNDRRANVARDNCMNAARNA",
                testPrimarySequence.getAminoAcidSequence());
    }

    @Test
    void testAddAaSequence(){
        String newSequence = "EIENPETSDQ";
        testPrimarySequence.addAaSequence(newSequence);
        assertEquals("MARNARNDRRANVARMNDCDNCEGHIARNMARNARNDRRANVARDNCMNAARNA"
                + newSequence, testPrimarySequence.getAminoAcidSequence());
    }

    @Test
    void testStartsWithMTrue(){
        boolean startsWith = testPrimarySequence.startsWithM(testPrimarySequence.getAminoAcidSequence());
        assertTrue(startsWith);
    }

    @Test
    void testStartsWithMFalse(){
        boolean startsWith = testPrimarySequence.startsWithM("ARMHRV");
        assertFalse(startsWith);

    }

    @Test
    void testLength() {
        assertEquals(54, testPrimarySequence.length());
    }

}
