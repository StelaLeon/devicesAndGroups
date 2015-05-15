package deviceTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Main {

	public static void main(String[] args) {
		Device deviceOne = new Device("deviceOne");
		deviceOne.setMeaning(Meaning.Humidity);
		DeviceController deviceController= new DeviceController(deviceOne);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
