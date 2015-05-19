package group;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mainTools.Main;
import device.interaction.Device;

public abstract class GroupHelper {
	/**
	 * @TODO to be transformed into potential commands
	 */
	/**
	 * query to get a list of groups the device belongs to.
	 * 
	 * @param device
	 * @return list of groups the device belongs to
	 */
	public static List<DeviceGroup> getGroupsOfDevice(String hierarchy,	Device device) {
		return device.groups;
	}

	/**
	 * query to get a list of devices group owns.
	 * 
	 * @param groupName
	 * @return list of devices group owns
	 */
	public static Set<Device> getDevicesOfGroup(String groupName) {
		Set<Device> devices = Collections.emptySet();
		Iterator<?> groupIt = Main.hierarchy.entrySet().iterator();
		while (groupIt.hasNext()) {
			DeviceGroup group = ((DeviceGroup) groupIt.next())
					.getInstanceOfGroup(groupName);
			devices.addAll(group.getDevices());
		}
		return devices;
	}

	public static void deleteDeviceFromGroup(String hierarchy,String groupRoot, Device device) {
		Main.hierarchy.get(hierarchy).deleteDevice(device);
	}
}
