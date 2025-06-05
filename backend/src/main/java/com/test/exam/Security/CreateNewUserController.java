package com.test.exam.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Model.CustomUser;
import com.test.exam.Service.CreateNewUserService;

@RestController
public class CreateNewUserController {

    private final CreateNewUserService createNewUserService;

    public CreateNewUserController(CreateNewUserService createNewUserService){
        this.createNewUserService = createNewUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createNewUser(@RequestBody CustomUser user){
        return createNewUserService.execute(user);
    }

}
