package com.example.stock.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import com.example.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionStockService {

	private final StockService stockService;

	@Transactional
	public void decrease(Long id, Long quantity) {
		startTransaction();

		stockService.decrease(id, quantity);

		endTransaction();
	}
	private void startTransaction() {
		System.out.println("Transaction started");
	}
	
	private void endTransaction() {
		System.out.println("Transaction ended");
	}
}
