package model;

// Primary protein sequence later used to create a hydropathy plot
public class PrimaryProteinSequence {

    private String aminoAcidSequence = "";

    //REQUIRES: Amino acid sequence to be upper case, starts with "M"
    //EFFECTS: constructs an amino acid sequence given sequence in string
    public PrimaryProteinSequence(String sequence) {
        setSequence(sequence);
    }

    public boolean setSequence(String sequence) {
        String upperCaseSequence = sequence.toUpperCase();
        if (startsWithM(upperCaseSequence)) {
            aminoAcidSequence = upperCaseSequence;
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds sequence to existing aminoAcidsSequence
    public void addAaSequence(String sequence) {
        aminoAcidSequence += sequence;
    }

    public String getAminoAcidSequence() {
        return aminoAcidSequence;
    }


    //EFFECTS: returns true if sequence starts with "M", false otherwise
    public boolean startsWithM(String sequence) {
        if (sequence.length() > 0 &&  sequence.substring(0, 1).equals("M")) {
            return true;
        }
        return false;
    }

    // EFFECTS: calculates length of the amino acid sequence
    public int length() {
        return aminoAcidSequence.length();
    }

}
