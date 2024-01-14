package com.jsp.ums.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.ums.dto.UserRequest;
import com.jsp.ums.dto.UserResponse;
import com.jsp.ums.entity.User;
import com.jsp.ums.exceptionhandler.DataNotPresentException;
import com.jsp.ums.exceptionhandler.UserNotFoundByIdException;
import com.jsp.ums.repository.UserRepository;
import com.jsp.ums.service.UserService;
import com.jsp.ums.util.ResponseStructure;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ResponseStructure<UserResponse> structure;
	

	public User mapToUser(UserRequest userRequest)
	{
		return User.builder()
				.userName(userRequest.getUserName())
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())
				.address(userRequest.getAddress())
				.build();
	}
	
	public UserResponse mapToUserResponse(User user)
	{
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest) {
		User user = mapToUser(userRequest);
	     user = userRepo.save(user); 
	     
	     UserResponse userResponse = mapToUserResponse(user);
	     
	     structure.setStatusCode(HttpStatus.CREATED.value());
	     structure.setMessage("User Created");
	     structure.setData(userResponse);
	     
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new UserNotFoundByIdException("User With Given Id Not Found"));
		
			UserResponse userResponse = mapToUserResponse(user);
			
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("User Data found for a given id");
			structure.setData(userResponse);
			
			return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest userRequest, int id) {
		
//		User olduser = userrepo.findById(id).map(u -> {
//			u.setId(id);
//			return userrepo.save(u);
//		}).orElseThrow(() -> new RuntimeException());
		
	    User olduser = userRepo.findById(id).orElseThrow(()->new UserNotFoundByIdException("User With Given Id Not Found"));
		
		User user = mapToUser(userRequest);
		user.setUserId(olduser.getUserId());
		
	    user = userRepo.save(user);
	    
	    UserResponse userResponse = mapToUserResponse(user);
	    
	    structure.setStatusCode(HttpStatus.OK.value());
	    structure.setMessage("User Data Updated");
	    structure.setData(userResponse);
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
	}
	

//	@Override
//	public ResponseEntity<ResponseStructure<UserResponce>> updateUserById(UserRequest userRequest, int userId) {
//		User user = userRepo.findById(userId)
//				.orElseThrow(() -> new UserNotFoundByIdException("User not found with the id " + userId));
//		
//		User user2 = userRepo.save(convertToUser(userRequest, user));
//		
////		User userObj = userRepo.findById(userId).map(u->{
////			user.se
////			return userRepo.save(convertToUser(userRequest, user));
////			
////		}).orElseThrow(() -> new UserNotFoundByIdException("User not found with the id " + userId));
////			
//		
//		UserResponce userResponse = convertToUserResponse(user2);
//		
//		responceStructure.setStatusCode(HttpStatus.OK.value());
//		responceStructure.setMessage("User Date ");
//		responceStructure.setData(userResponse);
//		return new ResponseEntity<ResponseStructure<UserResponce>>(responceStructure,HttpStatus.OK);
//	}




	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers() {
		
		List<User> userList = userRepo.findAll();
		if(!userList.isEmpty())
		{
			List<UserResponse> list=new ArrayList<>();
			
			for(User user : userList)
			{
				UserResponse userResponse = mapToUserResponse(user);
			     list.add(userResponse);
			}
			ResponseStructure<List<UserResponse>> structure=new ResponseStructure<>();
			
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("User Data Found");
			structure.setData(list);
			
			return new ResponseEntity<ResponseStructure<List<UserResponse>>>(structure,HttpStatus.FOUND);
		}
		else {
			throw new DataNotPresentException("User Data Not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundByIdException("User With Given Id Not Found"));
		
		userRepo.delete(user);
		
		UserResponse userResponse = mapToUserResponse(user);
		
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("User Deleted Successfully");
		structure.setData(userResponse);
		
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findUserByAdress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserByEmail(UserRequest userRequest, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
