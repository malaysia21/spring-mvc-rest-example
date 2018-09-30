package guru.springfamework.services;

import guru.springfamework.api.model.CustomerDTO;
import guru.springfamework.controller.CustomerController;
import guru.springfamework.api.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper categoryMapper, CustomerRepository customerRepository) {
        this.customerMapper = categoryMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> collect = customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public CustomerDTO getCustomersById(Long id) {
        return customerRepository.findById(id).map(customer -> {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
            customerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
            return customerDTO;
        })
                .orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        return saveAndReturnDto(customer);
    }

    private CustomerDTO saveAndReturnDto(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnDto.setCustomerUrl(getCustomerURL(savedCustomer.getId()));
        return returnDto;
    }

    @Override
    public CustomerDTO updateCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDto(customer);

    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if(customerDTO.getFirstName()!= null){
                customer.setFirstName(customerDTO.getFirstName());
            }
            if(customerDTO.getLastName()!= null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO customerDTO1 = customerMapper.customerToCustomerDto(customerRepository.save(customer));
            customerDTO1.setCustomerUrl(getCustomerURL(id));

            return customerDTO1;
        }).orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public String getCustomerURL(Long id){
        return CustomerController.BASE_URL + "/"+ id;
    }
}
