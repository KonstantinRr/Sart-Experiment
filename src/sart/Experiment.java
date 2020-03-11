package sart;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Experiment implements Callable<Integer> {
	private JFrame frame;
	private JLabel label;
	
	private ExperimentSettings settings;
	
	private volatile TrialResponse[] trialResponses;
	private volatile int currentTrial;
	private volatile long lastTime;
	private boolean isTrial;
	
	private class TrialResponse {
		private long rt;
		private String response;
		
		public TrialResponse(long rt, String response) {
			this.rt = rt;
			this.response = response;
		}
		
		public long getResponseTime() {
			return rt;
		}
		public String getResponse() {
			return response;
		}
	}
	
	public Experiment(ExperimentSettings settings) {
		this.settings = settings;
		this.currentTrial = 0;
		this.isTrial = false;
		this.trialResponses = new TrialResponse[settings.getTrials()];
		
		frame = new JFrame();
		frame.setTitle(settings.getName());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isTrial && trialResponses[currentTrial] == null)
				{
					// Received unique event
					System.out.println("Got char " + e.getKeyChar());
					String var = new String(new char[] { e.getKeyChar() });
					
					long cTime = System.currentTimeMillis();
					trialResponses[currentTrial] = new TrialResponse(cTime - lastTime, var);
				}
			}
		});
		
		label = new JLabel("Starting...", SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(50.0f));
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(label, BorderLayout.CENTER);
		
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	public void addWindowListener(WindowListener listener) {
		frame.addWindowListener(listener);
	}
	
	private String generateID() {
		return Integer.toString(new Random().nextInt(10000));
	}
	
	private void updateLabel(String text) throws InvocationTargetException, InterruptedException {
		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				label.setText(text);
			}			
		});
	}
	
	private void writeToFile(
			String name, boolean[] stimuli, TrialResponse[] results) throws IOException {
		FileWriter fileWriter = new FileWriter(name);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(settings.getName());
	    printWriter.println(settings.getMaskTime());
	    printWriter.println(settings.getIntervalTime());
	    printWriter.println("---- DATA ----");
	    for (int i = 0; i < stimuli.length; i++) {
	    	printWriter.print(stimuli[i] ? 'O' : 'Q');
	    	printWriter.print(':');
	    	if (results[i] != null) {
	    		printWriter.print(results[i].getResponse());
	    		printWriter.print(':');
	    		printWriter.print(results[i].getResponseTime());
	    	} else {
	    		printWriter.print("null:null");
	    	}
	    	printWriter.println();
	    }
	    
	    printWriter.close();
	}
	
	@Override
	public Integer call() throws Exception {
		String id = generateID();
		
		this.currentTrial = 0;
		Thread.sleep(settings.getStartTime());
		updateLabel("");
		Thread.sleep(settings.getGoTime());
		
		/*
		int iTime = (int) settings.getIntervalTime() +
				(int) settings.getMaskTime();
		Timer swingTimer = new Timer(iTime, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isTrial = true;
				lastTime = System.currentTimeMillis();
				if (settings.getTrialValues()[currentTrial]) {
					label.setText("O");
				} else {
					label.setText("Q");
				}
			}
		});
		swingTimer.start();
		Thread.sleep(settings.getIntervalTime());
		*/
		
		for (int i = 0; i < settings.getTrials(); i++) {
			System.out.println("Trial " + currentTrial + " of " + settings.getTrials());
			
			// Start
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					isTrial = true;
					lastTime = System.currentTimeMillis();
					if (settings.getTrialValues()[currentTrial]) {
						label.setText("O");
					} else {
						label.setText("Q");
					}
				}	
			});
			// Wait
			Thread.sleep(settings.getIntervalTime());
			
			// End
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					isTrial = false;
					label.setText("");
				}			
			});
			Thread.sleep(settings.getMaskTime());
			currentTrial++;
		}
		
		System.out.println("Writing results to file " + id);
		try {
			writeToFile(id, settings.getTrialValues(), trialResponses);
			updateLabel("Done");
		} catch (Exception e) {
			System.out.println(e.toString());
			updateLabel("Finished with Errors");
			throw e;
		}
		
		return 0;
	}
}
