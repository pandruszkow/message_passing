package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.Message;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageProcessor {
	private final long reportingInterval;
	private final long pauseInterval;
	long numProcessedSoFar = 0;

	public List<Message> saleLog = new ArrayList<>();
	public List<Message> adjustmentLog = new ArrayList<>();

	public MessageProcessor(long reportingInterval, long pauseInterval) {
		this.reportingInterval = reportingInterval;
		this.pauseInterval = pauseInterval;
	}

	public void accept(Message msg) {
		if (!"GBP".equals(msg.getSale().getItemUnitCurrency())) {
			throw new IllegalArgumentException("Invalid currency specified in sales message");
		}

		Integer numberOfSales = msg.getNumberOfSales();
		msg.setNumberOfSales(numberOfSales == null ? 1 : numberOfSales);

		//adjustment and sale messages are mutually exclusive
		if (msg.getAdjustment() == null) {
			saleLog.add(msg);
		} else {
			applyAdjustment(msg);
			adjustmentLog.add(msg);
		}

		++numProcessedSoFar;

		if (numProcessedSoFar % reportingInterval == 0) {
			System.out.println(TransactionLogReporter.getSalesReport(saleLog));
		}

		if (numProcessedSoFar % pauseInterval == 0) {
			System.out.println("Pausing processing to display adjustment log.");
			System.out.println(TransactionLogReporter.getAdjustmentReport(adjustmentLog));

			System.out.println("Press Return to continue.");
			new Scanner(System.in).nextLine();
		}
	}

	private void applyAdjustment(Message msg) {
		BigDecimal applyAmount = msg.getSale().getItemUnitPrice();
		for (Message logged : saleLog) {
			BigDecimal origPrice = logged.getSale().getItemUnitPrice();
			BigDecimal newPrice = null;
			switch (msg.getAdjustment()) {
				case ADD:
					newPrice = origPrice.add(applyAmount);
					break;
				case SUBTRACT:
					newPrice = origPrice.subtract(applyAmount);
					break;
				case MULTIPLY:
					newPrice = origPrice.multiply(applyAmount);
					break;
			}
			logged.getSale().setItemUnitPrice(newPrice);
		}
	}
}
