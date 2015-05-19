package deviceTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserCommandLine implements Runnable{
	
	IDeviceCommand deviceCommand;
	
	IGroupCommand groupCommand;
	
	
	public UserCommandLine(IDeviceCommand command, IGroupCommand groupCommand) {
		this.deviceCommand= command;
		this.groupCommand = groupCommand;
	}
	
	@Override
	public void run() {
		System.out.println("Please write the command you want to send to the device");
		while(true){
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String line = bufferedReader.readLine();
				String[] tokens = line.split(" ");
				if(line.contains("start")){
					deviceCommand.startTask();
				}else if(line.contains("shutdown") || line.contains("stop")){
					deviceCommand.shutDownTask();
				}
				else if(line.contains("list all")){
					System.out.println(groupCommand.toString());
				}
				else if(line.contains("add root")){
					//add a new hierarchy to the architecture
					//right format add root <name of the root group>
					this.groupCommand.addHierarchy(tokens[2]);
				}
				else if(line.contains("add group")){
					//add group to-root to-parent <root> <parent> <name of the group>
					if(tokens.length != 5){
						System.out.println("usage: add group <to-root> <to-parent of the group> <name of group>");
					}
					else {
						try{
							this.groupCommand.addGroupToGroup(tokens[2], tokens[3], tokens[4]);
						}
						catch(NoDuplicateGroupsException ex){
							System.out.println("you are not allowed to add duplicate groups to the same hierarchy");
						}
					}
				}
				else if(line.contains("add device")){
					if(tokens.length < 4){
						System.out.println("usage: add device <root> <group> <name of the device>");
					}
					try {
						this.groupCommand.addDeviceToGroup(tokens[2], tokens[3], new Device(tokens[4]));
					} catch (NoDuplicatedDevicesException e) {
						System.out.println("you are not allowed to add duplicate device to the same hierarchy");
					}
				}
				else if(line.contains("remove group")){
					if(tokens.length != 2){
						System.out.println("usage: remove group <name of the group you want to remove>");
					}
					this.groupCommand.deleteGroup(tokens[2]);
				}
				else{
					System.out.println("Let me help you. You might wanna use some key words like: start, shutdown, stop.");
				}
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
