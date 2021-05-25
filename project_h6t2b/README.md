# Hydropathy Plot 

## Given an amino acid sequence, creates a hydropathy plot and produces necessary information

***What* will this application do?**
- This application will output necessary information related to the hydrophilicity/hydrophobicity
of the given primary protein sequence (amino acid sequence).

***Who* will use this application?**
- This application will be used by microbiologists to determine the number of transmembrane domains
of a protein 

***Why* is this project of interest to me?**
- I'm interested in the bioinformatics field. When taking BIOL 200 course, I came across a bioinformatics
technique called the "Hydropathy Plot", used to determine the hydrophobic/hydrophilic characteristics of a protein. 

**User Stories:**

- As a user, I want to be able to add a new amino acid sequence to a list and be able to choose which sequence 
  to analyze from the list
- As a user, I want to be able to determine whether a single amino acid is hydrophobic or hydrophilic 
- As a user, I want to be able calculate the number of transmembrane domains in a given amino acid sequence. 

- As a user, I want to be able to save my list of amino acid sequences
- As a user, I want to ba able to load my list of amino acid sequences

**Phase 4: Task 2**

- Made use of the Map interface in the PrimaryProteinAnalyzer class and created an amino acid hydrophilicity score map.
- Methods involved: 'createAminoAcidMap', 'getAminoAcidMap', 'makeAminoAcidCodes', 'aminoAcidHydropathyScoreDictionary'

**Phase 4: Task 3**

- Possibly adding exceptions to make my code more robust. 