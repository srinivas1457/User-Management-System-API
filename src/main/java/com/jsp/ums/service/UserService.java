package com.jsp.ums.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.ums.dto.UserRequest;
import com.jsp.ums.dto.UserResponse;
import com.jsp.ums.util.ResponseStructure;


public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userrequest);
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest userRequest,int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId);
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers();
	
	
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmail(String email);
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findUserByAdress(String address);
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserByEmail(UserRequest userRequest,String email);
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserByEmail(String email);
	

}
