package by.bsu.orienteering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by alexey.memelov on 21-Dec-18.
 */
@Controller
public class ViewController {

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String load() {
        return "load";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public String rating() {
        return "rating";
    }

    @RequestMapping(value = "/competitions", method = RequestMethod.GET)
    public String competitions() {
        return "competitions";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable String id, ModelMap model) {
        model.addAttribute("id", id);
        return "detail";
    }

    @RequestMapping(value = "/competition/{id}", method = RequestMethod.GET)
    public String competition(@PathVariable String id, ModelMap model) {
        model.addAttribute("id", id);
        return "competition";
    }

}
