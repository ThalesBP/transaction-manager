package br.thales.tools.transactions.manager.service.impl;

import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.thales.tools.transactions.manager.utils.Constants.Status.ACTIVE;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Account add(Long userId, Account.Type type) throws ServiceException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new ServiceException("User not found");
        }
        Account account = Account.builder()
                .userId(userId)
                .status(ACTIVE)
                .type(type)
                .date(new Date())
                .build();
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account getById(Long id) throws ServiceException {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new ServiceException("Account not found");
        }
    }

    @Override
    public List<Account> listAll() {
        return accountRepository.findAll();
    }
}
