package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.ButtonEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.container.impl.Equipment;

public class AttackButtonEventHandler implements EventHandler<ButtonEvent> {

	@Override
	public void handleEvent(ButtonEvent event, Player player) throws Exception {
		/*
		 * The weapon id
		 */
		int id = player.getEquipment().getId(Equipment.SLOT_WEAPON);
		
		/*
		 * Get the weapon interface
		 */
		WeaponInterface weaponInterface = WeaponInterface.get(id);
		
		/*
		 * Setting the attack index and such
		 */
		for (int index = 0; index < weaponInterface.getOptions().length; index++) {
			if (weaponInterface.getOption(index).getId() == event.getIndex()) {
				player.setAttackIndex(index);
			}
		}
	}

}