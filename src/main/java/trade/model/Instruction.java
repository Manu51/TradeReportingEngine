package trade.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Instruction {

	public enum TradeAction {
		BUY, SELL
	}

	// A financial entity whose shares are to be bought or sold
	private final String entity;

	// What action should the Instruction represent (Buy or Sell)
	private final TradeAction action;

	// Date on which the instruction was sent to JP Morgan by various clients
	private final LocalDate instructionDate;

	// The Date on which the client wished for the instruction to be settled
	// with respect to Instruction LocalDate
	private LocalDate settlementDate;

	private final Currency currency;

	// The foreign exchange rate with respect to USD that was agreed
	private final BigDecimal agreedFx;

	// Number of shares to be bought or sold
	private final int units;

	// The price per unit
	private final BigDecimal pricePerUnit;

	// The total trade amount in USD
	private final BigDecimal tradeAmount;

	public Instruction(String entity, TradeAction action, LocalDate instructionDate, LocalDate settlementDate,
			Currency currency, BigDecimal agreedFx, int units, BigDecimal pricePerUnit) {
		this.entity = entity;
		this.action = action;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.currency = currency;
		this.agreedFx = agreedFx;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		this.tradeAmount = calculateTradeAmount(this);
	}

	private static BigDecimal calculateTradeAmount(Instruction instruction) {
		return instruction.getPricePerUnit().multiply(BigDecimal.valueOf(instruction.getUnits()))
				.multiply(instruction.getAgreedFx());
	}

	public String getEntity() {
		return entity;
	}

	public TradeAction getAction() {
		return action;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public void setSettlementDate(LocalDate newDate) {
		settlementDate = newDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public int getUnits() {
		return units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}

	@Override
	public String toString() {
		return entity;
	}
}
