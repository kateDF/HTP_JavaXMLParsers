package by.htp.xml.parser.dom;

import static by.htp.xml.entity.FamilyEnum.AGE;
import static by.htp.xml.entity.FamilyEnum.CHILD;
import static by.htp.xml.entity.FamilyEnum.CHILDREN;
import static by.htp.xml.entity.FamilyEnum.FAMILY;
import static by.htp.xml.entity.FamilyEnum.FATHER;
import static by.htp.xml.entity.FamilyEnum.GENDER;
import static by.htp.xml.entity.FamilyEnum.ID;
import static by.htp.xml.entity.FamilyEnum.MAIDEN_NAME;
import static by.htp.xml.entity.FamilyEnum.MILITARY;
import static by.htp.xml.entity.FamilyEnum.MOTHER;
import static by.htp.xml.entity.FamilyEnum.NAME;
import static by.htp.xml.entity.FamilyEnum.SURNAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.htp.xml.entity.Child;
import by.htp.xml.entity.Families;
import by.htp.xml.entity.Family;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;
import by.htp.xml.parser.FamilyParser;

public class DomFamilyParser implements FamilyParser {

	private List<Family> families = new ArrayList<>();

	public Families parseFamilyDoc(String path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);

			Element root = doc.getDocumentElement();
			NodeList familiesList = doc.getElementsByTagName(FAMILY.getValue());
			for (int i = 0; i < familiesList.getLength(); i++) {
				Element familyElement = (Element) familiesList.item(i);
				Family family = buildFamily(familyElement);
				families.add(family);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Families(families);
	}

	private Family buildFamily(Element familyElement) {
		Family family = new Family();
		family.setId(Integer.parseInt(familyElement.getAttribute(ID.getValue())));

		Mother mother = buildMother(getElementByTagName(familyElement, MOTHER.getValue()));
		Father father = buildFather(getElementByTagName(familyElement, FATHER.getValue()));
		List<Child> children = buildChildren(getElementByTagName(familyElement, CHILDREN.getValue()));

		family.setMother(mother);
		family.setFather(father);
		family.setChildren(children);

		return family;
	}

	private Mother buildMother(Element motherElement) {
		Mother mother = new Mother();
		mother.setMaidenName(motherElement.getAttribute(MAIDEN_NAME.getValue()));
		mother.setName(getElementTextContent(motherElement, NAME.getValue()));
		mother.setSurname(getElementTextContent(motherElement, SURNAME.getValue()));
		mother.setAge(Integer.parseInt(getElementTextContent(motherElement, AGE.getValue())));
		return mother;
	}

	private Father buildFather(Element fatherElement) {
		Father father = new Father();
		father.setName(getElementTextContent(fatherElement, NAME.getValue()));
		father.setSurname(getElementTextContent(fatherElement, SURNAME.getValue()));
		father.setAge(Integer.parseInt(getElementTextContent(fatherElement, AGE.getValue())));
		if (hasElement(fatherElement, MILITARY.getValue())) {
			father.setMilitary(true);
		}
		return father;
	}

	private List<Child> buildChildren(Element childrenElement) {
		List<Child> children = new ArrayList<>();
		NodeList childrenList = childrenElement.getElementsByTagName(CHILD.getValue());
		for (int i = 0; i < childrenList.getLength(); i++) {
			Element childElement = (Element) childrenList.item(i);
			Child child = buildChild(childElement);
			children.add(child);
		}

		return children;
	}

	private Child buildChild(Element childElement) {
		Child child = new Child();
		child.setName(getElementTextContent(childElement, NAME.getValue()));
		child.setSurname(getElementTextContent(childElement, SURNAME.getValue()));
		child.setAge(Integer.parseInt(getElementTextContent(childElement, AGE.getValue())));
		child.setGender(getElementTextContent(childElement, GENDER.getValue()));

		return child;
	}

	private static String getElementTextContent(Element element, String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}

	private static Element getElementByTagName(Element element, String tagName) {
		return (Element) element.getElementsByTagName(tagName).item(0);
	}

	private static boolean hasElement(Element element, String tagName) {
		NodeList nList = element.getElementsByTagName(tagName);
		if (nList.item(0) != null) {
			return true;
		}
		return false;
	}
}
