package com.tekup.location.security;


import com.tekup.location.entities.User;
import com.tekup.location.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@AllArgsConstructor
public class AuthCtrlView {

    private UserService userService;

    @GetMapping("/signup")
    public String registerUser(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @GetMapping("/signin")
    public String loginUser(){
        return "signin";
    }


    @PostMapping("/signup")
    public String registerUserPost( @ModelAttribute("user") User user ){


        userService.registerUser(user);
        return "redirect:/signin";
    }
}
