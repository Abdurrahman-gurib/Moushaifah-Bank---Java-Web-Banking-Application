package com.moushaifahbank.model;
import javax.persistence.*;
@Entity @Table(name="accounts")
public class Account{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long userId; @Column(unique=true) private String accountNumber; private String type; private double balance; private String status="ACTIVE";
 public Long getId(){return id;} public void setId(Long id){this.id=id;} public Long getUserId(){return userId;} public void setUserId(Long u){this.userId=u;} public String getAccountNumber(){return accountNumber;} public void setAccountNumber(String a){this.accountNumber=a;} public String getType(){return type;} public void setType(String t){this.type=t;} public double getBalance(){return balance;} public void setBalance(double b){this.balance=b;} public String getStatus(){return status;} public void setStatus(String s){this.status=s;}}
