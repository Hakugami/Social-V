package org.spring.userservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.userservice.events.UserRegistrationEvent;
import org.spring.userservice.mappers.RegisterMapper;
import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.RegisterDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.Dtos.PictureDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.spring.userservice.utils.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserModelRepository userModelRepository;
	private final SecurityUtil securityUtil;
	private final RegisterMapper registerMapper;
	private final KafkaTemplate<String , UserRegistrationEvent> kafkaTemplate;
	private static final String USER_TOPIC = "user-topic";

	public boolean createUser(RegisterDto registerDto) {
		try {
			registerDto.setPassword(securityUtil.hashPassword(registerDto.getPassword()));
			UserModel userModel = userModelRepository.save(registerMapper.toEntity(registerDto));
			kafkaTemplate.send(USER_TOPIC, new UserRegistrationEvent(userModel.getId().toString(), userModel.getUsername(),
					userModel.getEmail(), userModel.getProfilePicture(), userModel.getFirstName(), userModel.getLastName()));
			log.info("User created successfully : {}", registerDto.getUsername());
			return true;
		} catch (Exception e) {
			log.error("Error creating user: {}", e.getMessage());
			return false;
		}
	}


	public Page<UserModelDto> getAllUsers(int page, int size) {
		return userModelRepository.findAllBy(PageRequest.of(page, size));

	}

	public AuthModelDto getUserByUsername(String username) {
		return userModelRepository.findByUsername(username);
	}

	public boolean isFullNameTaken(String fullName) {
		return userModelRepository.existsByUsername(fullName);
	}

	public boolean isEmailTaken(String email) {
		return userModelRepository.existsByEmail(email);
	}

	public AuthModelDto getUserByEmail(String email) {
		return userModelRepository.findByEmail(email);
	}

	public List<UserModelDto> getUsersByEmails(List<String> emails) {
		return userModelRepository.findByEmailIn(emails);
	}

	public UserModelDto getByEmail(String email) {
		return userModelRepository.findUserByEmail(email);
	}

	public UserModelDto getByUsername(String username) {
		return userModelRepository.getByUsername(username);
	}

	public PictureDto getProfilePicture(String username) {
		return userModelRepository.findProfilePictureByUsername(username);
	}
}
