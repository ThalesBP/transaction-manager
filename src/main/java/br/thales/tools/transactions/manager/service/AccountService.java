package br.thales.tools.transactions.manager.service;

import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Account;

import java.util.List;

public interface AccountService {

    public Account add(Long userId, Account.Type type) throws ServiceException;

    public Account getById(Long id) throws ServiceException;

    public List<Account> listAll();
}
