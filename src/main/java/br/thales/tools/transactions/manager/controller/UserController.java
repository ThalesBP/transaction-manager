package br.thales.tools.transactions.manager.controller;

import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.error.ModelException;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.model.User.Type;
import br.thales.tools.transactions.manager.utils.Constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static br.thales.tools.transactions.manager.utils.Constants.Strings.SPACE;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @PostMapping
    public String addCustomer(@RequestParam String name){
        String[] fullName = name.split(SPACE);
        if (fullName.length < 2) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("It must have at least name and sure name");
        }
        User user = new User();
        try {
            user.setFullName(name);
            user.setType(Type.CUSTOMER);
            user.setStatus(Status.ACTIVE);
            user.setDate(new Date());
        }
        catch (ModelException me) {
            System.out.println("Model User: " + me.getMessage());
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me.getMessage());
        }
        userRepository.save(user);
        return "New customer registered: " + user.getFullName();
    }
}
