package by.htp.xml.entity;

public class Child extends Person {

	private String gender;

	public Child() {

	}

	public Child(String name, String surname, int age, String gender) {
		super(name, surname, age);
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Child: " + super.toString() + ". Gender: " + gender;
	}

}
