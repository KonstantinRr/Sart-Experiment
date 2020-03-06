package sart;

public class ExperimentSettings {
	private long maskTime, intervalTime, startTime, goTime;
	private double oProbab;
	private int trials;
	private boolean[] trialValues;
	private String name;
	public ExperimentSettings(String name,
			long startTime, long goTime, long maskTime, long intervalTime,
			double oProbab, int trials) {
		this.maskTime = maskTime;
		this.goTime = goTime;
		this.intervalTime = intervalTime;
		this.oProbab = oProbab;
		this.trials = trials;
		this.startTime = startTime;
		this.name = name;
		this.trialValues = new boolean[trials];
		for (int i = 0; i < trials; i++) {
			trialValues[i] = Math.random() < oProbab;
		}
	}
	
	public String getName() {
		return name;
	}
	public long getGoTime() {
		return goTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public long getMaskTime() {
		return maskTime;
	}
	public long getIntervalTime() {
		return intervalTime;
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
