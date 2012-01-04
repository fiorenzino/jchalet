package by.giava.gestionechalet.repository.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jboss.logging.Logger;

public class TimeUtils {

	static Logger logger = Logger.getLogger(TimeUtils.class);

	static DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public static Long getDiffDays(Date dataInit, Date dataEnd,
			boolean conEstremi) {
		logger.info("INTERVALLO: " + format.format(dataInit) + "-"
				+ format.format(dataEnd));
		if (dataEnd.after(dataInit)) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(dataInit);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dataEnd);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal1.set(Calendar.MINUTE, 60);
			Long numDay = (new Long(cal2.getTimeInMillis()
					- cal1.getTimeInMillis()) / (new Long(24 * 60 * 60 * 1000)));
			if (conEstremi)
				return numDay + 1;
			return numDay;
		} else {
			return new Long(0);
		}
	}

	public static String getSingleDayName(Date data, boolean withYear) {
		String dataS = "";
		if (data != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);

			if (withYear) {
				dataS = cal.get(Calendar.DAY_OF_MONTH) + "-"
						+ (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.YEAR);
			} else {
				dataS = cal.get(Calendar.DAY_OF_MONTH) + "-"
						+ (cal.get(Calendar.MONTH) + 1);
				// + "-"
				// + cal.get(Calendar.YEAR);
			}

		}
		logger.info(dataS);
		return dataS;
	}
}
