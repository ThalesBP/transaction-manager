package br.thales.tools.transactions.manager.service.impl;

import br.thales.tools.transactions.manager.database.UserRepository;
import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.Customer;
import br.thales.tools.transactions.manager.model.User;
import br.thales.tools.transactions.manager.service.UserService;
import br.thales.tools.transactions.manager.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User add(String name, User.Type type)  throws ServiceException {
        if (type.equals(User.Type.CUSTOMER) && !Customer.validateName(name)) {
            throw new ServiceException("It must have at least name and sure name");
        }
        User user = User.builder()
                .name(name)
                .type(type)
                .status(Constants.Status.ACTIVE)
                .date(new Date())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public User getById(Long id)  throws ServiceException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ServiceException("User not found");
        }
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }
}
