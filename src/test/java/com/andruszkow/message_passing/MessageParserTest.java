package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.AdjustmentType;
import com.andruszkow.message_passing.data.Message;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class MessageParserTest {
	public MessageParserTest() {
	}

	@Test
	public void type1Message() throws JAXBException, FileNotFoundException {
		Message expected = CommonTestUtil.getSaleMessage();

		Message actual = new MessageParser().parse(new File("src/test/resources/parser-type1.xml"));
		assertEquals(expected, actual);
	}

	@Test
	public void type2Message() throws JAXBException, FileNotFoundException {
		Message expected = CommonTestUtil.getSaleMessage();
		expected.setNumberOfSales(20);

		Message actual = new MessageParser().parse(new File("src/test/resources/parser-type2.xml"));
		assertEquals(expected, actual);
	}
	@Test
	public void type3Message() throws JAXBException, FileNotFoundException {
		Message expected = CommonTestUtil.getSaleMessage();
		expected.setAdjustment(AdjustmentType.SUBTRACT);

		Message actual = new MessageParser().parse(new File("src/test/resources/parser-type3.xml"));
		assertEquals(expected, actual);
	}
}