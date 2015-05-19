package group;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import device.interaction.Device;
import deviceTools.Main;

public class GroupHelper {
	/**
	 * @TODO to be transformed into potential commands
	 */
	/**
	 * query to get a list of groups the device belongs to.
	 * @param device
	 * @return list of groups the device belongs to
	 */
	public List<Group> getGroupsOfDevice(String hierarchy, Device device){
		return device.groups;
	}
	
	/**
	 * query to get a list of devices group owns. 
	 * @param groupName
	 * @return list of devices group owns
	 */
	public Set<Device> getDevicesOfGroup(String groupName){
		Set<Device> devices = Collections.emptySet();
		Iterator<?> groupIt = Main.hierarchy.entrySet().iterator();
		while(groupIt.hasNext()){
			Group group = ((Group) groupIt.next()).getInstanceOfGroup(groupName);
			devices.addAll(group.getDevices());
		}
		return devices;
	}

		public void deleteDeviceFromGroup(String hierarchy, String groupRoot, Device device) {
			Main.hierarchy.get(hierarchy).deleteDevice(device);
		}
}
