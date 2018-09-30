package guru.springfamework.services;

import guru.springfamework.api.model.VendorDTO;
import guru.springfamework.api.model.VendorListDTO;
import guru.springfamework.controller.VendorController;
import guru.springfamework.api.mapper.VendorMapper;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    private final static Long ID = 1L;
    private final static Long ID2 = 2L;
    private final static String NAME = "Agnieszka";
    private final static String NAME2 = "Ana";

    @Mock
    VendorRepository vendorRepository;
    VendorService vendorService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendorsTest(){

        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
        given(vendorRepository.findAll()).willReturn(vendors);

        VendorListDTO vendorListDTO = vendorService.getAllVendors();

        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(vendors.size())));
    }

    @Test
    public void getVendorByIdTest(){
        Vendor vendor = getVendor1();

        when(vendorRepository.findById(ID)).thenReturn(Optional.of(vendor));

        VendorDTO vendorById = vendorService.getVendorById(ID);

        then(vendorRepository).should(times(1)).findById(anyLong());

        assertEquals(vendor.getName(), vendorById.getName());
        assertEquals(VendorController.BASE_URL + "/"+ vendor.getId(), vendorById.getVendorURL());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {

        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        vendorService.getVendorById(1L);

        then(vendorRepository).should(times(1)).findById(anyLong());
    }


    @Test
    public void createNewVendorTest(){

        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor1();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO newVendor = vendorService.createNewVendor(vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));

        assertEquals(vendorDTO.getName(), newVendor.getName());
        assertEquals(VendorController.BASE_URL + "/"+ vendor.getId(), newVendor.getVendorURL());
    }



    @Test
    public void updateVendorByDtoTest(){

        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor1();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO newVendor = vendorService.updateVendorByDTO(ID, vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));

        assertEquals(vendorDTO.getName(), newVendor.getName());
        assertEquals(VendorController.BASE_URL + "/"+ vendor.getId(), newVendor.getVendorURL());
    }


    @Test
    public void patchVendorTest(){

        VendorDTO vendorDTO = getVendorDTO2();

        Vendor vendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO newVendor = vendorService.patchVendor(vendor.getId(), vendorDTO);

        assertEquals(vendorDTO.getName(), newVendor.getName());
        assertEquals(VendorController.BASE_URL + "/"+ vendor.getId(), newVendor.getVendorURL());
    }


    @Test
    public void deleteCustomerById() {
        vendorRepository.deleteById(ID);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME2);
        vendor.setId(ID2);
        return vendor;
    }

    private VendorDTO getVendorDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        return vendorDTO;
    }

    private VendorDTO getVendorDTO2() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME2);
        return vendorDTO;
    }


}
