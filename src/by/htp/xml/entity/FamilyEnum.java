package by.htp.xml.entity;

public enum FamilyEnum {

	FAMILIES("families"), FAMILY("family"), MOTHER("mother"), FATHER("father"), CHILDREN("children"), CHILD(
			"child"), ID("id"), MAIDEN_NAME("maiden-name"), NAME(
					"name"), SURNAME("surname"), AGE("age"), MILITARY("military"), GENDER("gender");

	private String value;

	private FamilyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static FamilyEnum getByValue(String value) {
		for (FamilyEnum e : values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException("Enum with value not found");
	}

}
