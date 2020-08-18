package com.ibhsystems.dailybackup.service;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {

	static boolean backupIsRunning = false;
	public static void main(String[] args) {
		final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				if (backupIsRunning) {
					return;
				}
			}
		}, 0, 15, TimeUnit.SECONDS);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				if (scheduler != null) {
					scheduler.shutdown();
				}
			}
		}));

	}
}
