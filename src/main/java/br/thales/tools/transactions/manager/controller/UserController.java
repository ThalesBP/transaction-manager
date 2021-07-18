package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.model.Customer;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.model.User.Type;
import br.thales.tools.transactions.manager.utils.Constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import static br.thales.tools.transactions.manager.utils.Constants.Strings.SPACE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "getAllCustomers")
    public List<User> listAll(){
        return userRepository.findAll();
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
