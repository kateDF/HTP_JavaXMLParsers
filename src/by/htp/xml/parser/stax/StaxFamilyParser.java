package by.htp.xml.parser.stax;

import static by.htp.xml.entity.FamilyEnum.CHILD;
import static by.htp.xml.entity.FamilyEnum.CHILDREN;
import static by.htp.xml.entity.FamilyEnum.FAMILY;
import static by.htp.xml.entity.FamilyEnum.FATHER;
import static by.htp.xml.entity.FamilyEnum.MOTHER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.xml.entity.Child;
import by.htp.xml.entity.Families;
import by.htp.xml.entity.Family;
import by.htp.xml.entity.FamilyEnum;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;
import by.htp.xml.entity.Person;
import by.htp.xml.parser.FamilyParser;

public class StaxFamilyParser implements FamilyParser {
	private List<Family> familiesList = new ArrayList<>();
	private XMLInputFactory inputFactory;

	public StaxFamilyParser() {
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public Families parseFamilyDoc(String path) {
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(new File(path));
			XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String s = reader.getLocalName();
					if (s == FAMILY.getValue()) {
						Family family = buildFamily(reader);
						familiesList.add(family);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Families(familiesList);
	}

	private Family buildFamily(XMLStreamReader reader) throws XMLStreamException {
		Family family = new Family();
		family.setId(Integer.parseInt(reader.getAttributeValue(0)));

		String s;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				s = reader.getLocalName();
				switch (FamilyEnum.valueOf(s.toUpperCase().replace("-", "_"))) {
				case MOTHER:
					Mother mother = (Mother) buildPerson(reader);
					family.setMother(mother);
					break;
				case FATHER:
					Father father = (Father) buildPerson(reader);
					family.setFather(father);
					break;
				case CHILDREN:
					List<Child> currentChildren = buildChildrenList(reader);
					family.setChildren(currentChildren);
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				s = reader.getLocalName();
				if (s == FAMILY.getValue()) {
					return family;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag User");
	}

	private Person buildPerson(XMLStreamReader reader) throws XMLStreamException, NumberFormatException {
		Person currentPerson = null;

		switch (FamilyEnum.valueOf(reader.getLocalName().toUpperCase())) {
		case MOTHER:
			currentPerson = new Mother();
			((Mother) currentPerson).setMaidenName(reader.getAttributeValue(0));
			break;
		case FATHER:
			currentPerson = new Father();
			break;
		case CHILD:
			currentPerson = new Child();
			break;
		}

		String s;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				s = reader.getLocalName();
				switch (FamilyEnum.valueOf(s.toUpperCase().replace("-", "_"))) {
				case NAME:
					currentPerson.setName(getXMLText(reader));
					break;
				case SURNAME:
					currentPerson.setSurname(getXMLText(reader));
					break;
				case AGE:
					currentPerson.setAge(Integer.parseInt(getXMLText(reader)));
					break;
				case MILITARY:
					((Father) currentPerson).setMilitary(true);
					break;
				case GENDER:
					((Child) currentPerson).setGender(getXMLText(reader));
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				s = reader.getLocalName();
				if (s == MOTHER.getValue() || s == FATHER.getValue() || s == CHILD.getValue()) {
					return currentPerson;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag Family");
	}

	private List<Child> buildChildrenList(XMLStreamReader reader) throws XMLStreamException {
		List<Child> children = new ArrayList<>();
		String s;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				s = reader.getLocalName();
				if (s == CHILD.getValue()) {
					Child child = (Child) buildPerson(reader);
					children.add(child);
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				s = reader.getLocalName();
				if (s == CHILDREN.getValue()) {
					return children;
				}
				break;
			}
		}
		children = null;
		throw new XMLStreamException("Unknown element in tag Children");
	}

	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}

}
