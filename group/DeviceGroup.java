package group;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import device.interaction.Device;

public class DeviceGroup {
	private String name;
	
	private Set<Device> devices; //no duplicates
		
	private Set<DeviceGroup> kids; //no duplicates
	
	public DeviceGroup(String name){
		this.name = name;
		this.devices = new HashSet<Device>();
		this.kids = new HashSet<DeviceGroup>();
	}
	
	public void addDevice(String groupName, Device device) throws NoDuplicatedDevicesException{
		if(devices.contains(device)){
			throw new NoDuplicatedDevicesException("You cannot add the device twice in the same hierarchy");
		}
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while(itUtil.hasNext()){
			itUtil.next().addDevice(groupName, device);
		}
		
		if(groupName.equals(name)){
			this.devices.add(device); //if it;s a duplicate the set won;t accept it;
			device.addGroup(this);
		}
	}
	
	public void deleteDevice(Device device){
		if(devices.contains(device) ){
			devices.remove(device);
			return; // no need to go further, the hierarchy can contain the device only once
		}
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while(itUtil.hasNext()){
			itUtil.next().deleteDevice(device);
		}
	}

	
	public void deleteGroup(DeviceGroup group){
		if(kids.contains(group) ){
			kids.remove(group);
			return; // no need to go further, the hierarchy can contain the device only once
		}
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while(itUtil.hasNext()){
			itUtil.next().deleteGroup(group);
		}
	}
	
	private void addGroup(DeviceGroup group) throws NoDuplicateGroupsException{
		this.kids.add(group);
	}
	
	/**
	 * adds a group to a specific group to the hierarchy, we have this method in case we are identifying the groups 
	 * by name and we need to search them into the hierarchy
	 * @param grupName
	 * @param group
	 * @throws NoDuplicateGroups 
	 */
	public void addGroupToGroup(String groupName, DeviceGroup group) throws NoDuplicateGroupsException{
		if(this.kids.contains(group)){
			/**
			 * I assumed that in the context that you cannot add a device twice in the hierarchy
			 * you cannot add a group twice in the hierarchy either.
			 * I see it as an inconsistency to be able to add a group twice because: 
			 * if you have a group with a device, and the group is found twice in the hierarchy => the device is found twice too,
			 * which makes the group different than the original one. 
			 */
			throw new NoDuplicateGroupsException("You cannot add the group twice in the same hierarchy");
		}

		Iterator<DeviceGroup> itUtil = kids.iterator();
		while(itUtil.hasNext()){
			itUtil.next().addGroupToGroup(groupName,group);
		}
		if(name.equals(groupName)){
			addGroup(group);
		}
	}
	
	/**
	 * removes a group from a specific group from the hierarchy, we have this method in case we are identifying the groups 
	 * by name and we need to search them into the hierarchy
	 * @param grupName
	 * @param group
	 */
	public void deleteGroupFromGroup(String groupName, DeviceGroup group){
		if(this.name.equals(groupName)){
			deleteGroup(group);
			return;
		}
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while(itUtil.hasNext()){
			itUtil.next().deleteGroupFromGroup(groupName,group);
		}
	}
	
	/**
	 * we search for the instance of the group in case the Group object is not identified only by the name
	 * @param nameToSearch
	 * @return Group
	 */
	public DeviceGroup getInstanceOfGroup(String nameToSearch){
		if (this.name.equals(nameToSearch)) {
			return this;
		}
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while (itUtil.hasNext()) {
			DeviceGroup kid = itUtil.next().getInstanceOfGroup(nameToSearch);
			if(kid!=null){
				return kid;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public Set<DeviceGroup> getKids() {
		return kids;
	}
	
	public String toString(){
		StringBuilder stringRepresentation = new StringBuilder(name);
		stringRepresentation.append("\t");
		Iterator<DeviceGroup> itUtil = this.kids.iterator();
		while(itUtil.hasNext()){
			stringRepresentation.append(itUtil.next().toString()).append("\t\t");
		}
		return stringRepresentation.toString();
	}
}
