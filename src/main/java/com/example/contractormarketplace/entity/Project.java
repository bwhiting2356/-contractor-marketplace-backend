package com.example.contractormarketplace.entity;

import com.example.contractormarketplace.domain.ProjectStatus;
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
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String clientId;
  private Date deadline;
  private String description;
  private BigDecimal maximumBudget;
  private ProjectStatus projectStatus;
}
