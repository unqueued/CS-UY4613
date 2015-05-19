// From Tim

package starcraftbot.proxybot.bot;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import starcraftbot.proxybot.BotControl;
import starcraftbot.proxybot.Game;
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

	ArrayList<StarCraftBot> botList = new ArrayList<StarCraftBot>();
	ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
	
	public MetaBot(){
        StarCraftBot farmBot = new FarmBot();
        StarCraftBot attackBot = new AttackBot();
        StarCraftBot buildBot = new BuildBot();
		botList.add(farmBot);
		botList.add(attackBot);
		botList.add(buildBot);
		
		
	}
	
	void initUI(Game game){
		frame = new JFrame();
		frame.setLayout(new FlowLayout());

		for(StarCraftBot bot : botList){
			System.out.println(bot.getName());
			JCheckBox checkbox = new JCheckBox(bot.getName());
			checkbox.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox c = (JCheckBox) e.getItem();
					int index = checkboxList.indexOf(c);
					StarCraftBot the_bot = botList.get(index);
					the_bot.setPaused( !c.isSelected() );
				}
			});
			
			frame.add(checkbox);
			checkboxList.add(checkbox);
			
			new Thread() {
				public void run() {
    				bot.start(game);
				}
			}.start();
			
		}
		
		BotControl botControl = new BotControl();
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void start(Game game) {
		initUI(game);
		
	}

	public void stop() {
		for(StarCraftBot bot: botList){
			bot.stop();
		}
	}


    
    public static void main(String[] args){
    	MetaBot bot = new MetaBot();
    	bot.start(null);
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MetaBot";
	}
}
