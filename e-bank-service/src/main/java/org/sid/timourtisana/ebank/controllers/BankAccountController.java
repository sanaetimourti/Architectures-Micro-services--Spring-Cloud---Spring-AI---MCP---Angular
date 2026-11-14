package org.sid.timourtisana.ebank.controllers;
import org.sid.timourtisana.ebank.entities.BankAccount;
import org.sid.timourtisana.ebank.repositories.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
 private final BankAccountRepository repository;
 public BankAccountController(BankAccountRepository repository){this.repository=repository;}
 @GetMapping public List<BankAccount> findAll(){return repository.findAll();}
 @GetMapping("/{id}") public BankAccount findById(@PathVariable Long id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));}
 @GetMapping("/customer/{customerId}") public List<BankAccount> findByCustomer(@PathVariable Long customerId){return repository.findByCustomerId(customerId);}
 @PostMapping public BankAccount create(@RequestParam Long customerId,@RequestParam String accountType,@RequestParam(defaultValue="0.00") BigDecimal initialBalance){
  if(initialBalance.signum()<0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Initial balance cannot be negative");
  BankAccount account=new BankAccount("ACC-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(),initialBalance,accountType,customerId);
  return repository.save(account);
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){if(!repository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"); repository.deleteById(id);}
}