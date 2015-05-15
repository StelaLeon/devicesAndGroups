package deviceTools;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Device implements Runnable {

	protected UUID id = UUID.randomUUID();
	
	protected String name;
	
	protected Meaning meaning;
	
	protected List<Group> groups= new LinkedList<>();

	public Device(String name){
		this.name = name;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void addGroup(Group group) {
		this.groups.add(group);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Meaning getMeaning() {
		return meaning;
	}

	public void setMeaning(Meaning meaning) {
		this.meaning = meaning;
	}

	public void deleteGroup(Group group){
		this.groups.remove(group);
	}

	@Override
	public void run() {
		switch(meaning){
		case Humidity: System.out.println("~~~~~~~~~~hum: 50%~~~~~~~");
			break;
		case Temperature: System.out.println("~~~~~~~~~temp: 21 C~~~~~~~~~~~~");
			break;
		default: System.out.println("This device has no meaning... ");
			break;
		}
	}
}
