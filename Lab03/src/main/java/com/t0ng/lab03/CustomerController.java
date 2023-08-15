package com.t0ng.lab03;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    private final List<Customer> customers = new ArrayList<Customer>() {{
        add(new Customer("1010", "John", true, 25));
        add(new Customer("1018", "Peter", true, 24));
        add(new Customer("1019", "Sara", false, 23));
        add(new Customer("1110", "Rose", false, 23));
        add(new Customer("1001", "Emma", false, 30));
    }};

    @RequestMapping("/customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @RequestMapping("/customerbyid/{ID}")
    public Customer getCustomerByID(@PathVariable String ID) {
        for (Customer customer : customers) {
            if (customer.getID().equals(ID)) {
                return customer;
            }
        }
        return null;
    }

    @RequestMapping("/customerbyname/{name}")
    public Customer getCustomerByName(@PathVariable String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    @RequestMapping(value = "/customerDelByid/{ID}", method = {RequestMethod.DELETE})
    public Boolean deleteCustomerByID(@PathVariable String ID) {
        for (Customer customer : customers) {
            if (customer.getID().equals(ID)) {
                customers.remove(customer);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/customerDelByname/{name}", method = {RequestMethod.DELETE})
    public Boolean deleteCustomerByName(@PathVariable String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                customers.remove(customer);
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/addCustomer")
    public boolean addCustomer(
            @RequestParam("id") String ID,
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("sex") String sex
    ) {
        for (Customer customer: customers) {
            if (customer.getID().equals(ID)) {
                return false;
            }
        }
        customers.add(new Customer(ID, name, sex.equalsIgnoreCase("male"), age));
        return true;
    }

    @RequestMapping(value = "/addCustomer2", method = {RequestMethod.POST})
    public boolean addCustomer2(
            @RequestParam("id") String ID,
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("sex") String sex
    ) {
        for (Customer customer: customers) {
            if (customer.getID().equals(ID)) {
                return false;
            }
        }
        customers.add(new Customer(ID, name, sex.equalsIgnoreCase("male"), age));
        return true;
    }
}
