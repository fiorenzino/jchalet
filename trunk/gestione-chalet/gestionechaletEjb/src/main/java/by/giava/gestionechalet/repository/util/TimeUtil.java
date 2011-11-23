package by.giava.gestionechalet.repository.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	static DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public static Long getDiffDays(Date dataInit, Date dataEnd) {
		System.out.println("INTERVALLO: " + format.format(dataInit) + "-"
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
			return numDay;
		} else {
			return new Long(0);
		}
	}
}
