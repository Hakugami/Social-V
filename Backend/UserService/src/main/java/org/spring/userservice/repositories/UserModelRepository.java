package org.spring.userservice.repositories;

import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.PictureDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
	Page<UserModelDto> findAllBy(Pageable pageable);

	AuthModelDto findByUsername(String username);

    boolean existsByUsername(String fullName);

	boolean existsByEmail(String email);

	AuthModelDto findByEmail(String email);

	List<UserModelDto> findByEmailIn(List<String> emails);

	UserModelDto findUserByEmail(String email);

	PictureDto findProfilePictureByUsername(String username);

	@Query("select u from UserModel u where u.email=:email")
	Optional<UserModel> findUserModelByEmail(@Param("email") String email);

	@Query("select u from UserModel u where u.email=:email")
	Optional<UserModelDto> findUserModelDtoByEmail(@Param("email") String email);

	UserModelDto getByUsername(String username);
}