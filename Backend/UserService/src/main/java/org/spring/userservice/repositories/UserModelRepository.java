package org.spring.userservice.repositories;

import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
	Page<UserModelDto> findAllBy(Pageable pageable);

	AuthModelDto findByUsername(String username);

    boolean existsByUsername(String fullName);

	boolean existsByEmail(String email);

	AuthModelDto findByEmail(String email);

	List<UserModelDto> findByEmailIn(List<String> emails);

	UserModelDto findUserByEmail(String email);


}