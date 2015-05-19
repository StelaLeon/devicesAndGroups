package group.commands;

import group.NoDuplicatedDevicesException;
import device.interaction.Device;
import deviceTools.Main;

public class AddDeviceToGroupCommand implements GroupCommand {

	@Override
	public void execute(String command, String[] params) {
		if(params.length < 4){
			System.out.println("usage: add device <root> <group> <name of the device>");
		}
		try {
			Main.hierarchy.get(params[2]).addDevice(params[3], new Device(params[4]));
		} catch (NoDuplicatedDevicesException e) {
			System.out.println("You are not allowed to add the device twice to the hierarchy.");
		}
	}

}
