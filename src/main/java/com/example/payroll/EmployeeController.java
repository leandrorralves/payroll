package com.example.payroll;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/employees/name")
    List<Employee> allName(@RequestParam String name) {
        return repository.findByName(name);
    }

    @GetMapping("/employees/firstName")
    List<Employee> allFirstName(@RequestParam String firstName) { return repository.findByFirstName(firstName); }

    @GetMapping("/employees/lastName")
    List<Employee> allLastName(@RequestParam String lastName) {
        return repository.findByLastName(lastName);
    }

    @GetMapping("/employees/roleAndName")
    List<Employee> allRoleAndName(@RequestParam String role, @RequestParam String name) {
        return repository.findUserByRoleAndName(role, name);
    }
}
