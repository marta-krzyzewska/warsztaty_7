package pl.coderslab.warsztaty_7.controller.tests;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.util.List;

@Controller
@RequestMapping("/home/income")
public class TestIncomeController {

    private final IncomeService incomeService;

    @Autowired
    public TestIncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @ModelAttribute(name = "incomeCategories")
    public List<IncomeCategory> findAllCategories(){
        return incomeService.findAllCategories();
    }

    @ModelAttribute(name = "incomes")
    public List<Income> findAllIncomes(){
        return incomeService.findAll();
    }

    @ModelAttribute(name = "income")
    public Income createEmptyIncome(){
        return new Income();
    }

    @GetMapping(value = "/")
    public String allExpenses() {
        return "test_incomes";
    }

    @GetMapping(value = "/category/{id}")
    public String findIncomesByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("incomes", incomeService.findByCategoryId(id));
        return "test_incomes";
    }

    @GetMapping(value = "/edit/{id}")
    public String editIncome(@PathVariable Long id, Model model) {
        model.addAttribute("income", incomeService.findById(id));
        return "test_editIncome";
    }

    @PostMapping(value = "/")
    public String createIncome(@ModelAttribute Income income) {
        Long categoryId = income.getIncomeCategory().getId();
        income.setIncomeCategory(incomeService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
        incomeService.create(income);
        return "test_incomes";
    }

    @PostMapping(value = "/edit/{id}")
    public String editIncome(@ModelAttribute Income income) {
        Long categoryId = income.getIncomeCategory().getId();
        income.setIncomeCategory(incomeService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
        incomeService.edit(income);
        return "redirect:http://localhost:8080/home/income/";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeService.deleteById(id);
        return "redirect:http://localhost:8080/home/income/";
    }
}