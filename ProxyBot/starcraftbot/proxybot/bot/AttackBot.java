package starcraftbot.proxybot.bot;

import starcraftbot.proxybot.Constants.Order;
import starcraftbot.proxybot.Game;
import starcraftbot.proxybot.wmes.UnitTypeWME;
import starcraftbot.proxybot.wmes.UnitTypeWME.UnitType;
import starcraftbot.proxybot.wmes.unit.UnitWME;

public class AttackBot implements StarCraftBot {
	boolean running = true;
	//double attackRange = 30000;
	double attackRange = 30;
	int workerType = UnitType.Terran_SCV.ordinal();
	
	boolean paused = true;
	
	public void setPaused(boolean _paused) {
		paused = _paused;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public AttackBot() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Game game) {
		// TODO Auto-generated method stub
		while (running) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {}
			
			// Check to see if we're paused or not
			if(paused)
				continue;
			
			for (UnitWME unit : game.getPlayerUnits()) {
				if (unit.getTypeID() == workerType ){
				//	continue;
				}
				if (unit.getOrder() == Order.PlayerGuard.ordinal()) {

					int patchID = -1;
					double closest = Double.MAX_VALUE;
					
                    //if( game.getEnemyUnits().isEmpty()) System.out.println("No enemy found!");
					for (UnitWME enemy : game.getEnemyUnits()) {
						double dist = unit.distance(enemy);
//						double dx = unit.getX() - enemy.getX();
//						double dy = unit.getY() - enemy.getY();
//						double dist = Math.sqrt(dx*dx + dy*dy); 
			
						if (dist < closest) {
							patchID = enemy.getID();
							closest = dist;
						}
					}					
					//if(closest!=Double.MAX_VALUE) System.out.println("Enemy distance: "+ closest);
					if (patchID != -1 && closest < attackRange) {
						game.getCommandQueue().rightClick(unit.getID(), patchID);
					}
				}
			}
        }

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		running = false;

	}

}
