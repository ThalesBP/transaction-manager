package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.TransacionRepository;
import br.thales.tools.transactions.manager.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransacionRepository transacionRepository;

    @GetMapping
    public List<Transaction> listAll(){
        return transacionRepository.findAll();
    }

    @PostMapping
    public Transaction save(@RequestBody Transaction transaction){
        return transacionRepository.save(transaction);
    }
}
