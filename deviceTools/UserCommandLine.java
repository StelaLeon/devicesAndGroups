package deviceTools;

import group.DeviceGroup;
import group.commands.AddDeviceToGroupCommand;
import group.commands.AddGroupCommand;
import group.commands.ListCommand;
import group.commands.RemoveGroupCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import device.interaction.DeviceController;
import device.interaction.IDeviceCommand;

public class UserCommandLine implements Runnable{
	
	/**
	 * some kind of factory for firing commands according with the input
	 */
	private IDeviceCommand lastDeviceCommand; //could be a history of fired commands 
	
	@Override
	public void run() {
		System.out.println("Please write the command you want to send to the device");
		while(true){
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String line = bufferedReader.readLine();
				String[] tokens = line.split(" ");
				if(line.contains("start")){
					lastDeviceCommand= new DeviceController();
					lastDeviceCommand.startTask(Main.deviceTest);
					
				}else if(line.contains("shutdown") || line.contains("stop")){
					lastDeviceCommand.shutDownTask();
				}
				else if(line.contains("list all")){
					new ListCommand().execute(tokens);
				}
				else if(line.contains("add root")){
					//add a new hierarchy to the architecture
					//right format add root <name of the root group>
					DeviceGroup root = new DeviceGroup(tokens[2]);
					Main.hierarchy.put(tokens[2], root);
				}
				else if(line.contains("add group")){
					new AddGroupCommand().execute(tokens);
				}
				else if(line.contains("add device")){
						new AddDeviceToGroupCommand().execute(tokens);
				}
				else if(line.contains("remove group")){
					new RemoveGroupCommand().execute(tokens);
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
