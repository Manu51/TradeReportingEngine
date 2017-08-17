package trade.settlementCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import trade.entity.rank.EntityRank;
import trade.model.Instruction;
import trade.model.Instruction.TradeAction;

import static java.util.stream.Collectors.*;

/**
 * Describes a mapping between dates and the trade amount of those dates, based on instructions
 */
public class EntityRankCalculator {

    // Create a predicate for outgoing
    private final static Predicate<Instruction> outgoingPredicate =
            instruction -> instruction.getAction().equals(TradeAction.BUY);

    // Create a predicate for incoming
    private final static Predicate<Instruction> incomingPredicate =
            instruction -> instruction.getAction().equals(TradeAction.SELL);

    /**
     * Calculates the daily outgoing (BUY) trade amount in USD
     * @param instructions 
     * @return a map from date to total amount
     */
    public static Map<LocalDate, BigDecimal> calculateDailyOutgoingAmount(Set<Instruction> instructions) {
        return calculateDailyAmount(instructions, outgoingPredicate);
    }

    /**
     * Calculates the daily incoming (SELL) trade amount in USD
     * @param instructions 
     * @return a map from date to total amount
     */
    public static Map<LocalDate, BigDecimal> calculateDailyIncomingAmount(Set<Instruction> instructions) {
        return calculateDailyAmount(instructions, incomingPredicate);
    }

    /**
     * Ranks the daily outgoing (BUY) by trade amount in USD
     * @param instructions 
     * @return a map from date to a list of ranks (ranking)
     */
    public static Map<LocalDate, LinkedList<EntityRank>> calculateDailyOutgoingRanking(Set<Instruction> instructions) {
        return calculateRanking(instructions, outgoingPredicate);
    }

    /**
     * Ranks the daily incoming (SELL) by trade amount in USD
     * @param instructions
     * @return a map from date to a list of ranks (ranking)
     */
    public static Map<LocalDate, LinkedList<EntityRank>> calculateDailyIncomingRanking(Set<Instruction> instructions) {
        return calculateRanking(instructions, incomingPredicate);
    }

    private static Map<LocalDate, BigDecimal> calculateDailyAmount(
            Set<Instruction> instructions, Predicate<Instruction> predicate)
    {
        return instructions.stream()
                .filter(predicate)
                .collect(groupingBy(Instruction::getSettlementDate,
                    mapping(Instruction::getTradeAmount,
                        reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    private static Map<LocalDate, LinkedList<EntityRank>> calculateRanking(
            Set<Instruction> instructions, Predicate<Instruction> predicate)
    {
        final Map<LocalDate, LinkedList<EntityRank>> ranking = new HashMap<>();

        instructions.stream()
            .filter(predicate)
            .collect(groupingBy(Instruction::getSettlementDate, toSet()))
            .forEach((date, dailyInstructionSet) -> {
                final AtomicInteger rank = new AtomicInteger(1);

                final LinkedList<EntityRank> ranks = dailyInstructionSet.stream()
                    .sorted((a, b) -> b.getTradeAmount().compareTo(a.getTradeAmount()))
                    .map(instruction -> new EntityRank(rank.getAndIncrement(), instruction.getEntity(), date))
                    .collect(toCollection(LinkedList::new));

                ranking.put(date, ranks);
            });

        return ranking;
    }
}