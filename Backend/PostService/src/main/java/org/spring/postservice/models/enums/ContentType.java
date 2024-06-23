package org.spring.postservice.models.enums;

public enum ContentType {
	TEXT("text"),
	IMAGE("image"),
	VIDEO("video");

	private final String value;

	ContentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
