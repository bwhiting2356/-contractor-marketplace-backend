package com.example.contractormarketplace.config;

import com.example.contractormarketplace.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SpringConfig {
  public static final int INTERVAL =  1000 * 60; // one minute

  @Autowired
  MarketplaceService marketplaceService;

  @Scheduled(fixedDelay = INTERVAL)
  public void scheduleFixedDelayTask() {
    marketplaceService.closeAllOpenExpiredProjects();
  }
}
