package com.ibhsystems.dailybackup.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

public class TestDriveInfo {

	@Test
	public void newDriveInfo() {
		DriveInfo di = new DriveInfo();
		assertNotNull(di.getId());
		assertNotNull(di.getDailySchedule());
		assertNotNull(di.getMonthlySchedule());
		assertNotNull(di.getLastBackup());
	}

	@Test
	public void parseDriveInfo() {
		Properties props = new Properties();
		UUID id = UUID.randomUUID();
		props.setProperty(DriveInfo.ID_PROPERTY, "" + id);
		props.setProperty(DriveInfo.DAILY_SCHEDULE_PROPERTY, "MONDAY");
		DriveInfo di = DriveInfo.fromProperties(props);
		assertEquals(id, di.getId());
		assertTrue(di.getDailySchedule().contains(DayOfWeek.MONDAY));
		assertEquals(1, di.getDailySchedule().size());
	}

	@Test
	public void verifyCompletedBackup() {
		Properties props = new Properties();
		UUID id = UUID.randomUUID();
		props.setProperty(DriveInfo.ID_PROPERTY, "" + id);
		props.setProperty(DriveInfo.DAILY_SCHEDULE_PROPERTY, "");
		props.setProperty(DriveInfo.MONTHLY_SCHEDULE_PROPERTY, "");
		DriveInfo di = DriveInfo.fromProperties(props);

		assertTrue(DriveInfo.isBackupDone(di, Instant.parse("2020-01-01T00:00:00Z"),
				Instant.parse("2020-01-01T00:00:00Z")));
		assertFalse(DriveInfo.isBackupDone(di, Instant.parse("2020-01-01T00:00:00Z"),
				Instant.parse("2020-01-02T00:00:00Z")));
	}
}
