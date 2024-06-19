package org.spring.userservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "UserModel", indexes = {
        @Index(name = "idx_usermodel_username", columnList = "username")
})
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    private String firstName;
    private String lastName;
    private String address;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private String country;
    private String city;
    private LocalDate birthDate;
    private String phoneNumber;
    private String profilePicture;
    private String coverPicture;
    // URL For any Portfolio or other Social Media platform
    private String url;

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
