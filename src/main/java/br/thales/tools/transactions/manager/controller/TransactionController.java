package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.database.TransacionRepository;
import br.thales.tools.transactions.manager.external.CashMessage;
import br.thales.tools.transactions.manager.external.TransferMessage;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static br.thales.tools.transactions.manager.model.Transaction.Type.*;
import static br.thales.tools.transactions.manager.utils.Constants.BANK_ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransacionRepository transacionRepository;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping(value = "transfer")
    public Transaction postTransfer(@RequestBody TransferMessage transferMessage){
        Optional<Account> accountFromOptional = accountRepository.findById(transferMessage.getFromId());
        Account accountFrom = checkIfExists(accountFromOptional);
        Optional<Account> accountToOptional = accountRepository.findById(transferMessage.getToId());
        Account accountTo = checkIfExists(accountToOptional);

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(transferMessage.getFromId());
        transaction.setToAccountId(transferMessage.getToId());
        transaction.setValue(transferMessage.getValue());
        transaction.setType(TRANSFER);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);

        accountFrom.setBalance(accountFrom.getBalance() - transferMessage.getValue());
        accountTo.setBalance(accountTo.getBalance() + transferMessage.getValue());
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return transaction;
    }

    @PostMapping(value = "draw")
    public Transaction postDraw(@RequestBody CashMessage cashMessage){
        Optional<Account> accountFromOptional = accountRepository.findById(cashMessage.getId());
        Account accountFrom = checkIfExists(accountFromOptional);

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(DRAW);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);

        accountFrom.setBalance(accountFrom.getBalance() - cashMessage.getValue());
        accountRepository.save(accountFrom);
        return transaction;
    }

    @PostMapping(value = "deposit")
    public Transaction postDeposit(@RequestBody CashMessage cashMessage){
        Optional<Account> accountToOptional = accountRepository.findById(cashMessage.getId());
        Account accountTo = checkIfExists(accountToOptional);

        Transaction transaction = new Transaction();
        transaction.setToAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(DEPOSIT);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);

        accountTo.setBalance(accountTo.getBalance() + cashMessage.getValue());
        accountRepository.save(accountTo);
        return transaction;
    }

    @PostMapping(value = "income")
    public Transaction postIncome(@RequestBody CashMessage cashMessage){
        Optional<Account> accountFromOptional = accountRepository.findById(BANK_ID);
        Account accountFrom = checkIfExists(accountFromOptional);
        Optional<Account> accountToOptional = accountRepository.findById(cashMessage.getId());
        Account accountTo = checkIfExists(accountToOptional);

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(INCOME);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);

        accountFrom.setBalance(accountFrom.getBalance() - cashMessage.getValue());
        accountTo.setBalance(accountTo.getBalance() + cashMessage.getValue());
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return transaction;
    }

    @PostMapping(value = "fees")
    public Transaction postFees(@RequestBody CashMessage cashMessage){
        Optional<Account> accountFromOptional = accountRepository.findById(cashMessage.getId());
        Account accountFrom = checkIfExists(accountFromOptional);
        Optional<Account> accountToOptional = accountRepository.findById(BANK_ID);
        Account accountTo = checkIfExists(accountToOptional);

        Transaction transaction = new Transaction();
        transaction.setToAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(FEES);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);

        accountFrom.setBalance(accountFrom.getBalance() - cashMessage.getValue());
        accountTo.setBalance(accountTo.getBalance() + cashMessage.getValue());
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return transaction;
    }

    @PostMapping(value = "rollBack")
    public String rollBack(@RequestBody Long id){
        Transaction transaction;
        try {
            transaction = transacionRepository.getById(id);
        } catch (EntityNotFoundException ene) {
            System.out.println(ene.getMessage());
            throw new HttpClientErrorException(BAD_REQUEST, "Transaction does not exist");
        }
        transacionRepository.deleteById(id);
        return "Customer deleted: " + transaction.getId();
    }

    private Account checkIfExists(Optional<Account> userFrom) {
        if (userFrom.isEmpty()) {
            throw new HttpClientErrorException(BAD_REQUEST, "Account not found");
        }
        return userFrom.get();
    }
}
