package guru.springfamework.api.mapper;

import guru.springfamework.api.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);


    CustomerDTO customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer (CustomerDTO customerDTO);
}
