package trade.settlement.calculator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Test;

import trade.model.Instruction;
import trade.model.Instruction.TradeAction;
import trade.settlementDate.rank.calculator.InstructionSettlementDateCalculator;

public class SettlementDateCalculatorTest {

	@Test
	public void calculateSettlementDate_default_Friday() throws Exception {
		final LocalDate initialSettlementDate = LocalDate.of(2017, 8, 18); //Friday

		final Instruction instruction = new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17),
				initialSettlementDate, Currency.getInstance("SGD"), BigDecimal.valueOf(0.50), 200,
				BigDecimal.valueOf(100.25));

		// calculate new settlement day
		InstructionSettlementDateCalculator.calculateSettlementDate(instruction);

		// should be the same
		assertEquals(initialSettlementDate, instruction.getSettlementDate());
	}

	@Test
	public void calculateSettlementDate_default_Sunday() throws Exception {
		final LocalDate initialSettlementDate = LocalDate.of(2017, 8, 20); // Sunday

		final Instruction instruction = new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17),
				initialSettlementDate, Currency.getInstance("SGD"), BigDecimal.valueOf(1), 200,
				BigDecimal.valueOf(100.25));

		// calculate next working settlement Day
		InstructionSettlementDateCalculator.calculateSettlementDate(instruction);

		// should be the first Working day i.e.Monday (21/8/2017)
		assertEquals(LocalDate.of(2017, 8, 21), instruction.getSettlementDate());
	}

	@Test
	public void calculateSettlementDate_arabia_Friday() throws Exception {
		final LocalDate initialSettlementDate = LocalDate.of(2017, 8, 18); // Friday
		final Instruction instruction = new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17),
				initialSettlementDate, Currency.getInstance("AED"), 
				BigDecimal.valueOf(0.50), 200, BigDecimal.valueOf(100.25));

		// For Arbia, Friday is not working.So calculate next working day which is Sunday.
		InstructionSettlementDateCalculator.calculateSettlementDate(instruction);

		// should be the first Sunday (20/8/2017)
		assertEquals(LocalDate.of(2017, 8, 20), instruction.getSettlementDate());
	}

	@Test
	public void calculateSettlementDate_arabia_Sunday() throws Exception {
		final LocalDate initialSettlementDate = LocalDate.of(2017, 8, 20); //Sunday(Working for Arbia)
		final Instruction instruction = new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17),
				initialSettlementDate, Currency.getInstance("SAR"),
				BigDecimal.valueOf(0.50), 200, BigDecimal.valueOf(100.25));

		// calculate settlement day
		InstructionSettlementDateCalculator.calculateSettlementDate(instruction);

		// should be the same because Sunday is working for Arabia
		assertEquals(initialSettlementDate, instruction.getSettlementDate());
	}
}
