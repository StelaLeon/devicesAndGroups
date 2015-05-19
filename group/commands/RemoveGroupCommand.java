package group.commands;

import group.DeviceGroup;

import java.util.Iterator;

import deviceTools.Main;

public class RemoveGroupCommand implements GroupCommand {

	@Override
	public void execute(String[] params){
		if(params.length != 2){
			System.out.println("usage: remove group <name of the group you want to remove>");
		}
		deleteGroup(params[2]);
	}
	
	public void deleteGroup(String groupName) {
		Iterator<?> groupIt = Main.hierarchy.entrySet().iterator();
		while(groupIt.hasNext()){
			DeviceGroup root = (DeviceGroup) groupIt.next();
			DeviceGroup groupToBeDeleted = root.getInstanceOfGroup(groupName);
			root.deleteGroup(groupToBeDeleted);
		}
	}

	public void deleteKidOfGroup(String root, String groupName, String kidName) {
		DeviceGroup groupToBeDeleted = Main.hierarchy.get(root).getInstanceOfGroup(kidName);
		Main.hierarchy.get(root).deleteGroupFromGroup(groupName, groupToBeDeleted);
	}
}
