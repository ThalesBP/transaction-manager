package br.thales.tools.transactions.manager.service;

import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Transaction;

import java.util.List;

public interface TransactionService {

    public Transaction transfer(Long fromAccountId, Long toAccountId, Float value) throws ServiceException;

    public Transaction cash(Long accountId, Transaction.Type type, Float value) throws ServiceException;

    public Transaction getById(Long id) throws ServiceException;

    public Transaction deleteById(Long id) throws ServiceException;

    public List<Transaction> listAll();
}
