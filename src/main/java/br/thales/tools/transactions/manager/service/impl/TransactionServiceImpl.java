package br.thales.tools.transactions.manager.service.impl;

import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.database.TransactionRepository;
import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.model.Transaction;
import br.thales.tools.transactions.manager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.thales.tools.transactions.manager.model.Transaction.Type.TRANSFER;
import static br.thales.tools.transactions.manager.utils.Constants.BANK_ID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Transaction transfer(Long fromAccountId, Long toAccountId, Float value)  throws ServiceException {
        Account accountFrom = accountRepository.getById(fromAccountId);
        Account accountTo = accountRepository.getById(toAccountId);

        Transaction transaction = Transaction.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .value(value)
                .type(TRANSFER)
                .date(new Date())
                .build();
        transactionRepository.save(transaction);

        accountFrom.setBalance(accountFrom.getBalance() - value);
        accountTo.setBalance(accountTo.getBalance() + value);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        return transaction;
    }

    @Override
    public Transaction cash(Long accountId, Transaction.Type type, Float value)  throws ServiceException {
        if (!accountRepository.existsById(accountId)) {
            throw new ServiceException("Account not found");
        }
        Transaction.TransactionBuilder transactionBulder = Transaction.builder();
        switch (type) {
            case DRAW:
                transactionBulder
                    .fromAccountId(accountId);
                break;
            case DEPOSIT:
                transactionBulder
                    .toAccountId(accountId);
                break;
            case FEES:
                transactionBulder
                    .fromAccountId(accountId)
                    .toAccountId(BANK_ID);
                break;
            case INCOME:
                transactionBulder
                    .fromAccountId(BANK_ID)
                    .toAccountId(accountId);
                break;
            default:
                throw new ServiceException("Wrong transaction type: " + type);
        }
        Transaction transaction = transactionBulder
                .value(value)
                .type(type)
                .date(new Date())
                .build();
        transactionRepository.save(transaction);

        Account accountFrom = accountRepository.getById(transaction.getFromAccountId());
        Account accountTo = accountRepository.getById(transaction.getToAccountId());
        accountFrom.setBalance(accountFrom.getBalance() - value);
        accountTo.setBalance(accountTo.getBalance() + value);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        return transaction;
    }

    @Override
    public Transaction getById(Long id)  throws ServiceException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            return transactionOptional.get();
        } else {
            throw new ServiceException("Transaction not found");
        }
    }

    @Override
    public Transaction deleteById(Long id) throws ServiceException {
        try {
            return transactionRepository.getById(id);
        } catch (EntityNotFoundException ene) {
            throw new ServiceException("Transaction does not exist");
        }
    }

    @Override
    public List<Transaction> listAll() {
        return transactionRepository.findAll();
    }
}
