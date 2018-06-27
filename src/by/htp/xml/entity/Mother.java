package by.htp.xml.entity;

public class Mother extends Person {

	private String maidenName;

	public Mother() {

	}

	public Mother(String name, String surname, int age, String maidenName) {
		super(name, surname, age);
		this.maidenName = maidenName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	@Override
	public String toString() {
		return "Mother: " + super.toString() + ". Maiden name: " + maidenName;
	}

}
