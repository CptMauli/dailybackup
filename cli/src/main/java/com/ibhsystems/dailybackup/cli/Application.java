package com.ibhsystems.dailybackup.cli;

import com.ibhsystems.dailybackup.lib.DriveChecker;

public class Application {

	public static void main(String[] args) {
		System.err.println(DriveChecker.getBackupDrives());

	}

}
