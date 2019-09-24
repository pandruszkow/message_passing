package com.andruszkow.message_passing.data;

import java.math.BigDecimal;
import java.util.Objects;

public class Sale {
	String itemId;
	BigDecimal itemUnitPrice;
	String itemUnitCurrency;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getItemUnitCurrency() {
		return itemUnitCurrency;
	}

	public void setItemUnitCurrency(String itemUnitCurrency) {
		this.itemUnitCurrency = itemUnitCurrency;
	}

	@Override
	public String toString() {
		return "Sale{" +
				"itemId='" + itemId + '\'' +
				", itemUnitPrice=" + itemUnitPrice +
				", itemUnitCurrency='" + itemUnitCurrency + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sale sale = (Sale) o;
		return Objects.equals(itemId, sale.itemId) &&
				Objects.equals(itemUnitPrice, sale.itemUnitPrice) &&
				Objects.equals(itemUnitCurrency, sale.itemUnitCurrency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, itemUnitPrice, itemUnitCurrency);
	}
}
