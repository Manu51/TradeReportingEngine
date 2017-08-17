package trade.workingDay;



import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class DefaultWorkDaysTest{

	private WorkingDays workingDays;
    @Before
    public void setUp() throws Exception {
        workingDays = new DefaultWorkDays();
    }

    @Test
    public void testFindFirstWorkingDate_Monday() throws Exception {
        final LocalDate monday = LocalDate.of(2017, 8, 21);

        // should return the same, since Monday is a working by default
        assertEquals(monday, workingDays.findWorkingDate(monday));
    }

    @Test
    public void testFindFirstWorkingDate_Friday() throws Exception {
        final LocalDate aFriday = LocalDate.of(2017, 8, 18);

        // should return the same, since Friday is a working by default
        assertEquals(aFriday, workingDays.findWorkingDate(aFriday));
    }

    @Test
    public void testFindFirstWorkingDate_Saturday() throws Exception {
        final LocalDate aSaturday = LocalDate.of(2017, 8, 19);

        // should return the first Monday (21/8/2017), since Saturday is not a working day
        assertEquals(LocalDate.of(2017, 8, 21), workingDays.findWorkingDate(aSaturday));
    }

    @Test
    public void testFindFirstWorkingDate_Sunday() throws Exception {
        final LocalDate aSunday = LocalDate.of(2017, 8, 20);

        // should return the first Monday (21/8/2017), since Sunday is not a working day
        assertEquals(LocalDate.of(2017, 8, 21), workingDays.findWorkingDate(aSunday));
    }
}
