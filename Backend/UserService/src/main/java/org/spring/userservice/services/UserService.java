package org.spring.userservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.userservice.events.UserRegistrationEvent;
import org.spring.userservice.mappers.RegisterMapper;
import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.PictureDto;
import org.spring.userservice.models.Dtos.RegisterDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.spring.userservice.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	@Value("${default.user.image.url}")
	private String defaultProfilePicture;
	private final UserModelRepository userModelRepository;
	private final SecurityUtil securityUtil;
	private final RegisterMapper registerMapper;
	private final KafkaTemplate<String , UserRegistrationEvent> kafkaTemplate;
	private static final String USER_TOPIC = "user-topic";

	public boolean createUser(RegisterDto registerDto) {
		try {
			registerDto.setPassword(securityUtil.hashPassword(registerDto.getPassword()));
			UserModel registerMapperEntity = registerMapper.toEntity(registerDto);
			registerMapperEntity.setProfilePicture(defaultProfilePicture);
			UserModel userModel = userModelRepository.save(registerMapperEntity);
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
		return null;
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

	public UserModel updateCurrentUserModel(UserModelDto userModelDto, UserModel currentUserModel) {
		if (userModelDto.username() != null)
			currentUserModel.setUsername(userModelDto.username());

		if (userModelDto.email() != null)
			currentUserModel.setEmail(userModelDto.email());

		if (userModelDto.status() != null)
			currentUserModel.setStatus(userModelDto.status());

		if (userModelDto.firstName() != null)
			currentUserModel.setFirstName(userModelDto.firstName());

		if (userModelDto.lastName() != null)
			currentUserModel.setLastName(userModelDto.lastName());

		if (userModelDto.address() != null)
			currentUserModel.setAddress(userModelDto.address());

		if (userModelDto.gender() != null)
			currentUserModel.setGender(userModelDto.gender());

		if (userModelDto.country() != null)
			currentUserModel.setCountry(userModelDto.country());

		if (userModelDto.city() != null)
			currentUserModel.setCity(userModelDto.city());

		if (userModelDto.birthDate() != null)
			currentUserModel.setBirthDate(userModelDto.birthDate());

		if (userModelDto.phoneNumber() != null)
			currentUserModel.setPhoneNumber(userModelDto.phoneNumber());

		if (userModelDto.profilePicture() != null)
			currentUserModel.setProfilePicture(userModelDto.profilePicture());

		if (userModelDto.coverPicture() != null)
			currentUserModel.setCoverPicture(userModelDto.coverPicture());

		if (userModelDto.url() != null)
			currentUserModel.setUrl(userModelDto.url());

		return currentUserModel;
	}

	public UserModel updateProfileInfo(UserModelDto comingUserModelDto, String profilePicUrl) {
		UserModel saved = null;
		try {
			UserModel userModel = userModelRepository.findUserModelByEmail(comingUserModelDto.email());
			userModel = updateCurrentUserModel(comingUserModelDto, userModel);
			if(profilePicUrl!=null && !profilePicUrl.isEmpty()){
				userModel.setProfilePicture(profilePicUrl);
			}
			log.info("Updating user : {}", userModel);
			saved = userModelRepository.save(userModel);
			log.info("User updated successfully : {}", saved.getUsername());
		} catch (RuntimeException e) {
			log.info("something went wrong !");
		}
		kafkaTemplate.send(USER_TOPIC, new UserRegistrationEvent(saved.getId().toString(), saved.getUsername(),
				saved.getEmail(), saved.getProfilePicture(), saved.getFirstName(), saved.getLastName()));
		return saved;
	}
}
