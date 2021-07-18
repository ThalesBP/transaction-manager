package br.thales.tools.transactions.manager.controller;


import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.thales.tools.transactions.manager.model.Account.Type.CHECKING;
import static br.thales.tools.transactions.manager.model.Account.Type.SAVING;
import static br.thales.tools.transactions.manager.utils.Constants.Status.ACTIVE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "getAllAccounts")
    public List<Account> listAll(){
        return accountRepository.findAll();
    }

    @PostMapping(value = "getAccount")
    public Account getAccount(@RequestBody Long id){
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new HttpClientErrorException(BAD_REQUEST, "Account not found");
        }
    }

    @PostMapping(value = "addCheckingAccount")
    public String addCheckingAccount(@RequestBody Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new HttpClientErrorException(BAD_REQUEST, "User not found");
        }
        Account account = new Account();
        account.setUserId(user.get().getId());
        account.setStatus(ACTIVE);
        account.setType(CHECKING);
        account.setDate(new Date());
        accountRepository.save(account);
        return "Account ID: " + account.getId() + " for user " + user.get().getName();
    }

    @PostMapping(value = "addSavingAccount")
    public String addSavingAccount(@RequestBody Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new HttpClientErrorException(BAD_REQUEST, "User not found");
        }
        Account account = new Account();
        account.setUserId(user.get().getId());
        account.setStatus(ACTIVE);
        account.setType(SAVING);
        account.setDate(new Date());
        accountRepository.save(account);
        return "Account ID: " + account.getId() + " for user " + user.get().getName();
    }
}
