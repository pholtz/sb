package org.butternut.sb.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frames
{
	public static void replaceFramePanel(JFrame frame, JPanel panel) {
		frame.getContentPane().removeAll();
		frame.setContentPane(panel);
		frame.pack();
	}
}
