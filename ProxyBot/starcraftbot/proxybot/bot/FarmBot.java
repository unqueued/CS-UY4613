package starcraftbot.proxybot.bot;

import starcraftbot.proxybot.Game;
import starcraftbot.proxybot.Constants.Order;
import starcraftbot.proxybot.Constants.Race;
import starcraftbot.proxybot.wmes.UnitTypeWME;
import starcraftbot.proxybot.wmes.UnitTypeWME.UnitType;
import starcraftbot.proxybot.wmes.unit.UnitWME;
/**
 * Example implementation of the StarCraftBot.
 * 
 * This build will tell workers to mine, build additional workers,
 * and build additional supply units.
 */
public class FarmBot implements StarCraftBot {

	/** specifies that the agent is running */
	boolean running = true;
	boolean paused = true;
	
	public void setPaused(boolean _paused) {
		System.out.println(getName()+ "paused: "+ _paused);
		paused = _paused;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	/**
	 * Starts the bot.
	 * 
	 * The bot is now the owner of the current thread.
	 */
	public void start(Game game) {
		
		//System.out.println("FarmBot starting");
		
		// run until told to exit
		while (running) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {}
			
			// Check to see if we're paused or not
			if(paused)
				continue;
			
			// start mining
			for (UnitWME unit : game.getPlayerUnits()) {
				if ( unit.getIsWorker() && unit.getOrder() == Order.PlayerGuard.ordinal()) {

					int patchID = -1;
					double closest = Double.MAX_VALUE;
					
					for (UnitWME minerals : game.getMinerals()) {
						double dx = unit.getX() - minerals.getX();
						double dy = unit.getY() - minerals.getY();
						double dist = Math.sqrt(dx*dx + dy*dy); 
			
						if (dist < closest) {
							patchID = minerals.getID();
							closest = dist;
						}
					}					
					
					if (patchID != -1) {
						game.getCommandQueue().rightClick(unit.getID(), patchID);
					}
				}				
			}		
			
			
		}
	}

	/**
	 * Tell the main thread to quit.
	 */
	public void stop() {
		running = false;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "FarmBot";
	}

}
