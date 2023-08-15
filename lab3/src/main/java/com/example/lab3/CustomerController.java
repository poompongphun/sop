package com.example.lab3;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CustomerController {

    public CustomerController() {
        customers = new ArrayList<>();
        customers.add(new Customer("1010", "John", true, 25));
        customers.add(new Customer("1018", "Peter", true, 24));
        customers.add(new Customer("1019", "Sara", false, 23));
        customers.add(new Customer("1110", "Rose", false, 23));
        customers.add(new Customer("1001", "Emma", false, 30));
    }
    private List<Customer> customers;

    @RequestMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return  this.customers;
    }

    @RequestMapping(value = "/customerbyid/{id}")
    public Customer getCustomerByID(@PathVariable("id") String ID) {
        int i;
        for (i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getID().equals(ID)) {
                return this.customers.get(i);
            }
        }
        return null;
    }
    @RequestMapping(value = "/customerbyname/{n}")
    public Customer getCustomerByName(@PathVariable("n") String n) {
        int i;
        for (i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getName().equals(n)) {
                return this.customers.get(i);
            }
        }
        return null;
    }

    @RequestMapping(value = "/customerDelByid/{id}", method = RequestMethod.DELETE)
    public List<Customer> delCustomerByID(@PathVariable("id") String ID) {
        int i;
        for (i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getID().equals(ID)) {
                this.customers.remove(i);
                return this.customers;
            }
        }
        return null;
    }

    @RequestMapping(value = "/customerDelByname/{n}", method = RequestMethod.GET)
    public List<Customer> delCustomerByName(@PathVariable("n") String n) {
        int i;
        for (i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getName().equals(n)) {
                this.customers.remove(i);
                return this.customers;
            }
        }
        return null;
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public List<Customer> addCustomer(@RequestParam("ID") String ID, @RequestParam("n") String n, @RequestParam("s") String s, @RequestParam("a") int a) {
        this.customers.add(new Customer(ID, n, s.toLowerCase().equals("male"), a));
        return this.customers;
    }

}
