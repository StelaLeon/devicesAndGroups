package group.commands;

import group.DeviceGroup;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import device.interaction.Device;
import deviceTools.Main;

public class ListCommand implements GroupCommand {

	@Override
	public void execute(String[] params) {
		System.out.println(toString());

	}
	public String toString(){
		StringBuilder stringRepresentation = new StringBuilder();
		Iterator<?> groupKeys = Main.hierarchy.keySet().iterator();
		while(groupKeys.hasNext()){
			String root =  (String) groupKeys.next();
			stringRepresentation.append(Main.hierarchy.get(root).toString()).append("\n");
		}
		if(stringRepresentation.toString().isEmpty()){
			return "Sorry the architecture is empty";
		}
		return stringRepresentation.toString();
	}
	
}
