package com.sei.portfolio;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.repository.PortfolioRepository;
import com.sei.portfolio.service.PortfolioService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


public class PortfolioServiceTest {
    @Mock
    private PortfolioRepository portfolioRepository;
    @InjectMocks
    private PortfolioService portfolioService;

    public PortfolioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePortfolio() {
        PortfolioRequestDTO dto = new PortfolioRequestDTO();
        dto.setName("Retirement Plan");
        dto.setInvestmentName("HDFC Mutual Fund");
        dto.setAssetType("MUTUAL_FUND");
        List<PortfolioRequestDTO> dtos = Collections.singletonList(dto);
        when(portfolioRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        List<Portfolio> result = portfolioService.createPortfolio(dtos);
        assertEquals(1, result.size());
        assertEquals("Retirement Plan", result.get(0).getName());
        assertEquals(1, result.get(0).getInvestments().size());

    }

    @Test
    public void testGetAllPortfolios() {
        List<Portfolio> mockList = Arrays.asList(new Portfolio(), new Portfolio());
        when(portfolioRepository.findAll()).thenReturn(mockList);
        List<Portfolio> result = portfolioService.getAllPortfolios();
        assertEquals(2, result.size());

    }

    @Test
    void testUpdatePortfolio() {
        PortfolioRequestDTO dto = new PortfolioRequestDTO();
        dto.setId(1L);  // ✅ This is the key fix
        dto.setName("Updated Portfolio");
        dto.setInvestmentName("New Investment");
        dto.setAssetType("STOCK");

        Portfolio existing = new Portfolio();
        existing.setId(1L);
        existing.setName("Old Portfolio");
        existing.setInvestments(new ArrayList<>());

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(portfolioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Portfolio updated = portfolioService.updatePortfolio(dto);

        assertEquals("Updated Portfolio", updated.getName());
        assertEquals(1, updated.getInvestments().size());
        assertEquals("New Investment", updated.getInvestments().get(0).getName());
    }

    @Test
    public void testGetByPortfolioById() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(10l);
        when(portfolioRepository.findById(10l)).thenReturn(Optional.of(portfolio));
        Portfolio result = portfolioService.getByPortfolioById(10l);
        assertNotNull(result);
        assertEquals(10l, result.getId());
    }

    @Test
    public void testDeletePortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(20L);
        when(portfolioRepository.findById(20l)).thenReturn(Optional.of(portfolio));
        doNothing().when(portfolioRepository).deleteById(20l);
        Portfolio deleted = portfolioService.deletePortfolioById(20l);
        assertEquals(20l, deleted.getId());
        verify(portfolioRepository, times(1)).deleteById(20L);

    }

    @Test
    public void testGetPortfolioCount() {
        when(portfolioService.getPortfolioCount()).thenReturn(5l);
        long count = portfolioService.getPortfolioCount();
        assertEquals(5l, count);
    }
}
