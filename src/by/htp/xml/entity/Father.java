package by.htp.xml.entity;

public class Father extends Person {

	private boolean isMilitary;

	public Father() {

	}

	public Father(String name, String surname, int age, boolean isMilitary) {
		super(name, surname, age);
		this.isMilitary = isMilitary;
	}

	public boolean isMilitary() {
		return isMilitary;
	}

	public void setMilitary(boolean isMilitary) {
		this.isMilitary = isMilitary;
	}

	@Override
	public String toString() {
		return "Father: " + super.toString() + ". Is military: " + isMilitary;
	}

}
