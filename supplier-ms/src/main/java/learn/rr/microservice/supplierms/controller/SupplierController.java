package learn.rr.microservice.supplierms.controller;

import java.util.List;
import java.util.UUID;

import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.rr.microservice.supplierms.model.Supplier;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

	private SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Supplier> getAllSuppliers() {
		return supplierService.getAllSupplier();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") UUID id) throws BusinessException {
		Supplier supplier = supplierService.getSupplierById(id);
		return new ResponseEntity<>(supplier, HttpStatus.OK);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) throws BusinessException {
		supplierService.createSupplier(supplier);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> updateSupplier(@PathVariable("id") UUID id,
			@RequestBody Supplier supplier) throws BusinessException {
		Supplier updatedSupplier = supplierService.updateSupplier(id,supplier);
		return new ResponseEntity<Supplier>(updatedSupplier, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteSupplier(@PathVariable("id") UUID id) {
		supplierService.deleteSupplier(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}



}
