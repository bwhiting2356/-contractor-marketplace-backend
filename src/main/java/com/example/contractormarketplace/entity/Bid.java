package com.example.contractormarketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bid", indexes = {@Index(name="min_bid_price", columnList = "min, initialPrice, project_id") })
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  BigDecimal initialPrice;
  BigDecimal finalPrice;
  boolean win;

  @JsonIgnore
  BigDecimal min;

  String contractorId;
  Date date;

  @JsonIgnore
  @ManyToOne
  private Project project;
}
