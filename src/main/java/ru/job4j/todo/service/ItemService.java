package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistance.ItemDbStore;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class ItemService {

    private final ItemDbStore store;

    public ItemService(ItemDbStore store) {
        this.store = store;
    }

    public Collection<Item> findAll() {
        return store.findAllItems();
    }

    public void add(Item item) {
        item.setCreated(LocalDateTime.now());
        store.add(item);
    }

    public List<Item> trueItem() {
        return store.findTrueItems();
    }

    public List<Item> falseItem() {
        return store.findFalseItems();
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public Item findByIdWithCategories(int id) {
        return store.findByIdWithCategories(id);
    }

    public boolean delete(int idItem) {
        findById(idItem);
        return store.delete(idItem);
    }

    public void update(Item item) {
        store.update(item);
    }

    public void updateToTrue(int idItem) {
        store.updateByIdToTrue(idItem);
    }

    public void updateToFalse(int idItem) {
        store.updateByIdToFalse(idItem);
    }
}
