package sart;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runUI();
            }
        });
	}
	
	public static void runUI() {
		JFrame frame;
		frame = new JFrame();
		frame.setTitle("Experiment Manager");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Disposing Main Event Manager");
			}
		});
		
		JPanel centerPanel = new JPanel();
		centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		ExperimentSettings[] settings = {
				new ExperimentSettings("Experiment 1", 500, 0.3, 20),
				new ExperimentSettings("Experiment 2", 1000, 0.3, 20),
				new ExperimentSettings("Experiment 3", 2000, 0.3, 20),
				new ExperimentSettings("Experiment 4", 5000, 0.3, 20),
		};
		
		JButton[] buttons = new JButton[settings.length];
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < buttons.length; i++) {
					if (event.getSource() == buttons[i]) {
						Experiment exp = new Experiment(settings[i]);
						/*
						try {
							exp.runExperiment();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						*/
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
