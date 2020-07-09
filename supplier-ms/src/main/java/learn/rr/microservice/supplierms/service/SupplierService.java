package learn.rr.microservice.supplierms.service;

import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.exception.ErrorCode;
import learn.rr.microservice.supplierms.model.Supplier;
import learn.rr.microservice.supplierms.model.SupplierByEmail;
import learn.rr.microservice.supplierms.repository.SupplierByEmailRepository;
import learn.rr.microservice.supplierms.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SupplierService {
    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    private SupplierRepository supplierRepository;
    private SupplierByEmailRepository supplierByEmailRepository;

    public SupplierService(SupplierRepository supplierRepository, SupplierByEmailRepository supplierByEmailRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierByEmailRepository = supplierByEmailRepository;
    }

    public Supplier getSupplierById(UUID id) throws BusinessException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (!supplierOptional.isPresent()) {
            log.error("Supplier not found for supplier_id={}",id);
            throw  new BusinessException(ErrorCode.ERROR_CODE_SUPPLIER_NOT_FOUND,"Supplier not found");
        }
        return supplierOptional.get();
    }

    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll();
    }

    public Supplier createSupplier(Supplier supplier) throws BusinessException {
        boolean isSupplierExists = supplierByEmailRepository.existsById(supplier.getEmail());
        if(isSupplierExists){
            log.error("Supplier already exists for given email.");
            throw new BusinessException(ErrorCode.ERROR_CODE_SUPPLIER_ALREADY_EXISTS,"Supplier already exists");
        }
        supplier.setId(UUID.randomUUID());
        return createSupplierForAllEntities(supplier);
    }

    private Supplier createSupplierForAllEntities(Supplier supplier) {
        log.info("START| create supplier");
        Supplier savedSupplier = supplierRepository.save(supplier);
        saveSupplierByEmail(supplier);
        log.info("END| create supplier");
        return savedSupplier;
    }

    private void saveSupplierByEmail(Supplier supplier) {
        log.info("START| create supplier by email.");
        SupplierByEmail supplierByEmail = new SupplierByEmail();
        BeanUtils.copyProperties(supplier,supplierByEmail);
        supplierByEmailRepository.save(supplierByEmail);
        log.info("END| create supplier by email.");
    }

    public Supplier updateSupplier(UUID id,Supplier supplier) throws BusinessException {
        log.info("START| update supplier");
        Optional<Supplier> savedSupplier = supplierRepository.findById(id);
        if (!savedSupplier.isPresent()) {
            log.error("Supplier not found for supplier_id={}",id);
            throw new BusinessException(ErrorCode.ERROR_CODE_SUPPLIER_NOT_FOUND,"Supplier not found");
        }
        supplierByEmailRepository.deleteById(savedSupplier.get().getEmail());
        supplier.setId(id);
        Supplier  updatedSupplier = supplierRepository.save(supplier);
        saveSupplierByEmail(updatedSupplier);
        log.info("END| update supplier");
        return updatedSupplier;
    }

    public void deleteSupplier(UUID id){
        log.info("START| delete supplier");
        Optional<Supplier> savedSupplier = supplierRepository.findById(id);
        if(savedSupplier.isPresent()){
            log.info("supplier exisist for supplier_id={}, deleteing entry",id);
            supplierByEmailRepository.deleteById(savedSupplier.get().getEmail());
            supplierRepository.deleteById(id);
        }
        log.info("END| delete supplier");
    }
}
