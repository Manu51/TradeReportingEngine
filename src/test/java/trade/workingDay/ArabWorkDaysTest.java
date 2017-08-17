package trade.workingDay;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class ArabWorkDaysTest{

	 private WorkingDays workingDays;
	    @Before
	    public void setUp() throws Exception {
	        workingDays = new ArabWorkDays();
	    }

	    @Test
	    public void testFindFirstWorkingDate_Sunday() throws Exception {
	        final LocalDate sunday = LocalDate.of(2017, 8, 20);

	        // should return the same, since Sunday is a working day in Arabia
	        assertEquals(sunday, workingDays.findWorkingDate(sunday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Thursday() throws Exception {
	        final LocalDate aThursday = LocalDate.of(2017, 8, 17);

	        // should return the same, since Thursday is a working day in Arabia
	        assertEquals(aThursday, workingDays.findWorkingDate(aThursday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Friday() throws Exception {
	        final LocalDate aFriday = LocalDate.of(2017, 8, 18);

	        // should return the first Sunday (20/8/2017), since Friday is not a working day
	        assertEquals(LocalDate.of(2017, 8, 20), workingDays.findWorkingDate(aFriday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Saturday() throws Exception {
	        final LocalDate aSaturday = LocalDate.of(2017, 8, 19);

	        // should return the first Sunday (20/8/2017), since Saturday is not a working day
	        assertEquals(LocalDate.of(2017, 8, 20), workingDays.findWorkingDate(aSaturday));
	    }
}
