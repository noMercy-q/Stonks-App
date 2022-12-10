package com.example.stonks.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
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
import com.example.stonks.service.CurrencyService;
import com.example.stonks.service.UserService;

@Controller
public class CostController {

    private static final String EURO = "EURRUB";
    private static final String DOLLAR = "USDRUB";
    
    private CostService costService;
    private UserService userService;
    private CategoryService categoryService;
    private CurrencyService currencyService;

    public CostController(CostService costService, UserService userService,
                          CategoryService categoryService, CurrencyService currencyService) {
        this.costService = costService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String costs(Model model) {
        UserDto user = userService.getCurrentUser();
        List<CostDto> costs = costService.findAllCostsForUser(user);
        Map<String, Integer> debits_map = costService.createCategroriesMap(user, true);
        Map<String, Integer> credits_map = costService.createCategroriesMap(user, false);
        
        Long total_credit = costService.getTotalCredit(user);
        Long total_debit = costService.getTotalDebit(user);
        
        String pattern = "dd/MM/yyyy";
        DateFormat date_format = new SimpleDateFormat(pattern);

        String dollar_rate = currencyService.getCurrencyRate(DOLLAR);
        String euro_rate = currencyService.getCurrencyRate(EURO);

        model.addAttribute("user", user);
        model.addAttribute("costs", costs);

        model.addAttribute("dollar_rate", dollar_rate);
        model.addAttribute("euro_rate", euro_rate);

        model.addAttribute("debit_labels", debits_map.keySet());
        model.addAttribute("debit_values", debits_map.values());
        model.addAttribute("credit_labels", credits_map.keySet());
        model.addAttribute("credit_values", credits_map.values());

        model.addAttribute("date_format", date_format);
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_debit", total_debit);
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
