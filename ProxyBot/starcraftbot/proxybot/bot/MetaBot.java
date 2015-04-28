package starcraftbot.proxybot.bot;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

import starcraftbot.proxybot.Game;
/**
 * Empty implementation of the StarCraft bot interface.
 */
public class MetaBot implements StarCraftBot {
	
	private JFrame frame;
	
	public void start(Game game) {
		System.out.println("I are Metabot");
		
		frame = new JFrame("Game Speed");
		//frame.add(this);
		frame.add(new JLabel("I are metabot"));
		frame.pack();
		frame.setPreferredSize(new Dimension(512, 512));
		frame.setLocation(320, 0);
		frame.setVisible(true);		
	}

	public void stop() {
	}
}
