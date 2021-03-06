package test;

import static org.junit.Assert.fail;
import group.DeviceGroup;
import group.NoDuplicateGroupsException;
import group.NoDuplicatedDevicesException;

import org.junit.Assert;
import org.junit.Test;

import device.interaction.Device;

public class GroupUnitTest {

	@Test
	public void testAddDeviceToGroup() throws Exception {
		String groupName = "BerlinGroup";
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		Device device = new Device("device1");
		
		berlinGroup.addDevice(groupName, device);
		
		Assert.assertTrue(device.getGroups().contains(berlinGroup)); //make sure the device is updated with the group too
		Assert.assertTrue(berlinGroup.getDevices().contains(device));
	}
	
	@Test 
	public void testAddDeviceToGroupHierarchy() throws NoDuplicatedDevicesException, NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		Device device = new Device("device1");
		
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);

		berlinGroup.addGroupToGroup(groupName,berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
		
		berlinGroup.addDevice(groupZone2Name, device);
		
		Assert.assertTrue(berlinZone2.getDevices().contains(device));
		Assert.assertFalse(berlinZone1.getDevices().contains(device));
		Assert.assertFalse(berlinGroup.getDevices().contains(device));
		Assert.assertTrue(device.getGroups().contains(berlinZone2));
	}

	/**
	 * test if it fails to add a device twice to the same node
	 * @throws NoDuplicateGroupsException
	 */
	@Test
	public void testAddDeviceTwiceToTheSameGroup() throws NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		Device device = new Device("device1");
		
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);

		berlinGroup.addGroupToGroup(groupName,berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
		
		try {
			berlinGroup.addDevice(groupZone2Name, device);
		} catch (NoDuplicatedDevicesException e) {
			fail("it shouldn't throw an exception the first time when you add a device");
		}
		
		Assert.assertTrue(berlinZone2.getDevices().contains(device));
		Assert.assertFalse(berlinZone1.getDevices().contains(device));
		Assert.assertFalse(berlinGroup.getDevices().contains(device));
		
		//now lets try to add it another time we expect an exception
		try {
			berlinGroup.addDevice(groupZone2Name, device);
			fail("you shouldn't be allowed to add a device twice in the hierarchy");
		} catch (NoDuplicatedDevicesException e) {
			Assert.assertEquals(e.getMessage(), "You cannot add the device twice in the same hierarchy");
		}
	}
	
	/**
	 * test if you can add the same device twice to the group verifying the case when you add it first to the leaf
	 * then closer to the root
	 * @throws NoDuplicateGroupsException
	 */
	@Test
	public void testAddDeviceTwiceToGroupHierarchy() throws NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		Device device = new Device("device1");
		
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);

		berlinGroup.addGroupToGroup(groupName,berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
		
		try {
			berlinGroup.addDevice(groupZone2Name, device);
		} catch (NoDuplicatedDevicesException e) {
			fail("it shouldn't throw an exception the first time when you add a device");
		}
		
		Assert.assertTrue(berlinZone2.getDevices().contains(device));
		Assert.assertFalse(berlinZone1.getDevices().contains(device));
		Assert.assertFalse(berlinGroup.getDevices().contains(device));
		
		//now lets try to add it another time we expect an exception
		try {
			berlinGroup.addDevice(groupZone1Name, device);
			fail("you shouldn't be allowed to add a device twice in the hierarchy");
		} catch (NoDuplicatedDevicesException e) {
			Assert.assertEquals(e.getMessage(), "You cannot add the device twice in the same hierarchy");
			// we verify that it didn't added, yet throwing the exception too
			Assert.assertFalse(berlinZone1.getDevices().contains(device));
		}
	}
	
	@Test
	public void testDeleteDeviceFromGroup() throws NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		berlinGroup.addGroupToGroup(groupName, berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name, berlinZone2);
		
		Device device = new Device("device1");

		DeviceGroup tmpZone2 = berlinGroup.getInstanceOfGroup(groupZone2Name);
		Assert.assertNotNull(tmpZone2);
		
		Assert.assertEquals(groupZone2Name, tmpZone2.getName());
		
		try {
			berlinGroup.addDevice(groupZone2Name, device);
		} catch (NoDuplicatedDevicesException e) {
			fail("it shouldn't throw an exception the first time when you add a device");
		}
		
		Assert.assertTrue(berlinZone2.getDevices().contains(device));
		
		berlinGroup.deleteDevice(device);
		
		Assert.assertFalse(berlinZone2.getDevices().contains(device));

	}
	
	/**
	 * add a group in the hierarchy tests
	 * 
	 * test if it actually throwing an exception when it tries to add the same group twice to the same group
	 * we shouldn;t rely on the fact that we formed a set and that cannot have duplicates, we need a feedback to the caller
	 * @throws NoDuplicateGroupsException 
	 */
	@Test
	public void testAddGroupToHierarchyTwiceToTheSameGroup(){
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		try {
			berlinGroup.addGroupToGroup(groupName,berlinZone1);
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		try {
			berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		try {
			berlinGroup.addGroupToGroup(groupZone1Name, berlinZone2);
			fail("You should NOT be able to add a group twice to the hierarchy");
		} catch (NoDuplicateGroupsException e) {
			Assert.assertEquals(e.getMessage(),"You cannot add the group twice in the same hierarchy");
		}

	}
	/**
	 * test if it verifies the entire tree by adding it near the root node then to a leaf
	 */
	@Test
	public void testAddGroupToHierarchyTwiceToTheHierarchy(){
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		try {
			berlinGroup.addGroupToGroup(groupName,berlinZone1);
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		
		try {
			berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
			Assert.assertTrue(berlinZone1.getKids().contains(berlinZone2));
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		try {
			berlinGroup.addGroupToGroup(groupZone2Name, berlinZone2);
			fail("You should NOT be able to add a group twice to the hierarchy");
		} catch (NoDuplicateGroupsException e) {
			Assert.assertEquals(e.getMessage(),"You cannot add the group twice in the same hierarchy");
		}

	}
	/**
	 * test if it verifies the entire tree by adding the group to a leaf and then somewhere near the root node 
	 */
	@Test
	public void testAddGroupToHierarchyTwiceToTheHierarchy2(){
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		try {
			berlinGroup.addGroupToGroup(groupName,berlinZone1);
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		
		try {
			berlinGroup.addGroupToGroup(groupZone1Name,berlinZone2);
			Assert.assertTrue(berlinZone1.getKids().contains(berlinZone2));
		} catch (NoDuplicateGroupsException e) {
			fail("You should be able to add a group at this point");
		}
		
		try {
			berlinGroup.addGroupToGroup(groupName,berlinZone2);
			fail("You should NOT be able to add a group twice to the hierarchy");
		} catch (NoDuplicateGroupsException e) {
			Assert.assertEquals(e.getMessage(),"You cannot add the group twice in the same hierarchy");
		}
	}
	
	@Test
	public void testDeleteGroupFromTheHierarchy() throws NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		berlinGroup.addGroupToGroup(groupName, berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name, berlinZone2);
		
		DeviceGroup tmpZone2 = berlinGroup.getInstanceOfGroup(groupZone2Name);
		Assert.assertNotNull(tmpZone2);
		
		Assert.assertEquals(groupZone2Name, tmpZone2.getName());
		
		berlinGroup.deleteGroup(berlinZone2);
		
		Assert.assertNull(berlinGroup.getInstanceOfGroup(groupZone2Name));
	}
	
	@Test
	public void testDeleteGroupFromGroup() throws NoDuplicateGroupsException{
		String groupName = "BerlinGroup";
		String groupZone1Name = "BerlinZoneGroup1";
		String groupZone2Name = "BerlinZoneGroup2";
		
		DeviceGroup berlinGroup = new DeviceGroup(groupName);
		DeviceGroup berlinZone1 = new DeviceGroup(groupZone1Name);
		DeviceGroup berlinZone2 = new DeviceGroup(groupZone2Name);
		
		berlinGroup.addGroupToGroup(groupName, berlinZone1);
		berlinGroup.addGroupToGroup(groupZone1Name, berlinZone2);
		
		DeviceGroup tmpZone2 = berlinGroup.getInstanceOfGroup(groupZone2Name);
		Assert.assertNotNull(tmpZone2);
		
		Assert.assertEquals(groupZone2Name, tmpZone2.getName());
		
		berlinGroup.deleteGroupFromGroup(groupZone1Name, berlinZone2);
		
		Assert.assertNull(berlinGroup.getInstanceOfGroup(groupZone2Name)); 
	}
	
}
