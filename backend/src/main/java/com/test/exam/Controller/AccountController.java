package com.test.exam.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Model.AccountDTO;
import com.test.exam.Model.CustomUser;
import com.test.exam.Model.UpdateAccountRequest;
import com.test.exam.Service.GetAccountService;
import com.test.exam.Service.UpdateAccountService;

@RestController
public class AccountController {

    private final GetAccountService getAccountService;
    private final UpdateAccountService updateAccountService;

    public AccountController(GetAccountService getAccountService, UpdateAccountService updateAccountService){
        this.getAccountService = getAccountService;
        this.updateAccountService = updateAccountService;
    }

    //Get Account
    //Secure endpoint to require authentication
    @GetMapping("/account")
    public ResponseEntity<AccountDTO> getAccount(@RequestHeader("Authorization") String authHeader){
        return getAccountService.execute(authHeader);
    }

    //Update Account
    @PutMapping("/account")
    public ResponseEntity<AccountDTO> updateAccount(@RequestHeader("Authorization") String authHeader, @RequestBody CustomUser updateRequest){
        UpdateAccountRequest request = new UpdateAccountRequest(authHeader, updateRequest);
        ResponseEntity<AccountDTO> result = updateAccountService.execute(request);
        return result;
    }
    
}
