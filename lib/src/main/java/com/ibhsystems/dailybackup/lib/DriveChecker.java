package com.ibhsystems.dailybackup.lib;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public abstract class DriveChecker {
	
	public static List<Path> getBackupDrives() {
		final List<Path> result = new LinkedList<Path>();
		FileSystems.getDefault().getRootDirectories().forEach(new Consumer<Path>() {
			@Override
			public void accept(Path path) {
				final Path db = Paths.get(path.toString(), ".dailybackup");
				if (db.toFile().exists()) {
					result.add(path);
				}
			}
		});
		return result;
	}
}
