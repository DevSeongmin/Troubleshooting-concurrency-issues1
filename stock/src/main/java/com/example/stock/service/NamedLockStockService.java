package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NamedLockStockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void  decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Stock not found"));
		stock.decrease(quantity);

		stockRepository.save(stock);
	}
}
