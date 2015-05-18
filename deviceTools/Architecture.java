package deviceTools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Architecture implements IGroupCommand{

	private Map<String, Group> hierarchies;
	/**
	 * make sure this is the only entry point for adding things 
	 */

	public Architecture(){
		this.hierarchies = new HashMap<String,Group>();
	}
	
	public void addHierarchy(String rootHierarchyName){
		Group root = new Group(rootHierarchyName);
		this.hierarchies.put(rootHierarchyName, root);
	}
	
	public Group getHierarchyRoot(String rootName){
		return this.hierarchies.remove(rootName);
	}
	
	public void addGroupToGroup(String hierarchy, String groupParent, String groupToAdd) throws NoDuplicateGroupsException{
		Group root = this.getHierarchyRoot(hierarchy);
		Group group = root.getInstanceOfGroup(groupToAdd);
		if(group==null){
			group = new Group(groupToAdd);
		}
		root.addGroupToGroup(groupParent, group);
	} 
	
	public void addDeviceToGroup(String hierarchy, String groupName, Device device) throws NoDuplicatedDevicesException{
		this.getHierarchyRoot(hierarchy).addDevice(groupName, device);
	}
	
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
		Iterator<?> groupIt = this.hierarchies.entrySet().iterator();
		while(groupIt.hasNext()){
			Group group = ((Group) groupIt.next()).getInstanceOfGroup(groupName);
			devices.addAll(group.getDevices());
		}
		return devices;
	}

	@Override
	public void deleteGroup(String groupName) {
		Iterator<?> groupIt = this.hierarchies.entrySet().iterator();
		while(groupIt.hasNext()){
			Group root = (Group) groupIt.next();
			Group groupToBeDeleted = root.getInstanceOfGroup(groupName);
			root.deleteGroup(groupToBeDeleted);
		}
	}

	@Override
	public void deleteKidOfGroup(String hierarchy, String groupName, String kidName) {
		Group groupToBeDeleted = this.hierarchies.get(hierarchy).getInstanceOfGroup(kidName);
		this.hierarchies.get(hierarchy).deleteGroupFromGroup(groupName, groupToBeDeleted);
	}

	@Override
	public void deleteDeviceFromGroup(String hierarchy, String groupRoot, Device device) {
		this.hierarchies.get(hierarchy).deleteDevice(device);
	}
	
	public String toString(){
		StringBuilder stringRepresentation = new StringBuilder();
		Iterator<?> groupKeys = this.hierarchies.keySet().iterator();
		while(groupKeys.hasNext()){
			String root =  (String) groupKeys.next();
			stringRepresentation.append(this.hierarchies.get(root).toString()).append("\n\t");
		}
		if(stringRepresentation.toString().isEmpty()){
			return "Sorry the architecture is empty";
		}
		return stringRepresentation.toString();
	}
}
