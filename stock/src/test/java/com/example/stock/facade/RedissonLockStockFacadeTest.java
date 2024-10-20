package com.example.stock.facade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

@SpringBootTest
class RedissonLockStockFacadeTest {

	@Autowired
	private RedissonLockStockFacade redissonLockStockFacade;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	public void before() {
		stockRepository.save(new Stock(1L, 100L));
	}
	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@Test
	public void 낙관적락_동시에_100개의_요청() throws InterruptedException{
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.execute(() -> {
				try {
					redissonLockStockFacade.decrease(1L, 1L);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		Stock stock = stockRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("재고가 없습니다."));

		Assertions.assertThat(stock.getQuantity()).isEqualTo(0L);
	}

}