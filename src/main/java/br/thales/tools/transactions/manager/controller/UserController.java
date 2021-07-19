package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.model.User.Type;
import br.thales.tools.transactions.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "listAll")
    public List<User> listAll(){
        return userService.listAll();
    }

    @PostMapping(value = "getById")
    public User getById(@RequestBody Long id){
        try {
            return userService.getById(id);
        } catch (ServiceException se) {
            throw new HttpClientErrorException(BAD_REQUEST, se.getMessage());
        }
    }

    @PostMapping(value = "addCustomer")
    public String addCustomer(@RequestBody String name){
        User user;
        try {
            user = userService.add(name, Type.CUSTOMER);
        }
        catch (ServiceException se) {
            throw new HttpClientErrorException(BAD_REQUEST, se.getMessage());
        }
        return "New customer registered: " + user.getName() + " with ID: " + user.getId();
    }

    @PostMapping(value = "addBusiness")
    public String addBusiness(@RequestBody String name){
        User user;
        try {
            user = userService.add(name, Type.BUSINESS);
        }
        catch (ServiceException se) {
            throw new HttpClientErrorException(BAD_REQUEST, se.getMessage());
        }
        return "New customer registered: " + user.getName() + " with ID: " + user.getId();
    }
}
