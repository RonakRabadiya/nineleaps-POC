package learn.rr.microservice.supplierms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.model.Supplier;
import learn.rr.microservice.supplierms.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "Supplier microservice", value = "Supplier Controller" ,description = "Controller to support all supplier related operations")
@Slf4j
public class SupplierController {

	private SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@ApiOperation(value = "Retrive all available supplier",produces = "application/json")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,headers = "API-VERSION=1")
	public List<Supplier> getAllSuppliers() {
	    log.info("[GET] request received for get all supplier");
		return supplierService.getAllSupplier();
	}

	@ApiOperation(value = "Retrieve supplier by supplier id")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") UUID id) throws BusinessException {
	    log.info("[GET] request received for get supplier by id");
		Supplier supplier = supplierService.getSupplierById(id);
		return new ResponseEntity<>(supplier, HttpStatus.OK);
	}

	@ApiOperation(value = "Create new Supplier")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,headers = "API-VERSION=1")
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) throws BusinessException {
		log.info("[POST] request received for create supplier");
	    supplierService.createSupplier(supplier);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update an existing supplier")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,headers = "API-VERSION=1")
	public ResponseEntity<Supplier> updateSupplier(@PathVariable("id") UUID id,
			@RequestBody Supplier supplier) throws BusinessException {
	    log.info("[PUT] request received for update supplier. supplier_id={}",id);
		Supplier updatedSupplier = supplierService.updateSupplier(id,supplier);
		return new ResponseEntity<Supplier>(updatedSupplier, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Supplier")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,headers = "API-VERSION=1")
	public ResponseEntity<Void> deleteSupplier(@PathVariable("id") UUID id) {
	    log.info("[DELETE] request received for delete supplier. Supplier_id={}",id);
		supplierService.deleteSupplier(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}



}
