package org.spring.userservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.userservice.mappers.RegisterMapper;
import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.RegisterDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.repositories.UserModelRepository;
import org.spring.userservice.utils.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserModelRepository userModelRepository;
	private final SecurityUtil securityUtil;
	private final RegisterMapper registerMapper;

	public boolean createUser(RegisterDto registerDto) {
		try {
			registerDto.setPassword(securityUtil.hashPassword(registerDto.getPassword()));
			userModelRepository.save(registerMapper.toEntity(registerDto));
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
}
