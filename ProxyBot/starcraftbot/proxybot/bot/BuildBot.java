package starcraftbot.proxybot.bot;

import java.util.HashMap;

import starcraftbot.proxybot.Constants.Order;
import starcraftbot.proxybot.Game;
import starcraftbot.proxybot.wmes.UnitTypeWME;
import starcraftbot.proxybot.wmes.unit.UnitWME;




/**
 * 
 *Kirsten's Build Order Bot
 *
 */
public class BuildBot implements StarCraftBot {
	
	boolean running = true;
	int barrackMax = 1;
	int marineMax = 5;

	HashMap<Integer, Integer> unitNum = new HashMap<Integer, Integer>();
	public void start(Game game) {
		
		while (running) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {}

			//count all units 
            updateUnitNumber(game);
			
			//if you have enough minerals you're running out of supply, build more supply! 
			if (game.getPlayer().getMinerals() >= 100 && 
					game.getPlayer().getSupplyUsed() >= (game.getPlayer().getSupplyTotal() - 2) ) {
				
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
			else if (game.getPlayer().getMinerals() >= 150 ) {

				int barrackType = UnitTypeWME.Terran_Barracks;
				/*&& barrack doesn't already exist*/
				if( getUnitNumber(barrackType) < barrackMax ) {
				//find a worker to build the barrack
				System.out.println("----Build barrack");
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
				
				if( getUnitNumber( UnitTypeWME.Terran_Marine ) < barrackMax ) {
                System.out.println("----Build marine");
					for (UnitWME unit : game.getPlayerUnits()) {
						if (unit.getTypeID() == UnitTypeWME.Terran_Barracks) {
							game.getCommandQueue().train(unit.getID(), UnitTypeWME.Terran_Marine);
							break;
						}
					}
				}
			}
			
		}
	}
	
	void updateUnitNumber(Game game){
//		for(int typeId : UnitTypeWME.getUnitTypeMap().keySet() ){
//			unitNum.put(typeId, 0);
//		}
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

	

}
