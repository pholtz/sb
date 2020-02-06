package org.butternut.sb;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.butternut.sb.io.KeyController;
import org.butternut.sb.io.MouseController;
import org.butternut.sb.time.TimestepController;

public class Application
{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Game game = Game.initialize();
				TimestepController controller = new TimestepController(game);
				MouseController mouseController = new MouseController(game);
				KeyController keyController = new KeyController(game);
				View view = new View(game,
						controller,
						mouseController,
						keyController);

				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Sweet Brown's Cold Pop Escape!");
				frame.setContentPane(view);
				frame.pack();
				frame.setVisible(true);
				
				view.startGame();
			}
		});
	}
}
