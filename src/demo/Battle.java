package demo;

import java.util.ArrayList;
import java.util.List;

public class Battle {
	
	private int player1Targets = 0;
	private int player2Targets = 0;
	
	public Battle () {
		;
	}
	
	public void doBattle(Territory territory) {
		List<Fleet> player1 = new ArrayList<Fleet>();
		List<Fleet> player2 = new ArrayList<Fleet>();
		if (territory.getFleets().size() < 2) {
			throw new IllegalArgumentException("Needs to be 2 or more fleets in a territory for a battle to occur");
		}
		player1.add(territory.getFleets().get(0));
		player1Targets = territory.getFleets().get(0).targets();
		for (int i = 1; i < territory.getFleets().size(); i++) {
			if (territory.getFleets().get(i).getOwner() != player1.get(0).getOwner()) {
				player2.add(territory.getFleet(i));
				player2Targets = player2Targets + territory.getFleet(i).targets();
			} else {
				player1.add(territory.getFleet(i));
				player1Targets = player1Targets + territory.getFleet(i).targets();
			}
		}
		
		// Who will be hit? see Fleet.takeDamage() for requirements
		for (int i = 0; i < mergeAttacks(player1, 1).size(); i++) {
			
		}
		
		// KRIGA med mergeAttacks(player1) vs mergeAttacks(player2)


		territory.controlledBy(
				//winner of the battle!
				);
	}
	
	private List<Integer> mergeAttacks(List<Fleet> playerFleets, int initiative) {
		List<Integer> playerAttacks = new ArrayList<Integer>();
		for (int fleetIndex = 0; fleetIndex < playerFleets.size(); fleetIndex++) {
			for (int j = 0; j < playerFleets.get(fleetIndex).getAttacks(initiative).size(); j++) {
				playerAttacks.add(playerFleets.get(fleetIndex).getAttacks(initiative).get(j));
			}
		}
		return playerAttacks;
	}

}
