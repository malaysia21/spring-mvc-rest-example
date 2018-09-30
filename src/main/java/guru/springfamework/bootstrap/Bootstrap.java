package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        loadCategories();
        loadCustomer();
        loadVendors();
    }

    private void loadCustomer() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Ana");
        customer1.setLastName("Nowak");

        Customer customer2 = new Customer();
        customer2.setFirstName("Ana");
        customer2.setLastName("Nowak");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        log.info("Data loaded " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
        log.info("Data loaded " + categoryRepository.count());
    }

    private void loadVendors(){
        Vendor vendor = new Vendor();
        vendor.setName("Agnieszka");

        Vendor vendor1 = new Vendor();
        vendor1.setName("Anna");

        vendorRepository.save(vendor);
        vendorRepository.save(vendor1);

        log.info("Data loaded " + vendorRepository.count());
    }
}
