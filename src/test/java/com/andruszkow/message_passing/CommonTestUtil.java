package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.Message;
import com.andruszkow.message_passing.data.Sale;

import java.math.BigDecimal;

public class CommonTestUtil {
	public static Message getSaleMessage() {
		Sale sale = new Sale();
		sale.setItemUnitPrice(BigDecimal.valueOf(0.20));
		sale.setItemUnitCurrency("GBP");
		sale.setItemId("apple");

		Message message = new Message();
		message.setSale(sale);
		return message;
	}
}
