package org.butternut.sb;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Application
{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Game game = Game.initialize();
				
				View view = new View(game);

				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Sweet Brown's Cold Pop Escape!");
				frame.setContentPane(view);
				frame.pack();
				
				view.startGame();

				frame.setVisible(true);
			}
		});
	}
}
