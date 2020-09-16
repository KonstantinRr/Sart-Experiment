# Sart Experiment

This repository implements a basic tool to gather participant data in the famous **S**ustained **A**ttention to **R**esponse **T**ask (SART).
Read more about the experiment in [this paper](https://www.sciencedirect.com/science/article/pii/B9780123757319500598)
if you are not familiar with it. The tool was developed in the course Cognitive Modelling at the UNIVERSITY OF GRONINGEN 2020.

## Installing
The project is build using Java [Eclipse](https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers).
Clone the project and it use the Java Eclipse to run the project.
```
git clone https://github.com/KonstantinRr/Sart-Experiment && cd Sart-Experiment
```

## Data storage
The software stores the results of each trial in participant files. The data is stored in txt format.
The first three lines give general information about the experiment. The first line contains a string
determing the experiment's name, the second line contains an integer value containing the mask time
and the third line contains an integer giving the visible time. Read more about these parameters in
the paper.

All following lines contain three values seperated by colons:
1. Gives the input stimulus given by the program.
2. Gives the output that was given by the user (may be null if no output).
3. Gives the between the interval between the stimulus becoming visible and the users response
(may be null if no output).


```
Experiment 2 (6000ms)
6000
1000
---- DATA ----
Q:O:593
Q:O:508
Q:O:555
Q:O:561
Q:O:424
O:null:null
Q:O:440
Q:O:518
Q:O:483
Q:O:536
Q:O:391
Q:O:485
O:O:524
Q:O:508
O:O:466
```

## Credits
Cognitive Modelling - UNIVERSITY OF GRONINGEN - 2020
