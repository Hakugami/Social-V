package org.spring.likeservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emotion {
	LIKE("like"),
	LOVE("love"),
	HAPPY("happy"),
	HAHA("haha"),
	THINK("think"),
	SAD("sad"),
	LOVELY("lovely");

	private final String emotion;


}
