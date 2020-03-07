package sart;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main implements Runnable {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Main());
	}
	
	public void run() {
		ExecutorService service = Executors.newCachedThreadPool();
		
		JFrame frame;
		frame = new JFrame();
		frame.setTitle("Experiment Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Disposing Main Event Manager");
				try {
					service.awaitTermination(10, TimeUnit.SECONDS);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JPanel centerPanel = new JPanel();
		centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		// Start Time / Go Time / Mask Time / Interval Time
		ExperimentSettings[] settings = {
				new ExperimentSettings("Experiment 1", 5000, 2000, 500, 900, 0.3, 30),
				new ExperimentSettings("Experiment 2", 5000, 2000, 2000, 900, 0.3, 30),
				new ExperimentSettings("Experiment 3", 5000, 2000, 4000, 900, 0.3, 30),
				new ExperimentSettings("Experiment 4", 5000, 2000, 8000, 900, 0.3, 30),
		};
		
		JButton[] buttons = new JButton[settings.length];
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < buttons.length; i++) {
					if (event.getSource() == buttons[i]) {
						Experiment exp = new Experiment(settings[i]);
						Future<?> result = service.submit(exp);
						WindowListener listener = new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								System.out.println("Disposing Client Experiment Manager");
								result.cancel(true);
							}
						};
						exp.addWindowListener(listener);
					}
				}
			}
		};
		for (int i = 0; i < settings.length; i++) {
			buttons[i] = new JButton(settings[i].getName());
			buttons[i].addActionListener(actionListener);
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		
		centerPanel.add(Box.createVerticalStrut(10));
		for (int i = 0; i < buttons.length; i++) {
			centerPanel.add(buttons[i]);
			centerPanel.add(Box.createVerticalStrut(5));
		}
		centerPanel.add(Box.createVerticalStrut(10));
		
		frame.getContentPane().setPreferredSize(new Dimension(400, 150));
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
