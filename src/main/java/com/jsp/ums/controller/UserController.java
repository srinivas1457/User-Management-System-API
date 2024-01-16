package com.jsp.ums.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ums.dto.UserRequest;
import com.jsp.ums.dto.UserResponse;
import com.jsp.ums.exceptionhandler.ApplicationExceptionHandller;
import com.jsp.ums.service.UserService;
import com.jsp.ums.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * SWAGGER ANNOTATIONS
 * @Tag:
		Purpose: Organizes and categorizes API end points.
		Usage in Code: Applied at the class level to provide metadata about the API.
		
	@Operation:
		Purpose: Describes each API operation, including its purpose and possible responses.
		Usage in Code: Applied at the method level to describe an API operation.
		
	@ApiResponse:
		Purpose: Describes possible responses with their HTTP status codes and associated content types.
		Usage in Code: Used within the @Operation annotation to specify different responses for an API operation.
		
	@Content:
		Purpose: Specifies the content type and the schema of the response.
		Usage in Code: Used within the @ApiResponse annotation.
		
	@Schema:
		Purpose: Defines the structure of the response or request body.
		Usage in Code: Used within the @Content annotation or directly in the @ApiResponse annotation.
 */

@RestController
@Tag(name = "User", description = "API endpoints that are related to User Entity")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(description = "**User Registeration -** the API endpoint is used to register the user", responses = {
			@ApiResponse(responseCode = "201", description = "user successfully added", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add user") })
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.addUser(userRequest);
	}

	@Operation(description = "**Find User by Id -** "
			+ "the API endpoint is used to fetch the user data based on the Id", responses = {
					@ApiResponse(responseCode = "302", description = "user found", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found", content = {
							@Content(schema = @Schema(implementation = ApplicationExceptionHandller.class)) }) })
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId) {
		return userService.findUserById(userId);
	}

	@Operation(description = "**Find all Users -** " + "the API endpoint is used to list of users", responses = {
			@ApiResponse(responseCode = "302", description = "user found", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "no user found", content = {
					@Content(schema = @Schema(implementation = ApplicationExceptionHandller.class)) }) })
	@GetMapping("/users")
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers() {
		return userService.findAllUsers();
	}

	@Operation(description = "**Update User by Id -** "
			+ "the API endpoint is used to update the user data based on the Id", responses = {
					@ApiResponse(responseCode = "200", description = "user updated", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "failed to update user", content = {
							@Content(schema = @Schema(implementation = ApplicationExceptionHandller.class)) }) })
	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(@RequestBody @Valid UserRequest userRequest,
			@PathVariable int userId) {
		return userService.updateUserById(userRequest, userId);
	}

	@Operation(description = "**Delete User by Id -** "
			+ "the API endpoint is used to delete the user data based on the Id", responses = {
					@ApiResponse(responseCode = "200", description = "user deleted", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "failed to delete user", content = {
							@Content(schema = @Schema(implementation = ApplicationExceptionHandller.class)) }) })
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(@PathVariable int userId) {
		return userService.deleteUserById(userId);
	}

}
