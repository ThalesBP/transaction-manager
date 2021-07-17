package br.thales.tools.transactions.manager.controller;


import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> listAll(){
        return accountRepository.findAll();
    }

    @PostMapping
    public Account save(@RequestBody Account account){
        return accountRepository.save(account);
    }
}
