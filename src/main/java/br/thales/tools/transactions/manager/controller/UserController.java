package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.AccountRepository;
import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.model.Account;
import br.thales.tools.transactions.manager.model.Customer;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.model.User.Type;
import br.thales.tools.transactions.manager.utils.Constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "listAll")
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @PostMapping(value = "getById")
    public User getById(@RequestBody Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new HttpClientErrorException(BAD_REQUEST, "User not found");
        }
    }

    @PostMapping(value = "addCustomer")
    public String addCustomer(@RequestBody String name){
        if(Customer.validateName(name)) {
            throw new HttpClientErrorException(BAD_REQUEST, "It must have at least name and sure name");
        }
        User user = new User();
        user.setName(name);
        user.setType(Type.CUSTOMER);
        user.setStatus(Status.ACTIVE);
        user.setDate(new Date());
        userRepository.save(user);
        return "New customer registered: " + user.getName() + " with ID: " + user.getId();
    }

    @PostMapping(value = "addBusiness")
    public String addBusiness(@RequestBody String name){
        User user = new User();
        user.setName(name);
        user.setType(Type.BUSINESS);
        user.setStatus(Status.ACTIVE);
        user.setDate(new Date());
        userRepository.save(user);
        return "New customer registered: " + user.getName() + " with ID: " + user.getId();
    }
}
