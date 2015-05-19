package group.commands;

import group.Group;
import group.NoDuplicateGroupsException;
import deviceTools.Main;

public class AddGroupCommand implements GroupCommand {

	@Override
	public void execute(String command, String[] params){
		//add group to-root to-parent <root> <parent> <name of the group>
		if(params.length != 5){
			System.out.println("usage: add group <to-root> <to-parent of the group> <name of group>");
		}
		else {
			try{
				addGroupToGroup(params[2], params[3], params[4]);
			}
			catch(NoDuplicateGroupsException ex){
				System.out.println("you are not allowed to add duplicate groups to the same hierarchy");
			}
		}

	}
	
	public void addGroupToGroup(String hierarchy, String groupParent, String groupToAdd) throws NoDuplicateGroupsException{
		Group root = this.getHierarchyRoot(hierarchy);
		Group group = root.getInstanceOfGroup(groupToAdd);
		if(group==null){
			group = new Group(groupToAdd);
		}
		root.addGroupToGroup(groupParent, group);
	} 
	
	public Group getHierarchyRoot(String rootName){
		return Main.hierarchy.get(rootName);
	}
	

}
