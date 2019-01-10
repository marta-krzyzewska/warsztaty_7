package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceStubImpl implements ExpenseService {

    private List<Expense> expenses = new ArrayList<Expense>() {{
        add(new Expense(1L, new BigDecimal(100), new ExpenseCategory(1L, "kat1", true, null)));
        add(new Expense(2L, new BigDecimal(200.55), new ExpenseCategory(2L, "kat2", true, null)));
        add(new Expense(3L, new BigDecimal(333), new ExpenseCategory(3L, "kat3", true, null)));
    }};

    private List<ExpenseCategory> expenseCategories = new ArrayList<ExpenseCategory>() {{
       add(new ExpenseCategory(4L, "kat4", true, null));
       add(new ExpenseCategory(5L, "kat5", true, null));
       add(new ExpenseCategory(6L, "kat6", true, null));
    }};


    @Override
    public List<Expense> findAll() {
        return this.expenses;
    }

    @Override
    public List<ExpenseCategory> findAllCategories() {
        return this.expenseCategories;
    }

    @Override
    public List<Expense> findByCategory(ExpenseCategory expenseCategory) {
        return this.expenses.stream()
                .filter(p -> p.getExpenseCategory().equals(expenseCategory))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseCategory findCategoryById(Long id) {
        return this.expenseCategories.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Expense> findByCategoryId(Long id) {
        return this.expenses.stream()
                .filter(p -> p.getExpenseCategory().getId().equals(id))
                .collect((Collectors.toList()));
    }

    @Override
    public Expense findById(Long id) {
        return this.expenses.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Expense create(Expense expense) {
        expense.setId(this.expenses.stream().mapToLong(
                p -> p.getId()).max().getAsLong() + 1);
        this.expenses.add(expense);
        return expense;
    }

    @Override
    public Expense edit(Expense expense) {
        for (int i = 0; i < this.expenses.size(); i++) {
            if (Objects.equals(this.expenses.get(i).getId(), expense.getId())) {
                this.expenses.set(i, expense);
                return expense;
            }
        }
        throw  new RuntimeException("Expense not found: " + expense.getId());
    }

    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < this.expenses.size(); i++) {
            if (Objects.equals(this.expenses.get(i).getId(), id)) {
                this.expenses.remove(i);
                return;
            }
        }
        throw  new RuntimeException("Expense not found: " + id);
    }
}