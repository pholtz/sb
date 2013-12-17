package edu.ycp.ece220.rgb;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class sb {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create the Game object that represents the game state
				game mygame = null;
				try {
					mygame = new game();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Create the sbview that will visualize the game state
				sbview view = new sbview(mygame);

				// Create a frame (top-level window) to enclose the sbview
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Sweet Brown's Cold Pop Escape!");
				frame.setContentPane(view);
				frame.pack();
				
				// Start the game!
				view.startGame();
				
				// Make the frame visible
				frame.setVisible(true);
			}
		});
	}
}
