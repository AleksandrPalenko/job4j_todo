package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistance.CategoryDbStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryDbStore store;

    public CategoryService(CategoryDbStore store) {
        this.store = store;
    }

    public Collection<Category> findAll() {
        return store.findAll();
    }

    public Optional<Category> add(Category category) {
        return store.add(category);
    }

    public Category findById(int id) {
        return store.findById(id);
    }
}
