package br.thales.tools.transactions.manager.controller;


import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static br.thales.tools.transactions.manager.model.Account.Type.CHECKING;
import static br.thales.tools.transactions.manager.model.Account.Type.SAVING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "getAllAccounts")
    public List<Account> listAll(){
        return accountService.listAll();
    }

    @PostMapping(value = "getAccount")
    public Account getAccount(@RequestBody Long id){
        try {
            return accountService.getById(id);
        }
        catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "addCheckingAccount")
    public String addCheckingAccount(@RequestBody Long userId){
        Account account;
        try {
            account = accountService.add(userId, CHECKING);
        }
        catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
        return "Account ID: " + account.getId() + " for user id " + userId;
    }

    @PostMapping(value = "addSavingAccount")
    public String addSavingAccount(@RequestBody Long userId){
        Account account;
        try {
            account = accountService.add(userId, SAVING);
        }
        catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
        return "Account ID: " + account.getId() + " for user id " + userId;
    }
}
