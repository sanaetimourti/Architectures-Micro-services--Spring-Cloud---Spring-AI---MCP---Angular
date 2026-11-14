package org.sid.timourtisana.ebank.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="bank_accounts")
public class BankAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, unique=true) private String accountNumber;
 @Column(nullable=false, precision=19, scale=2) private BigDecimal balance;
 @Column(nullable=false) private String accountType;
 @Column(nullable=false) private Long customerId;
 public BankAccount() {}
 public BankAccount(String accountNumber, BigDecimal balance, String accountType, Long customerId) {
  this.accountNumber=accountNumber; this.balance=balance; this.accountType=accountType; this.customerId=customerId;
 }
 public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public String getAccountNumber(){return accountNumber;} public void setAccountNumber(String v){this.accountNumber=v;}
 public BigDecimal getBalance(){return balance;} public void setBalance(BigDecimal v){this.balance=v;}
 public String getAccountType(){return accountType;} public void setAccountType(String v){this.accountType=v;}
 public Long getCustomerId(){return customerId;} public void setCustomerId(Long v){this.customerId=v;}
}