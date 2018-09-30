package guru.springfamework.services;

import guru.springfamework.api.model.VendorDTO;
import guru.springfamework.api.model.VendorListDTO;
import guru.springfamework.api.mapper.VendorMapper;
import guru.springfamework.controller.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }


    public String getCustomerURL(Long id){
        return VendorController.BASE_URL + "/"+ id;
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendors = vendorRepository.findAll().stream().
                map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorURL(getCustomerURL(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());

        return new VendorListDTO(vendors);
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorURL(getCustomerURL(vendor.getId()));
            return vendorDTO;
        })
                .orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDto(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    private VendorDTO saveAndReturnDto(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);
        returnDto.setVendorURL(getCustomerURL(savedVendor.getId()));
        return returnDto;
    }

    @Override
    public VendorDTO updateVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDto(vendor);

    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName()!= null){
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO vendorDTO1 = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            vendorDTO1.setVendorURL(getCustomerURL(id));

            return vendorDTO1;
        }).orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
