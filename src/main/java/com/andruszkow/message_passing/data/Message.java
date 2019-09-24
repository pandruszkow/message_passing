package com.andruszkow.message_passing.data;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "message")
public class Message {
	Sale sale;
	Integer numberOfSales;
	AdjustmentType adjustment;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Integer getNumberOfSales() {
		return numberOfSales;
	}

	public void setNumberOfSales(Integer numberOfSales) {
		this.numberOfSales = numberOfSales;
	}

	public AdjustmentType getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(AdjustmentType adjustment) {
		this.adjustment = adjustment;
	}

	@Override
	public String toString() {
		return "Message{" +
				"sale=" + sale +
				", numberOfSales=" + numberOfSales +
				", adjustmentType=" + adjustment +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return Objects.equals(sale, message.sale) &&
				Objects.equals(numberOfSales, message.numberOfSales) &&
				adjustment == message.adjustment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sale, numberOfSales, adjustment);
	}
}
