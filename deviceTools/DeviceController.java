package deviceTools;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DeviceController implements IDeviceCommand{
	Device device;
	
	private ScheduledExecutorService scheduledExecutorService;
	
	public DeviceController(Device device) {
		this.device = device;
		scheduledExecutorService = Executors.newScheduledThreadPool(5);
	}

	@Override
	public void execute() {
		@SuppressWarnings("unchecked")
		ScheduledFuture scheduledFuture =
			scheduledExecutorService.scheduleAtFixedRate(device,
					0,
					1,
					TimeUnit.SECONDS);
	}
	
	public void shutDownTask(){
		this.scheduledExecutorService.shutdown();
	}
	
	public void startTask(){
		execute();
	}
}
