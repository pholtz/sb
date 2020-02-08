package org.butternut.sb.swing;

import javax.swing.JPanel;

public class Panels
{
	public static void requestFocus(JPanel panel) {
	    panel.setFocusable(true);
	    panel.requestFocusInWindow();
	}
}
