package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.external.CashMessage;
import br.thales.tools.transactions.manager.external.TransferMessage;
import br.thales.tools.transactions.manager.model.Transaction;
import br.thales.tools.transactions.manager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static br.thales.tools.transactions.manager.model.Transaction.Type.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "listAll")
    public List<Transaction> listAll(){
        return transactionService.listAll();
    }

    @PostMapping(value = "transfer")
    public Transaction postTransfer(@RequestBody TransferMessage transferMessage){
        try {
            return transactionService.transfer(transferMessage.getFromId(), transferMessage.getToId(), transferMessage.getValue());
        } catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "draw")
    public Transaction postDraw(@RequestBody CashMessage cashMessage){
        try {
            return transactionService.cash(cashMessage.getId(), DRAW, cashMessage.getValue());
        } catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "deposit")
    public Transaction postDeposit(@RequestBody CashMessage cashMessage){
        try {
            return transactionService.cash(cashMessage.getId(), DEPOSIT, cashMessage.getValue());
        } catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "income")
    public Transaction postIncome(@RequestBody CashMessage cashMessage){
        try {
            return transactionService.cash(cashMessage.getId(), INCOME, cashMessage.getValue());
        } catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "fees")
    public Transaction postFees(@RequestBody CashMessage cashMessage){
        try {
            return transactionService.cash(cashMessage.getId(), FEES, cashMessage.getValue());
        } catch (ServiceException e) {
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "rollBack")
    public String rollBack(@RequestBody Long id){
        Transaction transaction;
        try {
            transaction = transactionService.deleteById(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            throw new HttpClientErrorException(BAD_REQUEST, e.getMessage());
        }
        return "Customer deleted: " + transaction.getId();
    }
}
