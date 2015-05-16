// From Tim

package starcraftbot.proxybot.bot;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import starcraftbot.proxybot.BotControl;
import starcraftbot.proxybot.Game;
import starcraftbot.proxybot.Constants.Order;
import starcraftbot.proxybot.Constants.Race;
import starcraftbot.proxybot.wmes.UnitTypeWME;
import starcraftbot.proxybot.wmes.UnitTypeWME.UnitType;
import starcraftbot.proxybot.wmes.unit.UnitWME;
/**
 * Empty implementation of the StarCraft bot interface.
 * 
 * NOTE
 * This is where the selection gui is going to be, as well as
 * the selection logic
 * 
 */
public class MetaBot implements StarCraftBot {
	
	private JFrame frame;
	
	boolean running = true;
	FarmBot farmBot = new FarmBot();
	NullStarCraftBot nullStarCraftBot = new NullStarCraftBot();
	
	public void start(Game game) {
		//System.out.println("I are Metabot");
		
		/*
		frame = new JFrame("Game Speed");
		//frame.add(this);
		frame.add(new JLabel("I are metabot"));
		frame.pack();
		frame.setPreferredSize(new Dimension(512, 512));
		frame.setLocation(320, 0);
		frame.setVisible(true);
		*/
		
		BotControl botControl = new BotControl();
		
		frame = new JFrame();
		frame.add(botControl);
		frame.pack();
		frame.setVisible(true);
		
		// Create bots
		
		// Set up event listeners
		JList botList = botControl.getBotList();
		botList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            	JList jList = (JList) evt.getSource();
            	
            	if(contains(jList.getSelectedIndices(), 0)) {
            		farmBot.start(game);
            	} else {
            		farmBot.stop();
            	}
            }
        });
	}

	public void stop() {
		running = false;
	}

	// Kludge, I know
    public boolean contains(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
}
