package trade.workingDay;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.time.DayOfWeek;

public abstract class WorkingDaysImpl implements WorkingDays{

	 protected Map<DayOfWeek, Boolean> isWorkingDayMap = new HashMap<>();

	    protected abstract void setupWorkingDays();

	    public WorkingDaysImpl() {
	        setupWorkingDays();
	    }

	    public LocalDate findWorkingDate(LocalDate date) {

	        // for code safety, check if there is really an available weekday
	        if (isWorkingDayMap.values().stream().noneMatch(b -> b)) {
	            return null;
	        }

	        // if there are available working days, then check for the first working day
	        return findFirstWorkingDateRec(date);
	    }

	    private LocalDate findFirstWorkingDateRec(LocalDate date) {
	        final DayOfWeek inputDay = date.getDayOfWeek();

	        // in case the given date is a working day, just return this
	        if (isWorkingDayMap.get(inputDay)) {
	            return date;
	        } else {
	            // otherwise look for the next working day
	            return findFirstWorkingDateRec(date.plusDays(1));
	        }
	    }
}
