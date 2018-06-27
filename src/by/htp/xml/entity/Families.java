package by.htp.xml.entity;

import java.util.List;

public class Families {

	private List<Family> families;

	public Families() {

	}

	public Families(List<Family> families) {
		this.families = families;
	}

	public List<Family> getFamilies() {
		return families;
	}

	public void setFamilies(List<Family> families) {
		this.families = families;
	}

	public void show() {
		for (Family family : families) {
			System.out.println(family);
		}
	}

	@Override
	public String toString() {
		return "Families: " + families;
	}

}
