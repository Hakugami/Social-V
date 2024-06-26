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
    @Enumerated(EnumType.STRING)
    private Status status;
    private String firstName;
    private String lastName;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String country;
    private String city;
    private LocalDate birthDate;
    private String phoneNumber;
    @Column(name = "profile_picture", columnDefinition = "MEDIUMTEXT")
    private String profilePicture;
    @Column(name = "cover_picture", columnDefinition = "MEDIUMTEXT")
    private String coverPicture;
    // URL For any Portfolio or other Social Media platform
    @Column(name = "url", columnDefinition = "MEDIUMTEXT")
    private String url;

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) &&
                Objects.equals(username, userModel.username) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(email, userModel.email) &&
                status == userModel.status &&
                Objects.equals(firstName, userModel.firstName) &&
                Objects.equals(lastName, userModel.lastName) &&
                Objects.equals(address, userModel.address) &&
                gender == userModel.gender &&
                Objects.equals(country, userModel.country) &&
                Objects.equals(city, userModel.city) &&
                Objects.equals(birthDate, userModel.birthDate) &&
                Objects.equals(phoneNumber, userModel.phoneNumber) &&
                Objects.equals(profilePicture, userModel.profilePicture) &&
                Objects.equals(coverPicture, userModel.coverPicture) &&
                Objects.equals(url, userModel.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, status, firstName, lastName, address, gender, country, city, birthDate, phoneNumber, profilePicture, coverPicture, url);
    }
}
