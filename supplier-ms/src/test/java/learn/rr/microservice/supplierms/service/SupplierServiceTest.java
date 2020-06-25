package learn.rr.microservice.supplierms.service;

import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.model.Supplier;
import learn.rr.microservice.supplierms.repository.SupplierByEmailRepository;
import learn.rr.microservice.supplierms.repository.SupplierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SupplierByEmailRepository supplierByEmailRepository;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    @DisplayName("Test for get all Supplier")
    void getAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(Collections.singletonList(getMockSupplier()));
        List<Supplier> response = supplierService.getAllSupplier();
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Test for get supplier by supplierId which is exists in db")
    void getSupplierUsingSupplierId() throws BusinessException {
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.of(getMockSupplier()));
        Supplier response = supplierService.getSupplierById(UUID.randomUUID());
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Test for get supplier by supplierId which is not exists in db")
    void getSupplierUsingSupplierIdNotExists() throws BusinessException {
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Throwable ex = catchThrowable( ()-> supplierService.getSupplierById(UUID.randomUUID()));
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(((BusinessException) ex).getErrorCode()).isEqualTo("SUPPLIER_NOT_FOUND");
        assertThat(((BusinessException) ex).getErrorMessage()).isEqualTo("Supplier not found");
    }

    @Test
    @DisplayName("Test for create supplier with email already registered with other supplier")
    void createSupplierWithAlreadyExistingEmail(){
        when(supplierByEmailRepository.existsById(any(String.class))).thenReturn(true);
        Supplier supplier = getMockSupplier();
        supplier.setId(null);
        Throwable ex = catchThrowable( ()-> supplierService.createSupplier(supplier));
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(((BusinessException) ex).getErrorCode()).isEqualTo("SUPPLIER_ALREADY_EXISTS");
        assertThat(((BusinessException) ex).getErrorMessage()).isEqualTo("Supplier already exists");
    }

    @Test
    @DisplayName("Test for create supplier")
    void createSupplier() throws BusinessException {
        when(supplierByEmailRepository.existsById(any(String.class))).thenReturn(false);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(getMockSupplier());
        Supplier supplier = getMockSupplier();
        supplier.setId(null);
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        assertThat(createdSupplier).isNotNull();
        assertThat(createdSupplier.getId()).isNotNull();
        assertThat(createdSupplier.getName()).isEqualTo(supplier.getName());
        assertThat(createdSupplier.getEmail()).isEqualTo(supplier.getEmail());
    }

    @Test
    @DisplayName("Test for update supplier ")
    void updateSupplier() throws BusinessException {
        Supplier supplier = getMockSupplier();
        supplier.setEmail("updateMockEmail@test.com");
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.of(getMockSupplier()));
        doNothing().when(supplierByEmailRepository).deleteById(any(String.class));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        Supplier updatedSupplier = supplierService.updateSupplier(UUID.randomUUID(), supplier);
        assertThat(updatedSupplier).isNotNull();
        assertThat(updatedSupplier.getId()).isNotNull();
        assertThat(updatedSupplier.getName()).isEqualTo(supplier.getName());
        assertThat(updatedSupplier.getEmail()).isEqualTo(supplier.getEmail());
    }

    @Test
    @DisplayName("Test for update supplier which is not registered")
    void updateSupplierWhichNotExists(){
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Supplier supplier = getMockSupplier();
        supplier.setEmail("updateMockEmail@test.com");
        Throwable ex = catchThrowable( ()-> supplierService.updateSupplier(UUID.randomUUID(),supplier));
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(((BusinessException) ex).getErrorCode()).isEqualTo("SUPPLIER_NOT_FOUND");
        assertThat(((BusinessException) ex).getErrorMessage()).isEqualTo("Supplier not found");
    }

    @Test
    @DisplayName("Test for delete supplier")
    void deleteSupplier(){
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.of(getMockSupplier()));
        supplierService.deleteSupplier(UUID.randomUUID());
        verify(supplierByEmailRepository,times(1)).deleteById(any(String.class));
        verify(supplierRepository,times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Test for delete supplier which is not found in db")
    void deleteSupplierNotExists(){
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        supplierService.deleteSupplier(UUID.randomUUID());
        verify(supplierByEmailRepository,never()).deleteById(any(String.class));
        verify(supplierRepository,never()).deleteById(any(UUID.class));
    }

    private Supplier getMockSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a51"));
        supplier.setEmail("mock@test.com");
        supplier.setName("Mock Supplier");
        return supplier;
    }



}
