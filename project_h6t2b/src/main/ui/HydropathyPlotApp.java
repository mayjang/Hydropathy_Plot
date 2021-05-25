package ui;

import model.PrimaryProteinAnalyzer;
import model.PrimaryProteinSequence;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

// Hydropathy Plot App, which calculates number of transmembrane domains, determines if a code is hydrophilic
// or hydrophobic, set linear minimum number of codes in a row
// process commands
public class HydropathyPlotApp {

    private PrimaryProteinAnalyzer sequenceAnalyzer;
    private Scanner input;

    // EFFECTS: runs the Hydropathy Plot application
    public HydropathyPlotApp() throws IOException {
        runPrimaryProteinSequence();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPrimaryProteinSequence() throws IOException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws IOException {
        if (command.equals("a")) {
            addSequence();
        } else if (command.equals("o")) {
            chooseSequenceToAnalyze();
        } else if (command.equals("l")) {
            setLinearMinimum();
        } else if (command.equals("t")) {
            setThreshold();
        } else if (command.equals("h")) {
            hydrophilicityAnalysis();
        } else if (command.equals("c")) {
            calculateNumberOfDomains();
        } else if (command.equals("s")) {
            saveProteinSequence();
        } else if (command.equals("r")) {
            openProteinSequence();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes amino acid sequence
    private void init() {
        sequenceAnalyzer = new PrimaryProteinAnalyzer();

        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n(Make sure to go in a sequential order and only enter amino acid codes in a and h, "
                + "you may skip h)");
        System.out.println("Select from:");
        System.out.println("\ta -> addSequence");
        System.out.println("\to-> chooseSequence");
        System.out.println("\tl -> setLinearMinimum");
        System.out.println("\tt -> setThreshold");
        System.out.println("\th -> hydrophilicityAnalysis");
        System.out.println("\tc -> calculateNumberOfDomains");
        System.out.println("\ts -> save");
        System.out.println("\tr -> open");
        System.out.println("\tq -> quit");

    }

    // EFFECTS: saves current protein sequence
    private void saveProteinSequence() throws FileNotFoundException {
        JsonWriter writer = new JsonWriter("./data/proteinSequenceSaved.json");
        writer.open();
        writer.write(sequenceAnalyzer);
        writer.close();
    }

    // EFFECTS: opens protein sequence file
    private void openProteinSequence() throws IOException {
        JsonReader reader = new JsonReader("./data/proteinSequenceSaved.json");
        sequenceAnalyzer = reader.read();
    }

    // REQUIRES: existing amino acid sequence
    // MODIFIES: this
    // EFFECTS: adds amino acid sequence to existing amino acid sequence
    private void addSequence() {
        System.out.println("Enter sequence you'd like to add");
        String sequence = input.next();
        PrimaryProteinSequence aminoAcidSequence = new PrimaryProteinSequence(sequence);
        sequenceAnalyzer.addSequenceToList(aminoAcidSequence);
        System.out.println("Added sequences so far");
        ArrayList<PrimaryProteinSequence> list = sequenceAnalyzer.getSequenceList();
        for (int i = 0; i < list.size(); i++) {
            PrimaryProteinSequence sequenceInList = list.get(i);
            System.out.println(sequenceInList.getAminoAcidSequence());
        }

    }

    // REQUIRES: a user-input integer
    // EFFECTS: sets liner minimum integer
    private void setLinearMinimum() {
        System.out.println("Enter integer (range from 1 to 100):");
        int linearMinimum = input.nextInt();
        sequenceAnalyzer.setMinimumLinearCount(linearMinimum);
        System.out.printf("Inputted linear minimum = " + Integer.toString(linearMinimum));
    }

    // REQUIRES: a user-input double
    // EFFECTS: sets threshold double
    private void setThreshold() {
        System.out.println("Enter double (double from 1.0 to 6.0):");
        double threshold = input.nextDouble();
        sequenceAnalyzer.setThreshold(threshold);
        System.out.printf("Inputted threshold = " + Double.toString(threshold));
    }

    // REQUIRES: an input amino acid code
    // EFFECTS: analyzes if code is hydrophobic or hydrophilic
    private void hydrophilicityAnalysis() {
        System.out.println("Enter a single amino acid code to find out if it's hydrophobic or hydrophilic:");
        String code = input.next();
        String answer = sequenceAnalyzer.hydropathyScoreAnalysis(code);
        System.out.println("The inputted amino acid code is: " + answer);
    }

    // EFFECTS: chooses sequence to analyze from the list of sequences
    private void chooseSequenceToAnalyze() {
        System.out.println("Enter the number corresponding to the sequence you'd like to analyze:");
        int count = 1;
        int index = 0;
        ArrayList<PrimaryProteinSequence> list = sequenceAnalyzer.getSequenceList();
        for (int i = 0; i < list.size(); i++) {
            PrimaryProteinSequence sequence = list.get(index);
            System.out.println(count + ". "
                    + sequence.getAminoAcidSequence());
            count++;
            index++;
        }
        int inputted = input.nextInt();
        sequenceAnalyzer.setIndexOfList(inputted - 1);

        System.out.println("Sequence you chose to analyze: " + inputted);

    }

    // EFFECTS: calculates number of transmemembrane domains
    private void calculateNumberOfDomains() {
        System.out.println(sequenceAnalyzer.transmembraneDomainAnalysis());
    }



}
