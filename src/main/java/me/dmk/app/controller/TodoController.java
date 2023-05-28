package me.dmk.app.controller;

import me.dmk.app.user.User;
import me.dmk.app.user.repository.UserRepository;
import me.dmk.app.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by DMK on 17.04.2023
 */
@Controller
public class TodoController {

    private final UserRepository userRepository;
    private final UserService userService;

    public TodoController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new User());

        return "register.html";
    }

    @GetMapping("/panel")
    public String getPanel(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);

        return "panel.html";
    }

    @PostMapping("/register/submit")
    public String registerSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        User existingUser = this.userRepository.findUserByEmail(user.getEmail());

        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "Jest już konto o tym adresie email.");
            return "redirect:/register";
        }

        this.userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", "Stworzono konto.");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        if (this.userService.isAuthenticated()) {
            return "redirect:/panel";
        }

        return "login.html";
    }
}
