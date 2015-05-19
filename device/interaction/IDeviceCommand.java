package device.interaction;

public interface IDeviceCommand {

	public void execute(Device device);
	
	public void shutDownTask();
	
	public void startTask(Device device);
}
