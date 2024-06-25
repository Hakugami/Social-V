package org.spring.messageservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
public class User {
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private Status status;
}
