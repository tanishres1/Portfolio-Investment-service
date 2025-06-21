package com.sei.portfolio.service;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.AssetType;
import com.sei.portfolio.model.Investment;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    public Portfolio createPortfolio(PortfolioRequestDTO dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(dto.getName());

        Investment investment = new Investment();
        investment.setName(dto.getInvestmentName());
        investment.setAssetType(AssetType.valueOf(dto.getAssetType()));
        investment.setPortfolio(portfolio);

        portfolio.setInvestments(Arrays.asList(investment));
        return portfolioRepository.save(portfolio);
    }

    // Get List of Portfolio
    public List<Portfolio> getAllPortfolios() {

        return portfolioRepository.findAll();
    }

    public Portfolio updatePortfolio(PortfolioRequestDTO dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Portfolio not found with id " + dto.getId()));

        // Update portfolio name
        portfolio.setName(dto.getName());

        // Create new Investment and set values
        Investment investment = new Investment();
        investment.setName(dto.getInvestmentName()); // ✅ THIS IS IMPORTANT
        investment.setAssetType(AssetType.valueOf(dto.getAssetType()));
        investment.setPortfolio(portfolio);
        // Add to existing investments
        portfolio.getInvestments().add(investment);
        return portfolioRepository.save(portfolio);
    }
    public Portfolio getByPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio id not found: " + id));
    }
    public Portfolio deletePortfolioById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        portfolioRepository.deleteById(id);
        return portfolio; // or return a message/DTO
    }


}
