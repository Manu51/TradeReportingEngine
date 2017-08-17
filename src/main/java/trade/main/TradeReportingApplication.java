package trade.main;
import java.util.Set;

import trade.model.Instruction;
import trade.report.ReportGenerator;
import trade.report.ReportGeneratorImpl;
import trade.sampleData.SampleDataGenerator;

public class TradeReportingApplication {

	 public static void main(String[] args) {
	        final Set<Instruction> instructions = SampleDataGenerator.getSampleInstructions();
	        final ReportGenerator reportGenerator = new ReportGeneratorImpl();

	        System.out.println(reportGenerator.generateReportForInstructions(instructions));
	    }
}
