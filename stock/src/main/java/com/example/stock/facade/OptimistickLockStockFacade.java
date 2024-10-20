package com.example.stock.facade;

import org.springframework.stereotype.Component;

import com.example.stock.service.OptimisticLockStockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OptimistickLockStockFacade {

	private final OptimisticLockStockService optimisticLockStockService;

	public void decrease(Long id, Long quantity) throws InterruptedException {
		while (true){
			try {
				optimisticLockStockService.decrease(id, quantity);
				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}
}
