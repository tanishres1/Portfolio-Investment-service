package com.sei.portfolio;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.repository.PortfolioRepository;
import com.sei.portfolio.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;
    @InjectMocks
    private PortfolioService portfolioService;

    public PortfolioServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePortfolio(){
        PortfolioRequestDTO dto = new PortfolioRequestDTO();
        dto.setName("Retirement Plan");
        dto.setInvestmentName("HDFC Mutual Fund");
        dto.setAssetType("MUTUAL_FUND");
        List<PortfolioRequestDTO> dtos =Collections.singletonList(dto);
        when(portfolioRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        List<Portfolio> result =portfolioService.createPortfolio(dtos);
        assertEquals(1, result.size());
        assertEquals("Retirement Plan", result.get(0).getName());
        assertEquals(1, result.get(0).getInvestments().size());

    }
    @Test
    public void testGetAllPortfolios(){
        List<Portfolio> mockList=Arrays.asList(new Portfolio(), new Portfolio());
        when(portfolioRepository.findAll()).thenReturn(mockList);
        List<Portfolio>result=portfolioService.getAllPortfolios();
        assertEquals(2, result.size());

    }
}
