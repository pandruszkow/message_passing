package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.AdjustmentType;
import com.andruszkow.message_passing.data.Message;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageProcessorTest {

	List<Message> singleSaleMessages;
	List<Message> multiSaleMessages;
	List<Message> adjustmentSet = new ArrayList<>();
	List<Message> mixedSet = new ArrayList<>();

	MessageProcessor processor;

	@Before
	public void setUp() throws Exception {
		Message single = CommonTestUtil.getSaleMessage();
		singleSaleMessages = Collections.nCopies(10, single);
		Message multiple = CommonTestUtil.getSaleMessage();
		multiple.setNumberOfSales(7);
		multiSaleMessages = Collections.nCopies(11, multiple);

		//(previous + 7 - 4) * 2
		Message adjustAdd = CommonTestUtil.getSaleMessage();
		adjustAdd.getSale().setItemUnitPrice(BigDecimal.valueOf(7));
		adjustAdd.setAdjustment(AdjustmentType.ADD);
		Message adjustSubtract = CommonTestUtil.getSaleMessage();
		adjustSubtract.getSale().setItemUnitPrice(BigDecimal.valueOf(4));
		adjustSubtract.setAdjustment(AdjustmentType.SUBTRACT);
		Message adjustMultiply = CommonTestUtil.getSaleMessage();
		adjustMultiply.getSale().setItemUnitPrice(BigDecimal.valueOf(2));
		adjustMultiply.setAdjustment(AdjustmentType.MULTIPLY);
		adjustmentSet.add(adjustAdd);
		adjustmentSet.add(adjustSubtract);
		adjustmentSet.add(adjustMultiply);

		mixedSet = new ArrayList<>();
		mixedSet.addAll(singleSaleMessages);
		mixedSet.addAll(multiSaleMessages);
		mixedSet.addAll(adjustmentSet);

		processor = new MessageProcessor(99, 99);
	}

	@Test
	public void SingleSaleMessages() {
		singleSaleMessages.stream().forEach(msg -> processor.accept(msg));
		int expectedCount = 10;
		int actualCount = processor.saleLog.size();

		String expectedReport = "Sales report so far:\n" +
				"Item\tUnits sold\tSales value (GBP)\n" +
				"apple\t10\t2.0\t\n";
		String actualReport = TransactionLogReporter.getSalesReport(singleSaleMessages);

		assertEquals(expectedCount, actualCount);
		assertEquals(expectedReport, actualReport);
	}

	@Test
	public void MultiSaleMessages() {
		multiSaleMessages.stream().forEach(msg -> processor.accept(msg));
		int expectedCount = 11;
		int actualCount = processor.saleLog.size();

		String expectedReport = "Sales report so far:\n" +
				"Item\tUnits sold\tSales value (GBP)\n" +
				"apple\t77\t15.4\t\n";
		String actualReport = TransactionLogReporter.getSalesReport(multiSaleMessages);

		assertEquals(expectedCount, actualCount);
		assertEquals(expectedReport, actualReport);
	}

	@Test
	public void MixMessages() {
		mixedSet.stream().forEach(msg -> processor.accept(msg));
		int expectedCount = 11;
		int actualCount = processor.saleLog.size();

		String expectedReport = "Sales report so far:\n" +
				"Item\tUnits sold\tSales value (GBP)\n" +
				"apple\t90\t5544768.2\t\n";
		String actualReport = TransactionLogReporter.getSalesReport(mixedSet);

		assertEquals(expectedCount, actualCount);
		assertEquals(expectedReport, actualReport);
	}
}