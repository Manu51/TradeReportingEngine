package trade.report;

import java.util.Set;

import trade.model.Instruction;

public interface ReportGenerator {

	String generateReportForInstructions(Set<Instruction> instructions);
	}

