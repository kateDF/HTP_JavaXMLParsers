package by.htp.xml.entity;

import java.util.List;

public class Family {

	private int id;
	private Mother mother;
	private Father father;
	private List<Child> children;

	public Family() {

	}

	public Family(int id, Mother mother, Father father, List<Child> children) {
		this.id = id;
		this.mother = mother;
		this.father = father;
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Mother getMother() {
		return mother;
	}

	public void setMother(Mother mother) {
		this.mother = mother;
	}

	public Father getFather() {
		return father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public void addChild(Child child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return "Family id: " + id + ". " + mother + ". " + father + ". Children: " + children;
	}

}
