package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.Message;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TransactionLogReporter {
	public static String getSalesReport(List<Message> salesLog) {
		Map<String, List<Message>> messageByProduct = salesLog
				.stream()
				.collect(Collectors.groupingBy(msg -> msg.getSale().getItemId()));

		StringBuilder report = new StringBuilder();
		report.append("Sales report so far:\n");
		report.append("Item\tUnits sold\tSales value (GBP)\n");

		for (String itemId : messageByProduct.keySet()) {
			long unitsSold = messageByProduct.get(itemId)
					.stream()
					.mapToLong(Message::getNumberOfSales)
					.sum();
			BigDecimal salesValue = computeSalesValue(messageByProduct.get(itemId));

			report.append(itemId);
			report.append("\t");
			report.append(unitsSold);
			report.append("\t");
			report.append(salesValue);
			report.append("\t\n");
		}
		return report.toString();
	}

	public static String getAdjustmentReport(List<Message> adjustmentReport) {
		StringBuilder report = new StringBuilder();
		report.append("Adjustment report so far:\n");
		report.append("Item\tPrice adjustment type\tAdjustment amount\n");

		for (Message adjustmentMsg : adjustmentReport) {
			report.append(adjustmentMsg.getSale().getItemId());
			report.append("\t");
			report.append(adjustmentMsg.getAdjustment());
			report.append("\t");
			report.append(adjustmentMsg.getSale().getItemUnitPrice());
			report.append("\t\n");
		}
		return report.toString();
	}

	private static BigDecimal computeSalesValue(List<Message> messages) {
		BigDecimal salesValueSum = BigDecimal.ZERO;
		for (Message msg : messages) {
			BigDecimal unitPrice = msg.getSale().getItemUnitPrice();
			long numUnits = msg.getNumberOfSales();
			salesValueSum = salesValueSum.add((unitPrice.multiply(BigDecimal.valueOf(numUnits))));
		};
		return salesValueSum;
	}
}
