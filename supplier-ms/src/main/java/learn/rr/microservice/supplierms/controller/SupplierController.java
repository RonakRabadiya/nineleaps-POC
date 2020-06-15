package learn.rr.microservice.supplierms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.model.Supplier;
import learn.rr.microservice.supplierms.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
@Api(tags = "Supplier microservice", value = "Supplier Controller" ,description = "Controller to support all supplier related operations")
public class SupplierController {

	private SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@ApiOperation(value = "Retrive all available supplier",produces = "application/json")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Supplier> getAllSuppliers() {
		return supplierService.getAllSupplier();
	}

	@ApiOperation(value = "Retrive supplier by supplier id")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") UUID id) throws BusinessException {
		Supplier supplier = supplierService.getSupplierById(id);
		return new ResponseEntity<>(supplier, HttpStatus.OK);
	}

	@ApiOperation(value = "Create new Supplier")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) throws BusinessException {
		supplierService.createSupplier(supplier);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update an existing supplier")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Supplier> updateSupplier(@PathVariable("id") UUID id,
			@RequestBody Supplier supplier) throws BusinessException {
		Supplier updatedSupplier = supplierService.updateSupplier(id,supplier);
		return new ResponseEntity<Supplier>(updatedSupplier, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Supplier")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteSupplier(@PathVariable("id") UUID id) {
		supplierService.deleteSupplier(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}



}
