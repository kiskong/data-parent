package com.cingk.datameta.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-15
 */
@Service
public class DruidService {

	public void getData() {

		ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("druid-pool-").build();
		ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);
		for (int i = 0; i < 600; i++) {
			executorService.execute(new SubThread());
		}
		executorService.shutdown();

	}


	class SubThread implements Runnable {
		Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}


		}
	}

}
