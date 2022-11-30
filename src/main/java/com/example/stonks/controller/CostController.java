package com.example.stonks.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.stonks.dto.CategoryDto;
import com.example.stonks.dto.CostDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.User;
import com.example.stonks.service.CategoryService;
import com.example.stonks.service.CostService;
import com.example.stonks.service.UserService;

@Controller
public class CostController {
    
    private CostService costService;
    private UserService userService;
    private CategoryService categoryService;

    public CostController(CostService costService, UserService userService, CategoryService categoryService) {
        this.costService = costService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String costs(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername(); // returns email(!)
        } else {
            email = principal.toString();
        }
        //UserDto user = userService.findUserByEmail(email);
        UserDto user = userService.getCurrentUser();
        List<CostDto> costs = costService.findAllCostsForUser(user);
        Map<String, Integer> categories_map = costService.createCategroriesMap(user);
        
        Long total_credit = costService.getTotalCredit(user);
        Long total_debit = costService.getTotalDebit(user);
        
        String pattern = "dd/MM/yyyy";
        DateFormat date_format = new SimpleDateFormat(pattern);

        model.addAttribute("costs", costs);
        model.addAttribute("category_names", categories_map.keySet());
        model.addAttribute("category_costs", categories_map.values());
        model.addAttribute("date_format", date_format);
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_debit", total_debit);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/cost")
    public String showCostForm(Model model) {
        CostDto cost = new CostDto();
        UserDto user = userService.getCurrentUser();
        List<CategoryDto> categories = categoryService.findCategoriesForUser(user);

        model.addAttribute("cost", cost);
        model.addAttribute("categories", categories);
        return "cost";
    }

    @PostMapping("/cost/save")
    public String cost(@Validated @ModelAttribute("cost")  @DateTimeFormat(pattern = "dd.MM.yyyy") CostDto costDto,
                        BindingResult result, Model model) {     
                            
        System.out.println(costDto.toString());
        costService.saveCost(costDto, userService.getCurrentUser());
        return "redirect:/";
    }
}
