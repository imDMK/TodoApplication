package me.dmk.app.controller;

import lombok.AllArgsConstructor;
import me.dmk.app.user.User;
import me.dmk.app.user.UserRepository;
import me.dmk.app.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Created by DMK on 17.04.2023
 */
@AllArgsConstructor
@Controller
public class TodoController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping({"/", "home"})
    public String getHome() {
        return "home.html";
    }

    @GetMapping("/panel")
    public String getPanel(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);

        return "panel.html";
    }

    @GetMapping("/login")
    public String getLogin() {
        if (this.userService.isAuthenticated()) {
            return "redirect:/panel";
        }

        return "login.html";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        if (this.userService.isAuthenticated()) {
            return "redirect:/panel";
        }

        model.addAttribute("user", new User());

        return "register.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = this.userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Jest już konto o tym adresie email.");
            return "redirect:/register";
        }

        this.userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", "Stworzono konto.");

        return "redirect:/login";
    }
}
