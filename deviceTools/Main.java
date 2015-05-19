package deviceTools;

import group.Group;

import java.util.HashMap;
import java.util.Map;

import device.interaction.Device;
import device.interaction.DeviceController;
import device.interaction.Meaning;

public final class Main {

	public static Map<String, Group> hierarchy =  new HashMap<String,Group>();
	
	public static void main(String[] args) {
		Device deviceOne = new Device("deviceOne");
		deviceOne.setMeaning(Meaning.Humidity);
		DeviceController deviceController= new DeviceController(deviceOne);
		
		Thread userInput = new Thread( new UserCommandLine(deviceController));
		userInput.start();
	}

}
