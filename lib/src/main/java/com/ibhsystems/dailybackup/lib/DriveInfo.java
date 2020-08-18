package com.ibhsystems.dailybackup.lib;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DriveInfo implements Serializable {

	private static final String DEFAULT_PAST_DATE = "2000-01-01T00:00:00.000Z";

	private static final long serialVersionUID = 335500574049892853L;

	public static final String ID_PROPERTY = "id";

	public static final String DAILY_SCHEDULE_PROPERTY = "dailySchedule";

	public static final String MONTHLY_SCHEDULE_PROPERTY = "monthlySchedule";

	public static final String LAST_BACKUP_PROPERTY = "lastBackup";

	private UUID id;

	private Set<DayOfWeek> dailySchedule;

	private Set<Month> monthlySchedule;

	private Instant lastBackup;

	public DriveInfo() {
		id = UUID.randomUUID();
		dailySchedule = new HashSet<>(Arrays.asList(DayOfWeek.values()));
		monthlySchedule = new HashSet<>(Arrays.asList(Month.values()));
		lastBackup = Instant.parse(DEFAULT_PAST_DATE);
	}

	public static DriveInfo fromProperties(Properties props) {
		DriveInfo result = new DriveInfo();
		result.id = UUID.fromString(props.getProperty(ID_PROPERTY));
		result.dailySchedule = Arrays.asList(//
				props.getProperty(DAILY_SCHEDULE_PROPERTY, "").split(",") //
		).stream() //
				.filter(d -> d != null && !d.isBlank())//
				.map(d -> DayOfWeek.valueOf(d)) //
				.collect(Collectors.toSet());
		result.monthlySchedule = Arrays.asList(//
				props.getProperty(MONTHLY_SCHEDULE_PROPERTY, "").split(",") //
		).stream() //
				.filter(m -> m != null && !m.isBlank())//
				.map(m -> Month.valueOf(m)) //
				.collect(Collectors.toSet());
		result.lastBackup = Instant.parse(props.getProperty(LAST_BACKUP_PROPERTY, DEFAULT_PAST_DATE));
		return result;
	}

	public UUID getId() {
		return id;
	}

	public Set<DayOfWeek> getDailySchedule() {
		return dailySchedule;
	}

	public Set<Month> getMonthlySchedule() {
		return monthlySchedule;
	}

	public Instant getLastBackup() {
		return lastBackup;
	}

	public static boolean isBackupDone(DriveInfo di) {
		return isBackupDone(di, di.getLastBackup(), Instant.now());
	}

	public static boolean isBackupDone(DriveInfo di, Instant timeOfBackup) {
		return isBackupDone(di, timeOfBackup, Instant.now());
	}

	public static boolean isBackupDone(DriveInfo di, Instant timeOfBackup, Instant timeOfCheck) {
		ZonedDateTime zNow = ZonedDateTime.ofInstant(timeOfCheck, ZoneId.systemDefault());
		ZonedDateTime zThen = ZonedDateTime.ofInstant(timeOfBackup, ZoneId.systemDefault());

		// if no schedule is set, then every day is assumed
		if (di.getDailySchedule().isEmpty() && di.getMonthlySchedule().isEmpty()) {
			return zThen.getLong(ChronoField.EPOCH_DAY) == zNow.getLong(ChronoField.EPOCH_DAY);
		}
		// daily, overrides monthly
		if (!di.getDailySchedule().isEmpty()) {
			return zThen.getLong(ChronoField.EPOCH_DAY) == zNow.getLong(ChronoField.EPOCH_DAY);
		} else if (di.getMonthlySchedule().isEmpty()) {
			
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dailySchedule == null) ? 0 : dailySchedule.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastBackup == null) ? 0 : lastBackup.hashCode());
		result = prime * result + ((monthlySchedule == null) ? 0 : monthlySchedule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriveInfo other = (DriveInfo) obj;
		if (dailySchedule == null) {
			if (other.dailySchedule != null)
				return false;
		} else if (!dailySchedule.equals(other.dailySchedule))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastBackup == null) {
			if (other.lastBackup != null)
				return false;
		} else if (!lastBackup.equals(other.lastBackup))
			return false;
		if (monthlySchedule == null) {
			if (other.monthlySchedule != null)
				return false;
		} else if (!monthlySchedule.equals(other.monthlySchedule))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DriveInfo [id=" + id + ", dailySchedule=" + dailySchedule + ", monthlySchedule=" + monthlySchedule
				+ ", lastBackup=" + lastBackup + "]";
	}
}
