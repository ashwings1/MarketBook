package com.test.exam.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.AccountDTO;
import com.test.exam.Security.CustomUser;
import com.test.exam.Security.CustomUserRepository;

@Service
public class GetAccountService implements Command<CustomUser, AccountDTO>{
    
    private final CustomUserRepository customUserRepository;

    public GetAccountService(CustomUserRepository customUserRepository){
        this.customUserRepository = customUserRepository;
    }

    @Override
    public ResponseEntity<AccountDTO> execute(CustomUser customUser){

        //Find user by id
        CustomUser userDetails = customUserRepository.findById(customUser.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        //Convert to DTO
        AccountDTO accountDTO = new AccountDTO(userDetails.getId());
        
        return ResponseEntity.ok(accountDTO);
    }
    
}
