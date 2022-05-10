package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", service.findAll());
        return "items";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model) {
        model.addAttribute("items", service.trueItem());
        return "items";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", service.falseItem());
        return "items";
    }

    @GetMapping("/formAddItem")
    public String formAddPost(Model model) {
        model.addAttribute("item",
                new Item(1, "Новая задача",
                        "Заполните описание",
                        null,
                        false)
        );
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) throws IOException {
        service.add(item);
        item.setCreated(LocalDateTime.now());
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        service.update(item);
        return "redirect:/items";
    }

    @GetMapping("/formDescItem/{itemId}")
    public String formDescItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "descItem";
    }

    @GetMapping("/updateTaskToTrue/{itemId}")
    public String updateTaskToTrue(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        service.updateToTrue(id);
        return "redirect:/items";
    }

    @GetMapping("/taskToFalse/{itemId}")
    public String taskToFalse(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "falseTask";

    }

    @GetMapping("/deleteTask/{itemId}")
    public String deleteTask(@PathVariable("itemId") int id) {
        service.delete(id);
        return "redirect:/items";
    }

}
