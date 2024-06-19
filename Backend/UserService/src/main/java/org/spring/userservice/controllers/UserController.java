package org.spring.userservice.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.RegisterDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	@ApiResponse(description = "Register a new user", responseCode = "201")
	public ResponseEntity<EntityModel<RegisterDto>> register(@Valid @RequestBody RegisterDto registerDto) {
		if (userService.createUser(registerDto)) {
			EntityModel<RegisterDto> resource = EntityModel.of(registerDto);
			WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).register(registerDto));
			resource.add(linkTo.withRel("self"));
			return ResponseEntity.created(linkTo.toUri()).body(resource);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/")
	@ApiResponse(description = "Get all users", responseCode = "200")
	public ResponseEntity<PagedModel<EntityModel<UserModelDto>>> getAllUsers(@RequestParam(defaultValue = "0") int page
			, @RequestParam(defaultValue = "10") int size, PagedResourcesAssembler<UserModelDto> assembler) {
		Page<UserModelDto> users = userService.getAllUsers(page, size);
		return ResponseEntity.ok(assembler.toModel(users));
	}


	@GetMapping("/auth/{username}")
	@ApiResponse(description = "Get user by username", responseCode = "200")
	public ResponseEntity<EntityModel<AuthModelDto>> getUserByUsername(@PathVariable String username) {
		log.info("Received request to get user by username: {}", username);
		AuthModelDto user = userService.getUserByUsername(username);
		EntityModel<AuthModelDto> resource = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getUserByUsername(username));
		resource.add(linkTo.withRel("self"));
		return ResponseEntity.ok(resource);
	}

	@GetMapping("/checkFullName")
	@ApiResponse(description = "Check if a full name is taken", responseCode = "200")
	public ResponseEntity<Boolean> isFullNameTaken(@RequestParam String fullName) {
		boolean isTaken = userService.isFullNameTaken(fullName);
		return ResponseEntity.ok(isTaken);
	}

	@GetMapping("/checkEmail")
	@ApiResponse(description = "Check if an email is taken", responseCode = "200")
	public ResponseEntity<Boolean> isEmailTaken(@RequestParam String email) {
		boolean isTaken = userService.isEmailTaken(email);
		return ResponseEntity.ok(isTaken);
	}


}
