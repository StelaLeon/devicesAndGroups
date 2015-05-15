package deviceTools;

import java.util.List;
import java.util.Set;

public interface IGroupCommand {

	public void addGroupToGroup(String groupParent, String groupToAdd) throws NoDuplicateGroupsException ;
	
	public void addDeviceToGroup(String groupName, Device device) throws NoDuplicatedDevicesException;
	
	public List<Group> getGroupsOfDevice(Device device);
	
	public Set<Device> getDevicesOfGroup(String groupName);
	
	public void deleteGroup(String groupName);
	
	public void deleteKidOfGroup(String groupName, String kidName );
	
	public void deleteDeviceFromGroup(String group, Device device);
	
}
