package com.example.contractormarketplace.config;

import com.example.contractormarketplace.entity.Project;
import com.example.contractormarketplace.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class SpringConfig {
  public static final int INTERVAL =  1000; // TODO: put back to one minute..  1000 * 60; // one minute

  @Autowired
  MarketplaceService marketplaceService;

  @Scheduled(fixedDelay = INTERVAL)
  public void scheduleFixedDelayTask() {
    marketplaceService.closeAllOpenExpiredProjects();
    System.out.println(
      "Fixed delay task - " + System.currentTimeMillis() / 1000);
  }
}
