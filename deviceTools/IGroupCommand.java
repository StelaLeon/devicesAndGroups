package deviceTools;

import java.util.List;
import java.util.Set;

public interface IGroupCommand {

	public void addGroupToGroup(String hierarchy, String groupParent, String groupToAdd) throws NoDuplicateGroupsException ;
	
	public void addDeviceToGroup(String hierarchy, String groupName, Device device) throws NoDuplicatedDevicesException;
	
	public List<Group> getGroupsOfDevice(String hierarchy, Device device);
	
	public Set<Device> getDevicesOfGroup(String groupName);
	
	public void deleteGroup(String groupName);
	
	public void deleteKidOfGroup(String hierarchy, String groupName, String kidName );
	
	public void deleteDeviceFromGroup(String hierarchy, String group, Device device);
	
	public void addHierarchy(String rootHierarchyName);
	
}
