package dotseven.backend.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Users
 * This class represents a single User, stored in the database
 * 
 * @author Nicola Baesso
 * @version 1.0
 */

@Entity
@Table(name = "Users")
public class User {

	@Id
	private final String username;

	private String passwordHash; // frontend can request a password reset

	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final String email;
	private final char gender;
	private final String profilePicture;

	/**
	 * Default constructor. Useless, but required by Hibernate.
	 */
	public User() {
		this.username = "test";
		this.passwordHash = "test";
		this.email = "testtest@test.it";
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.gender = 'N'; // N is for "null"
		this.profilePicture = null;
	}

	/**
	 * Constructor with the required parameters
	 * 
	 * @param username
	 * @param passwordHash
	 * @param email
	 */
	public User(String username, String passwordHash, String email) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.gender = 'N'; // N is for "null"
		this.profilePicture = null;
	}

	/**
	 * Constructor with all the parameters, even optional ones
	 * 
	 * @param username
	 * @param passwordHash
	 * @param firstName
	 * @param lastName
	 * @param birthDate
	 * @param email
	 * @param gender
	 * @param profilePicture
	 */
	public User(String username, String passwordHash, String firstName, String lastName, LocalDate birthDate,
			String email, char gender, String profilePicture) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.email = email;
		this.gender = gender;
		this.profilePicture = profilePicture;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public String getEmail() {
		return this.email;
	}

	public char getGender() {
		return this.gender;
	}

	public String getProfilePicture() {
		return this.profilePicture;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User{Username: ");
		sb.append(username).append("\n");
		sb.append("Password (not in clear, dummy): ").append(passwordHash).append("\n");
		if (firstName != null) {
			sb.append("First name: ").append(firstName).append("\n");
		} else {
			sb.append("First name: Unknown").append("\n");
		}
		if (lastName != null) {
			sb.append("Last name: ").append(lastName).append("\n");
		} else {
			sb.append("Last name: Unknown").append("\n");
		}
		if (birthDate != null) {
			sb.append("Date of birth: ").append(birthDate).append("\n");
		} else {
			sb.append("Date of birth: Unknown").append("\n");
		}
		sb.append("Email: ").append(email).append("\n");
		if (gender != 'N') {
			sb.append("Gender: ").append(gender).append("\n");
		} else {
			sb.append("Gender: Unknown").append("\n");
		}
		if (profilePicture != null) {
			sb.append("URL of profile picture: ").append(profilePicture).append("\n");
		} else {
			sb.append("URL of profile picture: not found").append("\n");
		}
		sb.append("}");
		return sb.toString();
	}
}
