package dotseven.backend.service;

import java.util.List;
import java.util.Optional;

import dotseven.backend.dto.UserDetailDto;
import dotseven.backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dotseven.backend.entity.User;
import dotseven.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Optional<User> findByUsername(String usernameToFind) {
		User selectedUser = userRepository.findUserByUsernam(usernameToFind);
		if (selectedUser == null) {
			return Optional.empty();
		}
		return Optional.of(selectedUser);
	}

	public List<User> listusers() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public UserDetailDto fromUserDetailToDto(UserDetailsImpl user) {

		User realUser = findByUsername(user.getUsername()).get();

		UserDetailDto dto = new UserDetailDto(realUser.getUsername(),
				realUser.getFirstName(),
				realUser.getLastName(),
				realUser.getGender(),
				realUser.getBirthDate(),
				realUser.getProfilePicture()
		);

		return dto;

	}
}
