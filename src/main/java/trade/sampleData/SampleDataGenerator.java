package trade.sampleData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import trade.model.Instruction;
import trade.model.Instruction.TradeAction;

public class SampleDataGenerator {

	public static Set<Instruction> getSampleInstructions() {
		return new HashSet<>(Arrays.asList(

				new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 17),
						Currency.getInstance("SGD"), BigDecimal.valueOf(0.50), 200, BigDecimal.valueOf(100.25)),

				new Instruction("Entity2", TradeAction.SELL, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 19),
						Currency.getInstance("AED"), BigDecimal.valueOf(0.22), 450, BigDecimal.valueOf(150.5)),

				new Instruction("Entity3", TradeAction.SELL, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 18),
						Currency.getInstance("SAR"), BigDecimal.valueOf(0.27), 150, BigDecimal.valueOf(400.8)),

				new Instruction("Entity4", TradeAction.SELL, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 19),
						Currency.getInstance("EUR"), BigDecimal.valueOf(0.34), 50, BigDecimal.valueOf(500.6)),

				new Instruction("Entity5", TradeAction.BUY, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 19),
						Currency.getInstance("GBP"), BigDecimal.valueOf(0.34), 20, BigDecimal.valueOf(40.6)),

				new Instruction("Entity6", TradeAction.BUY, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 19),
						Currency.getInstance("EUR"), BigDecimal.valueOf(0.34), 20, BigDecimal.valueOf(40.6)),

				new Instruction("Entity7", TradeAction.SELL, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 20),
						Currency.getInstance("GBP"), BigDecimal.valueOf(0.34), 1000, BigDecimal.valueOf(160.6)),

				new Instruction("Entity8", TradeAction.SELL, LocalDate.of(2017, 8, 17), LocalDate.of(2017, 8, 20),
						Currency.getInstance("INR"), BigDecimal.valueOf(0.34), 120, BigDecimal.valueOf(500.6))));
	}
}
