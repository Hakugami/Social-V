package org.spring.postservice.models.Dtos;

import java.io.Serializable;
import java.util.List;

public class LikeDto implements Serializable {
	Integer numberOfLikes;
	List<String> usernames;
}
