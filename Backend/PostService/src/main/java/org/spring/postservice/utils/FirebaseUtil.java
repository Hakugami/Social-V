package org.spring.postservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class FirebaseUtil {
	public String uploadVideo(byte[] video) {
		log.info("Uploading video to firebase");
		//TODO upload the video to firebase
		return "videoUrl";
	}

	public List<String> uploadImage(List<byte[]> images) {
		log.info("Uploading image to firebase");
		//TODO upload the images to firebase
		return Collections.emptyList();
	}
}
