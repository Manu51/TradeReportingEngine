package trade.settlement.calculator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.Test;

import trade.entity.rank.EntityRank;
import trade.model.Instruction;
import trade.model.Instruction.TradeAction;
import trade.settlementDate.rank.calculator.EntityRankCalculator;
import trade.settlementDate.rank.calculator.InstructionSettlementDateCalculator;

public class EntityRankCalculatorTest {

	private static final LocalDate MONDAY = LocalDate.of(2017, 8, 21);
	private static final LocalDate TUESDAY = LocalDate.of(2017, 8, 22);
	private static final LocalDate WEDNESDAY = LocalDate.of(2017, 8, 23);
	private static final LocalDate SATURDAY = LocalDate.of(2017, 8, 19);
	private static final LocalDate SUNDAY = LocalDate.of(2017, 8, 20);

	private static Set<Instruction> getSetOfInstructions() {
		final Set<Instruction> instructions = new HashSet<>();

		// ===========================================================================
		//                   Entities for Monday(21/8/2017)
		// ===========================================================================
		instructions.add(new Instruction("Entity1", TradeAction.BUY, LocalDate.of(2017, 8, 17), MONDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 100, BigDecimal.valueOf(1)));

		instructions.add(new Instruction("Entity2", TradeAction.BUY, LocalDate.of(2017, 8, 17), MONDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 200, BigDecimal.valueOf(1)));

		instructions.add(new Instruction("Entity3", TradeAction.BUY, LocalDate.of(2017, 8, 17), SATURDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 300, BigDecimal.valueOf(1)));

		instructions.add(new Instruction("Entity4", TradeAction.SELL, LocalDate.of(2017, 8, 17), SUNDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 200, BigDecimal.valueOf(1)));

		// ===========================================================================
		//                   Entities for Tuesday(22/8/2017)
		// ===========================================================================
		instructions.add(new Instruction("Entity5", TradeAction.BUY, LocalDate.of(2017, 8, 17), TUESDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 400, BigDecimal.valueOf(1)));

		instructions.add(new Instruction("Entity6", TradeAction.SELL, LocalDate.of(2017, 8, 17), TUESDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 1000, BigDecimal.valueOf(1)));

		// ===========================================================================
		//                   Entities for Wednesday(23/8/2017)
		// ===========================================================================
		instructions.add(new Instruction("Entity7", TradeAction.BUY, LocalDate.of(2017, 8, 17), WEDNESDAY,
				Currency.getInstance("SGD"), BigDecimal.valueOf(1), 7000, BigDecimal.valueOf(1)));

		InstructionSettlementDateCalculator.calculateSettlementDates(instructions);

		return instructions;
	}

	@Test
	public void testDailyIncomingAmount() throws Exception {
		final Map<LocalDate, BigDecimal> dailyIncomingAmount = EntityRankCalculator
				.calculateDailyIncomingAmount(getSetOfInstructions());

		assertEquals(2, dailyIncomingAmount.size());
		assertTrue(Objects.equals(dailyIncomingAmount.get(MONDAY),
				BigDecimal.valueOf(200.00).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
		assertTrue(Objects.equals(dailyIncomingAmount.get(TUESDAY),
				BigDecimal.valueOf(1000.00).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
	}

	@Test
	public void testDailyOutgoingAmount() throws Exception {
		final Map<LocalDate, BigDecimal> dailyOutgoingAmount = EntityRankCalculator
				.calculateDailyOutgoingAmount(getSetOfInstructions());

		assertEquals(3, dailyOutgoingAmount.size());
		assertTrue(Objects.equals(dailyOutgoingAmount.get(MONDAY),
				BigDecimal.valueOf(600.00).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
		assertTrue(Objects.equals(dailyOutgoingAmount.get(TUESDAY),
				BigDecimal.valueOf(400.00).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
			}

	@Test
	public void testDailyIncomingRanking() throws Exception {
		final Map<LocalDate, LinkedList<EntityRank>> dailyIncomingRanking = EntityRankCalculator
				.calculateDailyIncomingRanking(getSetOfInstructions());

		assertEquals(2, dailyIncomingRanking.size());

		assertEquals(1, dailyIncomingRanking.get(MONDAY).size());
		assertEquals(1, dailyIncomingRanking.get(TUESDAY).size());

		assertTrue(dailyIncomingRanking.get(MONDAY).contains(new EntityRank(1, "Entity4", MONDAY)));
		assertTrue(dailyIncomingRanking.get(TUESDAY).contains(new EntityRank(1, "Entity6", TUESDAY)));

	}

	@Test
	public void testDailyOutgoingRanking() throws Exception {
		final Map<LocalDate, LinkedList<EntityRank>> dailyOutgoingRanking = EntityRankCalculator
				.calculateDailyOutgoingRanking(getSetOfInstructions());

		assertEquals(3, dailyOutgoingRanking.size());

		assertEquals(3, dailyOutgoingRanking.get(MONDAY).size());
		assertEquals(1, dailyOutgoingRanking.get(TUESDAY).size());
		assertEquals(1, dailyOutgoingRanking.get(WEDNESDAY).size());

		assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new EntityRank(1, "Entity3", MONDAY)));
		assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new EntityRank(2, "Entity2", MONDAY)));
		assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new EntityRank(3, "Entity1", MONDAY)));

		assertTrue(dailyOutgoingRanking.get(TUESDAY).contains(new EntityRank(1, "Entity5", TUESDAY)));

		assertTrue(dailyOutgoingRanking.get(WEDNESDAY).contains(new EntityRank(1, "Entity7", WEDNESDAY)));
	}
}
