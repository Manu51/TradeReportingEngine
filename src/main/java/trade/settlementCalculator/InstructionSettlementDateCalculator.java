package trade.settlementCalculator;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

import trade.model.Instruction;
import trade.workingDay.ArabWorkDays;
import trade.workingDay.DefaultWorkDays;
import trade.workingDay.WorkingDays;

public class InstructionSettlementDateCalculator {

	/**
	 * Helper function to calculate settlement date for every given instruction
	 * 
	 * @param instructions
	 *            the instructions of which the settlement date will be
	 *            calculated
	 */
	public static void calculateSettlementDates(Set<Instruction> instructions) {
		instructions.forEach(InstructionSettlementDateCalculator::calculateSettlementDate);
	}

	/**
	 * Calculate the settlementDate
	 * 
	 * @param instruction
	 *            the instruction of which the settlement date will be
	 *            calculated
	 */
	public static void calculateSettlementDate(Instruction instruction) {
		// Select working days based on the Currency
		final WorkingDays workingDays = getWorkingDays(instruction.getCurrency());

		// find the correct settlement date
		final LocalDate settlementDate = workingDays.findWorkingDate(instruction.getSettlementDate());

		if (settlementDate != null) {
			// set the correct settlement date
			instruction.setSettlementDate(settlementDate);
		}
	}

	/**
	 * Select working days based on the Currency
	 * @param currency
	 * @return the working days
	 */
	private static WorkingDays getWorkingDays(Currency currency) {
		if ((currency.getCurrencyCode().equals("AED")) || (currency.getCurrencyCode().equals("SAR"))) {
			return new ArabWorkDays();
		}
		return new DefaultWorkDays();
	}
}
