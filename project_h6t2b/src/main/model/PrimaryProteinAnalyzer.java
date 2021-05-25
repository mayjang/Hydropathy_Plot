package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;

//Consists of protein sequences
//Analyzes protein sequences in the list
public class PrimaryProteinAnalyzer implements Writable {

    private ArrayList<PrimaryProteinSequence> primaryProteinSequences;
    private int indexOfList;
    private double threshold;
    private int minimumLinearCount;
    private ArrayList<String> makeAminoAcidCodes;
    private ArrayList<Double> aminoAcidHydropathyScoreDictionary;
    private HashMap<String, Double> aminoAcidMap;

    //REQUIRES: A list of primary protein sequences
    //EFFECTS: constructs a list of primary protein sequences
    public PrimaryProteinAnalyzer() {
        primaryProteinSequences = new ArrayList<>();
        makeAminoAcidCodes();
        aminoAcidHydropathyScoreDictionary();
        createAminoAcidMap();
    }

    //EFFECTS: creates an amino acid map with key as amino acid code, and hydropathy score as value
    public void createAminoAcidMap() {
        aminoAcidMap = new HashMap<String, Double>();

        for (int i = 0; i < aminoAcidHydropathyScoreDictionary.size(); i++) {
            double hydropathyScore = aminoAcidHydropathyScoreDictionary.get(i);
            String aminoAcidCode = makeAminoAcidCodes.get(i);
            aminoAcidMap.put(aminoAcidCode, hydropathyScore);
        }
    }

    public HashMap<String, Double> getAminoAcidMap() {
        return aminoAcidMap;
    }

    // EFFECTS: make amino acid codes
    private void makeAminoAcidCodes() {
        makeAminoAcidCodes = new ArrayList<>();
        makeAminoAcidCodes.add("M");
        makeAminoAcidCodes.add("A");
        makeAminoAcidCodes.add("R");
        makeAminoAcidCodes.add("N");
        makeAminoAcidCodes.add("D");
        makeAminoAcidCodes.add("C");
        makeAminoAcidCodes.add("E");
        makeAminoAcidCodes.add("Q");
        makeAminoAcidCodes.add("G");
        makeAminoAcidCodes.add("H");
        makeAminoAcidCodes.add("I");
        makeAminoAcidCodes.add("L");
        makeAminoAcidCodes.add("K");
        makeAminoAcidCodes.add("F");
        makeAminoAcidCodes.add("P");
        makeAminoAcidCodes.add("S");
        makeAminoAcidCodes.add("T");
        makeAminoAcidCodes.add("W");
        makeAminoAcidCodes.add("Y");
        makeAminoAcidCodes.add("V");

    }

    //EFFECTS: make list of hydropathy scores corresponding to amino acid codes based on:
    // https://en.wikipedia.org/wiki/Hydrophilicity_plot
    private void aminoAcidHydropathyScoreDictionary() {
        aminoAcidHydropathyScoreDictionary = new ArrayList<>();
        aminoAcidHydropathyScoreDictionary.add(1.9);
        aminoAcidHydropathyScoreDictionary.add(1.8);
        aminoAcidHydropathyScoreDictionary.add(-4.5);
        aminoAcidHydropathyScoreDictionary.add(-3.5);
        aminoAcidHydropathyScoreDictionary.add(-3.5);
        aminoAcidHydropathyScoreDictionary.add(2.5);
        aminoAcidHydropathyScoreDictionary.add(-3.5);
        aminoAcidHydropathyScoreDictionary.add(-3.5);
        aminoAcidHydropathyScoreDictionary.add(-0.4);
        aminoAcidHydropathyScoreDictionary.add(-3.2);
        aminoAcidHydropathyScoreDictionary.add(4.5);
        aminoAcidHydropathyScoreDictionary.add(3.8);
        aminoAcidHydropathyScoreDictionary.add(-3.9);
        aminoAcidHydropathyScoreDictionary.add(2.8);
        aminoAcidHydropathyScoreDictionary.add(-1.6);
        aminoAcidHydropathyScoreDictionary.add(-0.8);
        aminoAcidHydropathyScoreDictionary.add(-0.7);
        aminoAcidHydropathyScoreDictionary.add(-0.9);
        aminoAcidHydropathyScoreDictionary.add(-1.3);
        aminoAcidHydropathyScoreDictionary.add(4.2);

    }

    //MODIFIES: this
    //EFFECTS: add sequence to a list of sequences
    public void addSequenceToList(PrimaryProteinSequence sequence) {
        primaryProteinSequences.add(sequence);
    }

    //MODIFIES: this
    //EFFECTS: removes sequence from a list of sequences
    public void removeSequence(int index) {
        if ((index < 0) || (index >= getSequenceList().size())) {
            return;
        }
        primaryProteinSequences.remove(index);
    }

    public ArrayList<PrimaryProteinSequence> getSequenceList() {
        return primaryProteinSequences;
    }

    public PrimaryProteinSequence getSpecificSequence(int i) {
        if (i < primaryProteinSequences.size()) {
            return primaryProteinSequences.get(i);
        } else {
            return null;
        }
    }

    public PrimaryProteinSequence getSelectedSequence() {
        int index = getIndexOfList();
        return getSpecificSequence(index);
    }

    public int getMinimumLinearCount() {
        return minimumLinearCount;
    }

    public void setMinimumLinearCount(int linearCount) {
        minimumLinearCount = linearCount;
    }

    public void setThreshold(double number) {
        threshold = number;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setIndexOfList(int index) {
        indexOfList = index;
    }

    public int getIndexOfList() {
        return indexOfList;
    }

    //EFFECTS: returns "hydrophilic" if hydropathic score < 1, "hydrophobic" otherwise
    public String hydropathyScoreAnalysis(String code) {
        double hydropathyScore = aminoAcidMap.get(code);
        if (hydropathyScore < 1) {
            return "hydrophilic";
        } else { // if (hydropathyScore >= 1) {
            return "hydrophobic";
        }
    }

    //EFFECTS: returns the number of transmembrane domains in the amino acid sequence
    public int transmembraneDomainAnalysis() {
        int count = 0;
        int numberOfDomains = 0;
        String code = "";

        PrimaryProteinSequence aminoAcidSequence = primaryProteinSequences.get(indexOfList);
        String aminoAcidSequenceString = aminoAcidSequence.getAminoAcidSequence();

        int len = aminoAcidSequenceString.length();
        for (int i = 0; i < aminoAcidSequenceString.length(); i++) {
            code = aminoAcidSequenceString.substring(i,i + 1);
            System.out.println(i);
            double scores = aminoAcidMap.get(code);
            if (scores >= threshold) {
                count += 1;
            } else {
                if (count >= getMinimumLinearCount()) {
                    numberOfDomains++;
                }
                count = 0;
            }
        }

        if (count >= getMinimumLinearCount()) {
            numberOfDomains++;
        }

        return numberOfDomains;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sequenceList", sequenceListToJson());
        json.put("linearMinimum", getMinimumLinearCount());
        json.put("threshold", getThreshold());

        return json;
    }

    // EFFECTS: returns things in this primary protein sequences as a JSON array
    private JSONArray sequenceListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (PrimaryProteinSequence sequence : primaryProteinSequences) {
            String sequenceString = sequence.getAminoAcidSequence();
            jsonArray.put(sequenceString);
        }

        return jsonArray;
    }


}
