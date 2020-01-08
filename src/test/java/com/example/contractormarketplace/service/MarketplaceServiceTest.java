package com.example.contractormarketplace.service;

import com.example.contractormarketplace.entity.Bid;
import com.example.contractormarketplace.repo.BidRepository;
import com.example.contractormarketplace.repo.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MarketplaceServiceTest {

    @Mock
    BidRepository bidRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    MarketplaceService marketplaceService;

    @Test
    @DisplayName("should give the winning bid to the lowest minimum bid, but at the price one penny below the 2nd lowest bid")
    void setWinningBid() {
      long mockProjectId = 1L;
      Bid secondLowestBid = new Bid();
      secondLowestBid.setInitialPrice(BigDecimal.valueOf(10.00));
      secondLowestBid.setMin(BigDecimal.valueOf(5.00));

      Bid lowestBid = new Bid();
      lowestBid.setInitialPrice(BigDecimal.valueOf(11.00)); // initial price is higher
      lowestBid.setMin(BigDecimal.valueOf(4.00)); // but their minimum value is lower

      List<Bid> twoLowestBids = Arrays.asList(lowestBid, secondLowestBid);

      when(bidRepository.findLowestTwoBids(mockProjectId)).thenReturn(twoLowestBids);

      marketplaceService.setWinningBid(mockProjectId);

      ArgumentCaptor<Bid> argumentCaptor = ArgumentCaptor.forClass(Bid.class);
      verify(bidRepository).save(argumentCaptor.capture());
      Bid savedBid = argumentCaptor.getValue();
      assertEquals(savedBid.getId(), lowestBid.getId());
      assertEquals(savedBid.getFinalPrice(), BigDecimal.valueOf(4.99)); // one penny less than the 2nd lowest bid of $5.00
      assertTrue(savedBid.isWin());
    }
}
