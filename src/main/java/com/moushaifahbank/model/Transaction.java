package com.moushaifahbank.model;
import javax.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="transactions")
public class Transaction{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long accountId; private String type; private String description; private double amount; private LocalDateTime txDate=LocalDateTime.now();
 public Long getId(){return id;} public void setId(Long id){this.id=id;} public Long getAccountId(){return accountId;} public void setAccountId(Long a){this.accountId=a;} public String getType(){return type;} public void setType(String t){this.type=t;} public String getDescription(){return description;} public void setDescription(String d){this.description=d;} public double getAmount(){return amount;} public void setAmount(double a){this.amount=a;} public LocalDateTime getTxDate(){return txDate;} public void setTxDate(LocalDateTime t){this.txDate=t;}}
