package br.thales.tools.transactions.manager.service;

import br.thales.tools.transactions.manager.error.ServiceException;
import br.thales.tools.transactions.manager.model.User;

import java.util.List;

public interface UserService {

    public User add(String name, User.Type type) throws ServiceException;

    public User getById(Long id) throws ServiceException;

    public List<User> listAll();
}
