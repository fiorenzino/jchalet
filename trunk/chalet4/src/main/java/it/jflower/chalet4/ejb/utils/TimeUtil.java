package it.jflower.chalet4.ejb.utils;

import it.jflower.chalet4.ann.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;

public class TimeUtil {
	@Inject
	@Log
	private static transient Logger log;

	public static Long getDiffDays(Date dataInit, Date dataEnd) {
		log.info("DATE: " + dataInit + "-" + dataEnd);
		if (dataEnd.after(dataInit)) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(dataInit);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dataEnd);
			Long numDay = (new Long(cal2.getTimeInMillis()
					- cal1.getTimeInMillis()) / (new Long(24 * 60 * 60 * 1000))

			);
			return numDay;
		} else {
			return new Long(0);
		}
	}
}
