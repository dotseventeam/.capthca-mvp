package dotseven.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dotseven.backend.entity.User;

/**
 * UserRepository: interface to get information from the repository
 * 
 * @author Nicola Baesso
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Method to get a User from the repository, by giving username and password
     * 
     * @param username
     * @param password
     * @return the user with those parameters, null if it doesn't exists
     */
    @Query(value = "SELECT * FROM Users WHERE username=:user AND passwordHash=:passwd", nativeQuery = true)
    User findUserByUsernameAndPassword(@Param("user") String username, @Param("passwd") String password);

    @Query(value = "SELECT * FROM Users WHERE username=:user", nativeQuery = true)
    User findUserByUsernam(@Param("user") String username);

    @Query(value = "INSERT FROM Users (username, passwordHash, firstName, lastName, birthDate, email, gender, profilePicture) VALUES (:username,	:password,	:firstName,	:lastName,	:birthDate,	:email,	:gender, :profilePicture)", nativeQuery = true)
    boolean addUser(@Param("username") String user, @Param("password") String passw, @Param("firstName") String firstN,
            @Param("lastName") String lastN, @Param("birthDate") LocalDate birth, @Param("email") String emailAddr,
            @Param("gender") char gndr, @Param("profilePicture") String prflPctr);
}