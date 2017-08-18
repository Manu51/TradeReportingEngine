package trade.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Test;

import trade.model.Instruction.TradeAction;

public class InstructionTest {

	@Test
	public void testTradeAmountCalculation() throws Exception {
		final BigDecimal agreedFx = BigDecimal.valueOf(0.50);
		final BigDecimal pricePerUnit = BigDecimal.valueOf(100.25);
		final int units = 200;

		final Instruction instruction = new Instruction("Entity1", TradeAction.SELL, LocalDate.of(2017, 8, 17),
				LocalDate.of(2017, 8, 21), Currency.getInstance("GBP"), agreedFx, units, pricePerUnit);

		final BigDecimal calculatedAmount = pricePerUnit.multiply(agreedFx).multiply(BigDecimal.valueOf(units)).setScale(3,
				BigDecimal.ROUND_HALF_EVEN);
		assertEquals(calculatedAmount, instruction.getTradeAmount());
	}

}
