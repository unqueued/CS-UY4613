package starcraftbot.proxybot.bot;

import java.util.HashMap;

import starcraftbot.proxybot.Constants.Order;
import starcraftbot.proxybot.Constants.Race;
import starcraftbot.proxybot.Game;
import starcraftbot.proxybot.wmes.UnitTypeWME;
import starcraftbot.proxybot.wmes.UnitTypeWME.UnitType;
import starcraftbot.proxybot.wmes.unit.UnitWME;




/**
 * 
 *Kirsten's Build Order Bot
 *
 */
public class BuildBot implements StarCraftBot {
	
	boolean running = true;
	boolean paused = true;
	int barrackMax = 2;
	float marineP = 0.7f;
	float workerP = 0.3f;
	HashMap<Integer, Integer> unitNum = new HashMap<Integer, Integer>();
	
	
	public void setPaused(boolean _paused) {
		System.out.println(getName()+ "paused: "+ _paused);
		paused = _paused;
	}
	
	public void start(Game game) {
		
		while (running) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {}
			
			if(paused) continue;

			//count all units 
            updateUnitNumber(game);
			
			//if you have enough minerals you're running out of supply, build more supply! 
			if (game.getPlayer().getMinerals() >= 100 && 
					game.getPlayer().getSupplyUsed() >= (game.getPlayer().getSupplyTotal() - 4) ) {
				
				int supplyType = UnitTypeWME.getSupplyType(game.getPlayerRace());
				
				System.out.println("----Build farm");
				//build a farm near a worker!
				for (UnitWME unit : game.getPlayerUnits()) {
					if (unit.getIsWorker() && 
							unit.getOrder() != Order.ConstructingBuilding.ordinal() ) {
						
						// pick a random spot near the worker to build the farm
						game.getCommandQueue().build(unit.getID(), 
								unit.getX() + (int)(-10.0 + Math.random() * 20.0), 
								unit.getY() + (int)(-10.0 + Math.random() * 20.0), 
								supplyType);							
						break;
					}
				}
			}
			
			//if you have enough minerals and you have 0 barracks, build a barrack!
			if (game.getPlayer().getMinerals() >= 200 && game.getPlayer().getSupplyTotal()/2 >=12 ) {

				int barrackType = UnitTypeWME.Terran_Barracks;
				/*&& barrack doesn't already exist*/
				if( getUnitNumber(barrackType) < barrackMax ) {
				//find a worker to build the barrack
				System.out.println("----Build barrack, supply: "+game.getPlayer().getSupplyTotal()/2);
					for (UnitWME unit : game.getPlayerUnits()) {
						if (unit.getIsWorker() && 
							unit.getOrder() != Order.ConstructingBuilding.ordinal() ) {
							
							// build barrack near that worker
							game.getCommandQueue().build(unit.getID(), 
									unit.getX() + (int)(-10.0 + Math.random() * 20.0), 
									unit.getY() + (int)(-10.0 + Math.random() * 20.0), 
									barrackType);							
							break;
						}
					}
					
				}
				
			}

			// build more workers
			if (game.getPlayer().getMinerals() >= 50 &&
					getUnitNumber( UnitTypeWME.Terran_SCV ) < game.getPlayer().getSupplyTotal()/2*workerP ||
					getUnitNumber( UnitTypeWME.Terran_SCV ) < 10 ) {
				int workerType = UnitTypeWME.getWorkerType(game.getPlayerRace());
				int centerType = UnitTypeWME.getCenterType(game.getPlayerRace());

				for (UnitWME unit : game.getPlayerUnits()) {
					if (unit.getTypeID() == centerType) {
						game.getCommandQueue().train(unit.getID(), workerType);
					}
				}
			}


			if( getUnitNumber( UnitTypeWME.Terran_Marine ) < game.getPlayer().getSupplyTotal()/2*marineP ) {
				System.out.println("----Build marine");
				float need = game.getPlayer().getSupplyTotal()/2*marineP - getUnitNumber( UnitTypeWME.Terran_Marine );
				for (UnitWME unit : game.getPlayerUnits()) {
					if (unit.getTypeID() == UnitTypeWME.Terran_Barracks) {
						game.getCommandQueue().train(unit.getID(), UnitTypeWME.Terran_Marine);
						if (need<2) break;
					}
				}
			}
			
			
		}
	}
	
	void updateUnitNumber(Game game){
		unitNum.clear();
		for(UnitWME unit : game.getPlayerUnits()){
			int typeId = unit.getTypeID();
			int num = unitNum.containsKey(typeId)? unitNum.get(typeId) : 0;
			unitNum.put(typeId, num+1);
		}
	}
	int getUnitNumber(int typeId){
		return unitNum.containsKey(typeId)? unitNum.get(typeId) : 0;
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "BuildBot";
	}
	

}
