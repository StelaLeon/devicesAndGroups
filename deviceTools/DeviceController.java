package deviceTools;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DeviceController implements IDeviceCommand{
	Device device;
	ScheduledFuture<?> scheduledFuture;
	private ScheduledExecutorService scheduledExecutorService;
	
	public DeviceController(Device device) {
		this.device = device;
		scheduledExecutorService = Executors.newScheduledThreadPool(5);
	}

	@Override
	public void execute() {
		if(this.scheduledExecutorService.isShutdown()){
			scheduledExecutorService = Executors.newScheduledThreadPool(5);
		}
		scheduledFuture =
			scheduledExecutorService.scheduleAtFixedRate(device,
					0,
					6,
					TimeUnit.SECONDS);
	}
	
	public void shutDownTask(){
		this.scheduledFuture.cancel(false);
	}
	
	public void startTask(){
		execute();
	}
}
