package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Create a new request mapping in a method called index that will respond to requests at
 * <br>{@code http://localhost:8080/}<br> Then, return a String with the value index.
 * After creating the controller, you need to go to the resources package, where you will create the templates package
 * (remember, it must be called templates, because Thymeleaf by default looks for views in this package).
 * Create there file index.html.
 */
@Controller
public class StaticWebPageController {
    /**
     * Add an argument to the index method in the form of a map.
     * The next step will be to add elements to the array in a key-value relationship.<br>
     * This code snippet means that we accept a map as an argument, and in the first line of the method,
     * we place a key-value pair into it, where the key is the string "variable" and the value is
     * "My Thymeleaf variable."
     * Next, go to the HTML file and add the following attributes to the {@code <html>} tag.
     * These attributes declare the use of the Thymeleaf library:<br>
     * {@code <!DOCTYPE html>
     * <html lang="en" xmlns:th="http://www.thymeleaf.org">
     * <head>}
     */
    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thymeleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        return "index";
    }
}
