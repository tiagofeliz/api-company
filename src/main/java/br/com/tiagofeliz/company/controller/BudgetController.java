package br.com.tiagofeliz.company.controller;

import br.com.tiagofeliz.company.model.dto.BudgetDto;
import br.com.tiagofeliz.company.model.entity.Budget;
import br.com.tiagofeliz.company.model.form.BudgetForm;
import br.com.tiagofeliz.company.model.form.UpdateBudgetForm;
import br.com.tiagofeliz.company.service.impl.BudgetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetServiceImpl budgetService;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto> show(@PathVariable Long id){
        Optional<BudgetDto> budget = budgetService.findById(id);
        if(!budget.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(budget.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BudgetDto> create(@RequestBody @Valid BudgetForm form, UriComponentsBuilder builder){
        Budget budget = form.toEntity();
        BudgetDto dto = budgetService.create(budget);

        URI uri = builder.path("/budget/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BudgetDto> update(@RequestBody @Valid UpdateBudgetForm form, @PathVariable Long id){
        Budget budget = form.toEntity();
        return ResponseEntity.ok(budgetService.update(budget, id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<BudgetDto> delete(@PathVariable Long id){
        budgetService.delete(id);
        return ResponseEntity.ok().build();
    }

}
