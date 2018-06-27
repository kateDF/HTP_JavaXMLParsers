package by.htp.xml.run;

import by.htp.xml.entity.Families;
import by.htp.xml.parser.FamilyParser;
import by.htp.xml.parser.dom.DomFamilyParser;
import by.htp.xml.parser.sax.SaxFamilyParser;
import by.htp.xml.parser.stax.StaxFamilyParser;

public class MainApp {

	public static void main(String[] args) {

		FamilyParser parser = new SaxFamilyParser();
		Families families = parser.parseFamilyDoc("resources/family.xml");
		families.show();

		System.out.println();

		FamilyParser parser2 = new DomFamilyParser();
		Families families2 = parser2.parseFamilyDoc("resources/family.xml");
		families2.show();

		System.out.println();

		FamilyParser parser3 = new StaxFamilyParser();
		Families families3 = parser3.parseFamilyDoc("resources/family.xml");
		families3.show();

	}

}
