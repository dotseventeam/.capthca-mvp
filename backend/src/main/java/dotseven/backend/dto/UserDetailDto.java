package dotseven.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserDetailDto {

    private String username;

    private String firstName;

    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailDto that = (UserDetailDto) o;
        return gender == that.gender && Objects.equals(username, that.username) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(profilePicture, that.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, gender, birthDate, profilePicture);
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    private char gender;

    private LocalDate birthDate;

    private String profilePicture;

    public UserDetailDto(
            String username,
            String firstName,
            String lastName,
            char gender,
            LocalDate birthDate,
            String profilePicture) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
    }
}
