package trade.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import trade.entity.rank.EntityRank;
import trade.model.Instruction;
import trade.settlementDate.rank.calculator.EntityRankCalculator;
import trade.settlementDate.rank.calculator.InstructionSettlementDateCalculator;

public class ReportGeneratorImpl implements ReportGenerator {

	private StringBuilder stringBuilder = new StringBuilder();

	@Override
	public String generateReportForInstructions(Set<Instruction> instructions) {
		// first calculate the correct settlement dates
		InstructionSettlementDateCalculator.calculateSettlementDates(instructions);

		// Build the report string
		return generateDailyOutgoingRanking(instructions, generateDailyIncomingRanking(instructions,
				generateDailyIncomingAmount(instructions, generateDailyOutgoingAmount(instructions, stringBuilder))))
						.toString();
	}

	private static StringBuilder generateDailyOutgoingAmount(Set<Instruction> instructions,
			StringBuilder stringBuilder) {
		final Map<LocalDate, BigDecimal> dailyOutgoingAmount = EntityRankCalculator
				.calculateDailyOutgoingAmount(instructions);

		stringBuilder.append("\n----------------------------------------\n")
				.append("         Outgoing Daily Amount          \n")
				.append("----------------------------------------\n")
				.append("      Date       | Trade Amount in USD      \n")
				.append("----------------------------------------\n");

		for (LocalDate date : dailyOutgoingAmount.keySet()) {
			stringBuilder.append(date).append("       |      ").append(dailyOutgoingAmount.get(date)).append("\n");
		}

		return stringBuilder;
	}

	private static StringBuilder generateDailyIncomingAmount(Set<Instruction> instructions,
			StringBuilder stringBuilder) {
		final Map<LocalDate, BigDecimal> dailyIncomingAmount = EntityRankCalculator
				.calculateDailyIncomingAmount(instructions);

		stringBuilder.append("\n----------------------------------------\n")
				.append("         Incoming Daily Amount          \n")
				.append("----------------------------------------\n")
				.append("      Date       | Trade Amount In USD    \n")
				.append("----------------------------------------\n");

		for (LocalDate date : dailyIncomingAmount.keySet()) {
			stringBuilder.append(date).append("       |      ").append(dailyIncomingAmount.get(date)).append("\n");
		}

		return stringBuilder;
	}

	private static StringBuilder generateDailyOutgoingRanking(Set<Instruction> instructions,
			StringBuilder stringBuilder) {
		final Map<LocalDate, LinkedList<EntityRank>> dailyOutgoingRanking = EntityRankCalculator
				.calculateDailyOutgoingRanking(instructions);

		stringBuilder.append("\n----------------------------------------\n")
				.append("         Outgoing Daily Ranking          \n")
				.append("----------------------------------------\n")
				.append("     Date    |     Rank   |   Entity     \n")
				.append("----------------------------------------\n");

		for (LocalDate date : dailyOutgoingRanking.keySet()) {
			for (EntityRank rank : dailyOutgoingRanking.get(date)) {
				stringBuilder.append(date).append("   |      ").append(rank.getRank()).append("     |    ")
						.append(rank.getEntity()).append("\n");
			}
		}

		return stringBuilder;
	}

	private static StringBuilder generateDailyIncomingRanking(Set<Instruction> instructions,
			StringBuilder stringBuilder) {
		final Map<LocalDate, LinkedList<EntityRank>> dailyIncomingRanking = EntityRankCalculator
				.calculateDailyIncomingRanking(instructions);

		stringBuilder.append("\n----------------------------------------\n")
				.append("         Incoming Daily Ranking          \n")
				.append("----------------------------------------\n")
				.append("     Date    |     Rank   |   Entity     \n")
				.append("----------------------------------------\n");

		for (LocalDate date : dailyIncomingRanking.keySet()) {
			for (EntityRank rank : dailyIncomingRanking.get(date)) {
				stringBuilder.append(date).append("   |      ").append(rank.getRank()).append("     |    ")
						.append(rank.getEntity()).append("\n");
			}
		}

		return stringBuilder;
	}

}
