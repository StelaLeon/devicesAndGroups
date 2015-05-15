package deviceTools;

import java.util.List;
import java.util.Set;

public class Architecture implements IGroupCommand{

	private Group root;
	/**
	 * make sure this is the only entry point for adding things 
	 */

	public void addGroupToGroup(String groupParent, String groupToAdd) throws NoDuplicateGroupsException{
		Group group = root.getInstanceOfGroup(groupParent);
		this.root.addGroupToGroup(groupParent, group);
	} 
	
	public void addDeviceToGroup(String groupName, Device device) throws NoDuplicatedDevicesException{
		this.root.addDevice(groupName, device);
	}
	
	/**
	 * query to get a list of groups the device belongs to.
	 * @param device
	 * @return list of groups the device belongs to
	 */
	public List<Group> getGroupsOfDevice(Device device){
		return device.groups;
	}
	
	/**
	 * query to get a list of devices group owns. 
	 * @param groupName
	 * @return list of devices group owns
	 */
	public Set<Device> getDevicesOfGroup(String groupName){
		Group group = root.getInstanceOfGroup(groupName);
		return group.getDevices();
	}
/**
 * @TODO implement group deletion and of course UNIT TESTS
 */
	@Override
	public void deleteGroup(String groupName) {
		Group groupToBeDeleted = root.getInstanceOfGroup(groupName);
		
		
	}

	@Override
	public void deleteKidOfGroup(String groupName, String kidName) {
		Group groupToBeDeleted = root.getInstanceOfGroup(kidName);
		root.deleteGroupFromGroup(groupName, groupToBeDeleted);
	}

	@Override
	public void deleteDeviceFromGroup(String groupRoot, Device device) {
		this.root.deleteDevice(device);
	}
}
