package org.spring.postservice.models.Dtos;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagePostDto extends PostDto implements Serializable {
	List<MultipartFile> images;
}
