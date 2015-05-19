package deviceTools;

import group.Group;

import java.util.HashMap;
import java.util.Map;

import device.interaction.Device;
import device.interaction.DeviceController;
import device.interaction.Meaning;

public final class Main {

	public static Map<String, Group> hierarchy =  new HashMap<String,Group>();
	public static Device deviceTest; 
	
	public static void main(String[] args) {
		deviceTest = new Device("deviceOne");
		deviceTest.setMeaning(Meaning.Humidity);
		
		Thread userInput = new Thread( new UserCommandLine());
		userInput.start();
	}

}
