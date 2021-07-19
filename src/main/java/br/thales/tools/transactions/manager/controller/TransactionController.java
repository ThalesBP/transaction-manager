package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.TransacionRepository;
import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.external.CashMessage;
import br.thales.tools.transactions.manager.external.TransferMessage;
import br.thales.tools.transactions.manager.model.Transaction;
import br.thales.tools.transactions.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static br.thales.tools.transactions.manager.model.Transaction.Type.*;
import static br.thales.tools.transactions.manager.utils.Constants.BANK;
import static br.thales.tools.transactions.manager.utils.Constants.BANK_ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransacionRepository transacionRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "transfer")
    public Transaction postTransfer(@RequestBody TransferMessage transferMessage){
        Optional<User> userFrom = userRepository.findById(transferMessage.getFromId());
        checkIfExisits(userFrom, "User From not found");
        Optional<User> userTo = userRepository.findById(transferMessage.getToId());
        checkIfExisits(userTo, "User To not found");

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(transferMessage.getFromId());
        transaction.setToAccountId(transferMessage.getToId());
        transaction.setValue(transferMessage.getValue());
        transaction.setType(TRANSFER);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);
        return transaction;
    }

    @PostMapping(value = "draw")
    public Transaction postDraw(@RequestBody CashMessage cashMessage){
        Optional<User> userFrom = userRepository.findById(cashMessage.getId());
        checkIfExisits(userFrom, "User not found");

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(DRAW);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);
        return transaction;
    }

    @PostMapping(value = "deposit")
    public Transaction postDeposit(@RequestBody CashMessage cashMessage){
        Optional<User> userTo = userRepository.findById(cashMessage.getId());
        checkIfExisits(userTo, "User not found");

        Transaction transaction = new Transaction();
        transaction.setToAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(DEPOSIT);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);
        return transaction;
    }

    @PostMapping(value = "income")
    public Transaction postIncome(@RequestBody CashMessage cashMessage){
        Optional<User> userFrom = userRepository.findById(BANK_ID);
        checkBankUser(userFrom);
        Optional<User> userTo = userRepository.findById(cashMessage.getId());
        checkIfExisits(userTo, "User From not found");

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(INCOME);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);
        return transaction;
    }

    @PostMapping(value = "fees")
    public Transaction postFees(@RequestBody CashMessage cashMessage){
        Optional<User> userFrom = userRepository.findById(cashMessage.getId());
        checkIfExisits(userFrom, "User From not found");
        Optional<User> userTo = userRepository.findById(BANK_ID);
        checkBankUser(userTo);

        Transaction transaction = new Transaction();
        transaction.setToAccountId(cashMessage.getId());
        transaction.setValue(cashMessage.getValue());
        transaction.setType(FEES);
        transaction.setDate(new Date());
        transacionRepository.save(transaction);
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

    private void checkIfExisits(Optional<User> userFrom, String s) {
        if (userFrom.isEmpty()) {
            throw new HttpClientErrorException(BAD_REQUEST, s);
        }
    }

    private void checkBankUser(Optional<User> userBank) {
        if (userBank.isEmpty()) {
            throw new HttpClientErrorException(BAD_REQUEST, "Contact your administrator");
        } else {
            if (!BANK.equals(userBank.get().getName())) {
                throw new HttpClientErrorException(INTERNAL_SERVER_ERROR, "Contact your administrator");
            }
        }
    }
}
