package org.butternut.sb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.butternut.sb.input.KeyController;
import org.butternut.sb.input.MouseController;
import org.butternut.sb.state.StateController;

public class Application
{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Game game = Game.initialize();
				StateController stateController = new StateController(game);
				MouseController mouseController = new MouseController(game);
				KeyController keyController = new KeyController(game);
				View view = new View(game,
						mouseController,
						keyController);

				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Sweet Brown's Cold Pop Escape!");
				frame.setContentPane(view);
				frame.pack();
				frame.setVisible(true);

				Timer timer = new Timer(1000 / 60, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						game.timerTick();
						stateController.processTimestep();
						view.repaint();
					}
				});
				timer.start();
			}
		});
	}
}
