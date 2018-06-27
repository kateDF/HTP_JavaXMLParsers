package by.htp.xml.parser.sax;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.xml.entity.Child;
import by.htp.xml.entity.Family;
import by.htp.xml.entity.FamilyEnum;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;
import by.htp.xml.entity.Person;

public class SaxFamilyHandler extends DefaultHandler {

	private List<Family> families;
	private Family currentFamily = null;
	private Person currentPerson = null;
	private List<Child> currentChildren = new ArrayList<>();
	private FamilyEnum currentEnum = null;
	private FamilyEnum en;
	private EnumSet<FamilyEnum> withText;

	public SaxFamilyHandler() {
		families = new ArrayList<Family>();
		withText = EnumSet.range(FamilyEnum.NAME, FamilyEnum.GENDER);
	}

	public List<Family> getFamilies() {
		return families;
	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		en = FamilyEnum.getByValue(localName);
		switch (en) {
		case FAMILY:
			currentFamily = new Family();
			currentFamily.setId(Integer.parseInt(attributes.getValue(0)));
			break;
		case MOTHER:
			currentPerson = new Mother();
			((Mother) currentPerson).setMaidenName(attributes.getValue(0));
			break;
		case FATHER:
			currentPerson = new Father();
			break;
		case CHILDREN:
			break;
		case CHILD:
			currentPerson = new Child();
			break;
		default:
			FamilyEnum temp = FamilyEnum.getByValue(localName);
			if (withText.contains(temp)) {
				currentEnum = temp;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		en = FamilyEnum.getByValue(localName);
		switch (en) {
		case FAMILY:
			families.add(currentFamily);
			break;
		case MOTHER:
			currentFamily.setMother((Mother) currentPerson);
			break;
		case FATHER:
			currentFamily.setFather((Father) currentPerson);
			break;
		case CHILD:
			currentChildren.add((Child) currentPerson);
			break;
		case CHILDREN:
			currentFamily.setChildren(currentChildren);
			currentChildren = new ArrayList<>();
			break;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();
		if (currentEnum != null) {
			switch (currentEnum) {
			case NAME:
				currentPerson.setName(s);
				break;
			case SURNAME:
				currentPerson.setSurname(s);
				break;
			case AGE:
				currentPerson.setAge(Integer.parseInt(s));
				break;
			case MILITARY:
				((Father) currentPerson).setMilitary(true);
				break;
			case GENDER:
				((Child) currentPerson).setGender(s);
				break;
			default:
				break;
			}
			currentEnum = null;
		}
	}

}
