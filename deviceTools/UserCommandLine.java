package deviceTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserCommandLine implements Runnable{
	IDeviceCommand deviceCommand;
	
	String latestCommand = "";
	
	public UserCommandLine(IDeviceCommand command) {
		this.deviceCommand= command;
	}
	
	@Override
	public void run() {
		System.out.println("Please write the command you want to send to the device");
		while(true){
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String line = bufferedReader.readLine();
				if(line.contains("start")){
					deviceCommand.startTask();
				}else if(line.contains("shutdown") || line.contains("stop")){
					deviceCommand.shutDownTask();
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
