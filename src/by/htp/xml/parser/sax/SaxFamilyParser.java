package by.htp.xml.parser.sax;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.htp.xml.entity.Families;
import by.htp.xml.parser.FamilyParser;

public class SaxFamilyParser implements FamilyParser {

	public Families parseFamilyDoc(String path) {
		Families families = new Families();

		try {
			SaxFamilyHandler handler = new SaxFamilyHandler();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(path);
			families.setFamilies(handler.getFamilies());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return families; // Families = ArrayList<Family>
	}

}
