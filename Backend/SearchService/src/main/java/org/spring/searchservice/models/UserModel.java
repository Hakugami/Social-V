package org.spring.searchservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String username;

	@Field(type = FieldType.Text)
	private String email;

	@Field(type = FieldType.Text)
	private String profilePicture;

	@Field(type = FieldType.Text)
	private String firstName;

	@Field(type = FieldType.Text)
	private String lastName;

	@Field(type = FieldType.Text)
	private String combined;


	public void prePersist() {
		this.combined = String.join(" ",
				this.username != null ? this.username : "",
				this.firstName != null ? this.firstName : "",
				this.lastName != null ? this.lastName : "",
				this.email != null ? this.email : ""
		).trim();
	}
}
