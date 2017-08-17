package trade.workingDay;

import java.time.DayOfWeek;

public class ArabWorkDays extends WorkingDaysImpl{

    public ArabWorkDays() {
        super();
    }

    @Override
    protected void setupWorkingDays() {
        this.isWorkingDayMap.put(DayOfWeek.SUNDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.MONDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.TUESDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.WEDNESDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.THURSDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.FRIDAY, false); 
        this.isWorkingDayMap.put(DayOfWeek.SATURDAY, false);
    }
}
