package com.example.contractormarketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBid {
  private BigDecimal price;
  private BigDecimal min;
  private String contractorId;
  private long projectId;
  private Date date;
}
