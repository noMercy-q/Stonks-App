package com.example.stonks.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.stonks.dto.CostDto;
import com.example.stonks.model.User;
import com.example.stonks.service.CostService;
import com.example.stonks.service.UserService;

@Controller
public class CostController {
    
    private CostService costService;
    private UserService userService;

    public CostController(CostService costService, UserService userService) {
        this.costService = costService;
        this.userService = userService;
    }

    @GetMapping("/costs")
    public String costs(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername(); // returns email(!)
        } else {
            email = principal.toString();
        }
        User user = userService.findUserByEmail(email);
        List<CostDto> costs = costService.findAllCostsForUser(user);
        Long total_credit = costService.getTotalCredit(user);
        Long total_debit = costService.getTotalDebit(user);
        
        String pattern = "dd/MM/yyyy";
        DateFormat date_format = new SimpleDateFormat(pattern);

        model.addAttribute("costs", costs);
        model.addAttribute("date_format", date_format);
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_debit", total_debit);
        return "costs";
    }
}
