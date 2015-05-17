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
				if (unit.getIsWorker() ){
					//continue;
				}
				if (unit.getOrder() == Order.PlayerGuard.ordinal()) {

					int patchID = -1;
					double closest = Double.MAX_VALUE;
					UnitWME target;
					
                    //if( game.getEnemyUnits().isEmpty()) System.out.println("No enemy found!");
					for (UnitWME enemy : game.getEnemyUnits()) {
						double dist = unit.distance(enemy);
						if (dist < closest) {
							patchID = enemy.getID();
							closest = dist;
							target = enemy;
						}
					}					
					if ( closest < attackRange) {
						game.getCommandQueue().attackUnit(unit.getID(), patchID);
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
