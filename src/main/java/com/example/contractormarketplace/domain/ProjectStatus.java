package com.example.contractormarketplace.domain;

public enum ProjectStatus {
  OPEN("OPEN"), CLOSED("CLOSED");

  private final String name;

  ProjectStatus(final String name) {
    this.name = name;
  }
}
