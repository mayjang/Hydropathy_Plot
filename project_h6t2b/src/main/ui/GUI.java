package ui;

import model.PrimaryProteinAnalyzer;
import model.PrimaryProteinSequence;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class GUI extends JFrame implements ActionListener {

    private PrimaryProteinAnalyzer proteinAnalyzer;
    private PrimaryProteinSequence proteinSequence;
    private JLabel labelForProteinSequence;
    private JLabel labelForIndividualCode;
    private JLabel labelForThreshold;
    private JLabel labelForMinimumLinearCount;
    private JLabel labelForNumberOfDomains;
    private JLabel labelForIndividualCodeAnswer;
    private JTextField fieldForProteinSequenceAdded;
    private JTextField fieldForIndividualCode;
    private JTextField fieldForThreshold;
    private JTextField fieldForMinimumLinearCount;
    private DefaultListModel<String> listOfSequences;
    private JList<String> proteinSequencesAddedJList;

    private JFreeChart hydropathyChart;

    //MODIFIES: this
    //EFFECTS: has functions related to the display (size, colour, menu, labels, fields, etc.)
    public GUI(String title) {
        super(title);
        setLayout(null);

        setPreferredSize(new Dimension(1400, 1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.pink);

        createMenu();
        initConstructor();

        createLabels();
        createFields();

        createButtonsAddSequenceSetThresholdAndMinimumLinearCount();
        createButtonsAnalyzeHydrophilicityAndCreatePlot();
        createButtonRemoveSequence();

        add(proteinSequencesAddedJList);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createGraph();
    }

    //MODIFIES: this
    //EFFECTS: initializes constructors
    public void initConstructor() {
        proteinAnalyzer = new PrimaryProteinAnalyzer();
        proteinSequence = new PrimaryProteinSequence("");
        double threshold = proteinAnalyzer.getThreshold();
        double minimumLinearCount = proteinAnalyzer.getMinimumLinearCount();
        listOfSequences = new DefaultListModel<>();
        proteinSequencesAddedJList = new JList<String>(listOfSequences);
        proteinSequencesAddedJList.setBounds(870, 200, 480, 500);
    }

    //MODIFIES: this
    //EFFECTS: creates the hydropathy plot
    public void createGraph() {
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        chartPanel.setSize(800, 500);
        chartPanel.setLocation(50, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates menu with menu items
    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        file.add(quit);
        menuBar.add(file);

        createItem(file);
    }

    //MODIFIES: this
    //EFFECTS: creates labels with label name, location on panel, and size
    public void createLabels() {
        labelForProteinSequence = new JLabel("Protein Sequence to Analyze:");
        labelForProteinSequence.setBounds(50, 30, 400, 40);

        labelForIndividualCode = new JLabel("Single Amino Acid Code:");
        labelForIndividualCode.setBounds(880, 65, 200, 40);

        labelForThreshold = new JLabel("Threshold:");
        labelForThreshold.setBounds(170, 80, 300, 40);

        labelForMinimumLinearCount = new JLabel("Minimum Linear Count:");
        labelForMinimumLinearCount.setBounds(95, 115, 400, 40);

        labelForNumberOfDomains = new JLabel("Number of Transmembrane domains:");
        labelForNumberOfDomains.setBounds(50, 150, 400, 40);

        labelForIndividualCodeAnswer = new JLabel("Amino Acid inputted is: ");
        labelForIndividualCodeAnswer.setBounds(880, 120, 400, 30);

        add(labelForProteinSequence);
        add(labelForIndividualCode);
        add(labelForThreshold);
        add(labelForMinimumLinearCount);
        add(labelForNumberOfDomains);
        add(labelForIndividualCodeAnswer);
    }

    //MODIFIES: this
    //EFFECTS: creates fields with field name, location, size
    public void createFields() {
        fieldForProteinSequenceAdded = new JTextField(10);
        fieldForProteinSequenceAdded.setBounds(250, 35, 400, 30);

        fieldForIndividualCode = new JTextField(10);
        fieldForIndividualCode.setBounds(1040, 70, 40, 30);

        fieldForThreshold = new JTextField(10);
        fieldForThreshold.setBounds(250, 85, 40, 30);
        fieldForThreshold.setText("0");

        fieldForMinimumLinearCount = new JTextField(10);
        fieldForMinimumLinearCount.setBounds(250, 120, 80, 30);
        fieldForMinimumLinearCount.setText("10");

        add(fieldForProteinSequenceAdded);
        add(fieldForIndividualCode);
        add(fieldForThreshold);
        add(fieldForMinimumLinearCount);
    }

    //MODIFIES: this
    //EFFECTS: creates menu items
    public void createItem(JMenu file) {
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
    }

    //MODIFIES: this
    //EFFECTS: creates buttons for adding sequence, setting threshold, and setting minimum linear count
    public void createButtonsAddSequenceSetThresholdAndMinimumLinearCount() {
        JButton addSequenceButton = new JButton("Add Sequence");
        addSequenceButton.setBounds(650, 35, 150, 30);
        addSequenceButton.setActionCommand("addSequence");
        addSequenceButton.addActionListener(this);
        add(addSequenceButton);

        JButton setThresholdButton = new JButton("Set Threshold");
        setThresholdButton.setBounds(290, 85, 150, 30);
        setThresholdButton.setActionCommand("setThreshold");
        setThresholdButton.addActionListener(this);
        add(setThresholdButton);

        JButton setMinimumLinearCountButton = new JButton("Set Minimum Linear Count");
        setMinimumLinearCountButton.setBounds(330, 120, 200, 30);
        setMinimumLinearCountButton.setActionCommand("setMinimumLinearCount");
        setMinimumLinearCountButton.addActionListener(this);
        add(setMinimumLinearCountButton);
    }

    //MODIFIES: this
    //EFFECTS: creates buttons for analyzing hydrophilicity, and creating graph plot
    public void createButtonsAnalyzeHydrophilicityAndCreatePlot() {
        JButton analyzeHydrophilicityButton = new JButton("Analyze Hydrophlicity");
        analyzeHydrophilicityButton.setBounds(1100, 70, 200, 30);
        analyzeHydrophilicityButton.setActionCommand("analyzeHydrophilicity");
        analyzeHydrophilicityButton.addActionListener(this);
        add(analyzeHydrophilicityButton);

        JButton plotGraphButton = new JButton("Plot Graph");
        plotGraphButton.setBounds(880, 160, 150, 30);
        plotGraphButton.setActionCommand("plotGraph");
        plotGraphButton.addActionListener(this);
        add(plotGraphButton);
    }

    //MODIFIES: this
    //EFFECTS: creates button for removing selected sequence
    public void createButtonRemoveSequence() {
        JButton removeSequenceButton = new JButton("Remove Sequence");
        removeSequenceButton.setBounds(1050, 160, 200, 30);
        removeSequenceButton.setActionCommand("removeSequence");
        removeSequenceButton.addActionListener(this);
        add(removeSequenceButton);
    }

    //EFFECTS: saves the information of current protein analyzer
    private void saveProteinAnalyzer() throws FileNotFoundException {
        JFileChooser chooser = new JFileChooser();
        int userSelection = chooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = chooser.getSelectedFile();
        JsonWriter writer = new JsonWriter(fileToSave.getAbsolutePath());
        writer.open();
        writer.write(proteinAnalyzer);
        writer.close();
    }

    //EFFECTS: opens protein analyzer information from file
    private void openProteinAnalyzer() throws IOException {

        JFileChooser chooser = new JFileChooser();
        int userSelection = chooser.showOpenDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToOpen = chooser.getSelectedFile();

        JsonReader reader = new JsonReader(fileToOpen.getAbsolutePath());
        proteinAnalyzer = reader.read();

        ArrayList<PrimaryProteinSequence> addedSequences = proteinAnalyzer.getSequenceList();
        updateList(addedSequences);
        Double threshold = proteinAnalyzer.getThreshold();
        int minimumCount = proteinAnalyzer.getMinimumLinearCount();
        fieldForThreshold.setText(Double.toString(threshold));
        fieldForMinimumLinearCount.setText(Integer.toString(minimumCount));
    }

    //REQUIRES: a list of amino acid sequences
    //EFFECTS: updates the list by adding user-inputted sequences
    private void updateList(ArrayList<PrimaryProteinSequence> addedSequences) {
        listOfSequences.removeAllElements();
        for (int i = 0; i < addedSequences.size(); i++) {
            PrimaryProteinSequence sequence = addedSequences.get(i);
            String sequenceString = "";
            sequenceString = sequenceString + sequence.getAminoAcidSequence();
            listOfSequences.addElement(sequenceString);
        }
    }

    //EFFECTS: displays the application title and enables visibility for user
    public static void main(String[] args) {
        GUI myApp = new GUI("Hydropathy Plot");
        myApp.setVisible(true);
    }

    //EFFECTS: calls functions depending on the action performed that is called
    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = e.getActionCommand();
        if (userInput.equals("Quit")) {
            actionPerformedQuit();
        } else if (userInput.equals("Open")) {
            actionPerformedOpen();
        } else if (userInput.equals("Save")) {
            actionPerformedSave();
        } else if (userInput.equals("Add Sequence") || userInput.equals("addSequence")) {
            actionPerformedAddSequenceToList();
        } else if (userInput.equals("Analyze Hydrophilicity") || userInput.equals("analyzeHydrophilicity")) {
            actionPerformedHydropathyScoreAnalysis();
        } else if (userInput.equals("Number of Transmembrane Domains") || userInput.equals("numberOfDomains")) {
            actionPerformedHydropathyScoreAnalysis();
        } else if (userInput.equals("Plot Hydropathy Graph") || userInput.equals("plotGraph")) {
            actionPerformedDrawHydropathyGraph();
        } else if (userInput.equals("Remove Sequence") || userInput.equals("removeSequence")) {
            actionPerformedRemoveSequence();
        }
    }

    //EFFECTS: exits application window when user selects quit
    public void actionPerformedQuit() {
        System.exit(0);
    }


    //EFFECTS: opens protein analyzer when action performed is open
    public void actionPerformedOpen() {
        try {
            openProteinAnalyzer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: saves protein analyzer when action performed is save
    public void actionPerformedSave() {
        try {
            saveProteinAnalyzer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: removes user-selected sequence from jlist
    public void actionPerformedRemoveSequence() {
        int selectedIndex = proteinSequencesAddedJList.getSelectedIndex();
        int size = proteinAnalyzer.getSequenceList().size();
        if ((selectedIndex < 0) || (selectedIndex >= size)) {
            return;
        }
        listOfSequences.remove(selectedIndex);
        proteinAnalyzer.removeSequence(selectedIndex);
    }

    //MODIFIES: this
    //EFFECTS: adds user-inputted sequence onto jlist, displays the list after update
    public void actionPerformedAddSequenceToList() {
        String sequence = fieldForProteinSequenceAdded.getText();
        PrimaryProteinSequence proteinSequence = new PrimaryProteinSequence("");
        if (proteinSequence.setSequence(sequence)) {
            proteinAnalyzer.addSequenceToList(proteinSequence);
            ArrayList<PrimaryProteinSequence> proteinList = proteinAnalyzer.getSequenceList();
            updateList(proteinList);
        } //else : error message box, clear text box
    }

    //EFFECTS: Analyzes hydropathy score of user-inputted amino acid code, displays whether it is "hydrophilic"
    //         or "hydrophobic"
    public void actionPerformedHydropathyScoreAnalysis() {
        String individualAaCode = fieldForIndividualCode.getText();
        String answer = proteinAnalyzer.hydropathyScoreAnalysis(individualAaCode);
        labelForIndividualCodeAnswer.setText(individualAaCode + " is " + answer);
    }

    public void setThreshold() {
        String thresholdString = fieldForThreshold.getText();
        if (thresholdString == "") {
            thresholdString = "0";
        }

        Double threshold = Double.parseDouble(thresholdString);

        proteinAnalyzer.setThreshold(threshold);
    }

    //MODIFIES: this
    //EFFECTS: analyzes number of transmembrane domains of the selected sequence
    public void transmembraneDomainAnalysisUi() {
//        String thresholdString = fieldForThreshold.getText();
//        if (thresholdString == "") {
//            thresholdString = "0";
//        }
//
//        Double threshold = Double.parseDouble(thresholdString);
//
//        proteinAnalyzer.setThreshold(threshold);

        setThreshold();
        String minimumLinearCountString = fieldForMinimumLinearCount.getText();
        if (minimumLinearCountString == "") {
            minimumLinearCountString = "10";
        }
        int minimumLinearCount = Integer.parseInt(minimumLinearCountString);
        proteinAnalyzer.setMinimumLinearCount(minimumLinearCount);

        int selectedIndex = proteinSequencesAddedJList.getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        proteinAnalyzer.setIndexOfList(selectedIndex);

        int domains = proteinAnalyzer.transmembraneDomainAnalysis();

        labelForNumberOfDomains.setText("Number of Transmembrane domains: " + domains);
    }

    //EFFECTS: displays hydropathy graph of the chosen sequence
    public void actionPerformedDrawHydropathyGraph() {
        setThreshold();
        int selectedIndex = proteinSequencesAddedJList.getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        proteinAnalyzer.setIndexOfList(selectedIndex);

        CategoryDataset dataset = createDataset();

        hydropathyChart.getCategoryPlot().setDataset(dataset);

        transmembraneDomainAnalysisUi();
    }

    //EFFECTS: creates chart panel of the hydropathy plot
    private JPanel createChartPanel() {
        String chartTitle = "Hydropathy Plot";
        String xaxisLabel = "Position of Amino Acid";
        String yaxisLabel = "Hydrophilicity / Hydrophobicity";

        CategoryDataset dataset = createDataset();

        hydropathyChart = ChartFactory.createLineChart(chartTitle, xaxisLabel, yaxisLabel, dataset);

        return new ChartPanel(hydropathyChart);
    }

    //EFFECTS: creates data set using user-selected sequence
    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Sequence Hydrophilicity";
        String thresholdSeries = "Threshold";

        PrimaryProteinSequence selectedSequence = proteinAnalyzer.getSelectedSequence();
        if (selectedSequence == null) {
            return dataset;
        }

        String selectedString = selectedSequence.getAminoAcidSequence();

        for (int i = 0; i < selectedString.length(); i++) {
            String code = Character.toString(selectedString.charAt(i));
            Double yvalue = proteinAnalyzer.getAminoAcidMap().get(code);
            int xvalue = i + 1;
            dataset.addValue(yvalue, series, Integer.toString(xvalue));
        }
        for (int j = 0; j < selectedString.length(); j++) {
            int xvalue2 = j + 1;
            dataset.addValue(proteinAnalyzer.getThreshold(), thresholdSeries, Integer.toString(xvalue2));
        }

        return dataset;
    }


}
