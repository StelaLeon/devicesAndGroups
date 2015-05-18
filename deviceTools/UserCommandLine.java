package deviceTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserCommandLine implements Runnable{
	IDeviceCommand deviceCommand;
	
	IGroupCommand groupCommand;
	
	String latestCommand = "";
	
	public UserCommandLine(IDeviceCommand command) {
		this.deviceCommand= command;
		this.groupCommand = new Architecture();
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
				else if(line.contains("add group to-root to-parent")){
					//add group to-root to-parent <root> <parent> <name of the group>
					System.out.println("adding groupt to parent within a hierarchy");
					try{
						this.groupCommand.addGroupToGroup(tokens[4], tokens[5], tokens[6]);
					}
					catch(NoDuplicateGroupsException ex){
						System.out.println("you are not allowed to add duplicate groups to the same hierarchy");
					}
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
