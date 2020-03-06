package sart;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Experiment {
	private JFrame frame;
	private JLabel label;
	private volatile int currentTrial;
	
	private ExperimentSettings settings;
	
	public Experiment(ExperimentSettings settings) {
		this.settings = settings;
		this.currentTrial = -1;
		
		frame = new JFrame();
		frame.setTitle(settings.getName());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Disposing Client Experiment Mangaer");
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		label = new JLabel("Q");
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(label, BorderLayout.CENTER);
		
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	public boolean[] runExperiment() throws InterruptedException {
		boolean[] trialResponses = new boolean[settings.getTrials()];
		this.currentTrial = 0;
		for (int i = 0; i < settings.getTrials(); i++) {
			if (settings.getTrialValues()[i]) {
				label.setText("O");
			} else {
				label.setText("Q");
			}
			Thread.sleep(settings.getMaskTime());
		}
		return trialResponses;
	}
}
