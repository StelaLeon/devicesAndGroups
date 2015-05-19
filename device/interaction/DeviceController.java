package device.interaction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DeviceController implements IDeviceCommand{
	
	ScheduledFuture<?> scheduledFuture;
	
	private ScheduledExecutorService scheduledExecutorService;
	
	private long initialTaskDelay = 0;
	
	private long period = 3;
	
	public DeviceController() {
		scheduledExecutorService = Executors.newScheduledThreadPool(5);
	}

	@Override
	public void execute(Device device) {
		scheduledFuture =
			scheduledExecutorService.scheduleAtFixedRate(device,
					initialTaskDelay,
					period,
					TimeUnit.SECONDS);
	}
	
	public void shutDownTask(){
		System.out.println("stopping the task");
		this.scheduledFuture.cancel(false);
	}
	
	public void startTask(Device device){
		execute(device);
	}
}
