package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.UserDbStore;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public List<User> listOfUsers() {
        return store.findAllUsers();
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }

    public Optional<User> findUserByEmail(String email) {
        return store.findUserByEmail(email);
    }
}
