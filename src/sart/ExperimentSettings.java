package sart;

public class ExperimentSettings {
	private long maskTime;
	private double oProbab;
	private int trials;
	private boolean[] trialValues;
	private String name;
	public ExperimentSettings(String name, long maskTime, double oProbab, int trials) {
		this.maskTime = maskTime;
		this.oProbab = oProbab;
		this.trials = trials;
		this.name = name;
		this.trialValues = new boolean[trials];
		for (int i = 0; i < trials; i++) {
			trialValues[i] = Math.random() < oProbab;
		}
	}
	
	public String getName() {
		return name;
	}
	public long getMaskTime() {
		return maskTime;
	}
	public double getoProbab() {
		return oProbab;
	}
	public int getTrials() {
		return trials;
	}
	public boolean[] getTrialValues() {
		return trialValues;
	}
}
