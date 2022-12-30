package com.example.stonks.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.stonks.dto.CategoryDto;
import com.example.stonks.dto.CostDto;
import com.example.stonks.dto.TargetDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.service.*;


@Controller
public class CostController {

    private static final String EURO = "EURRUB";
    private static final String DOLLAR = "USDRUB";
    
    private CostService costService;
    private UserService userService;
    private CategoryService categoryService;
    private CurrencyService currencyService;
    private TargetService targetService;

    public CostController(CostService costService,
                          UserService userService,
                          CategoryService categoryService,
                          CurrencyService currencyService,
                          TargetService targetService) {
        this.costService = costService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.targetService = targetService;
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

        List<TargetDto> targets = targetService.findTargetsForUser(user);
        List<CategoryDto> categories = categoryService.findCategoriesForUser(user);
        
        CostDto new_cost = new CostDto();
        CategoryDto new_category = new CategoryDto();
        TargetDto new_target = new TargetDto();

        model.addAttribute("new_cost", new_cost);
        model.addAttribute("new_category", new_category);
        model.addAttribute("new_target", new_target);

        model.addAttribute("user", user);
        model.addAttribute("costs", costs);
        model.addAttribute("categories", categories);
        model.addAttribute("targets", targets);

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
                            
        costService.saveCost(costDto, userService.getCurrentUser());
        return "redirect:/";
    }

    @PostMapping("/category/save")
    public String category(@Validated @ModelAttribute("category") CategoryDto categoryDto, Model model) {

        categoryService.saveCategory(categoryDto, userService.getCurrentUser());
        return "redirect:/";
    }

    @PostMapping("/target/save")
    public String target(@Validated @ModelAttribute("target") TargetDto targetDto, Model model) {
        targetDto.setCurrent_sum(0L);
        targetDto.setProgress(0L);
        targetService.saveTarget(targetDto, userService.getCurrentUser());
        return "redirect:/";
    }
 
    @PostMapping("/target/update")
    public String updateTarget(@Validated @ModelAttribute("target") TargetDto updTargetDto, Model model) {
        UserDto user = userService.getCurrentUser();
        System.out.println(updTargetDto.getName());
        TargetDto targetDto = targetService.findTargetForUserByName(user, updTargetDto.getName());
        targetDto.updateCurrentSum(updTargetDto.getCurrent_sum());
        targetService.updateTarget(targetDto);
        return "redirect:/";
    }
}
