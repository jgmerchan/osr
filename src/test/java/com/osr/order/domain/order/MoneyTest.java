package com.osr.order.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class MoneyTest {
	
	@Test
	public void add10EurosPlus20EurosShouldBe30Euros() {
		Money money1 = new Money(new BigDecimal(10), Currency.getInstance(Locale.GERMANY));
		Money money2 = new Money(new BigDecimal(20), Currency.getInstance(Locale.GERMANY));
		
		Money expected = new Money(new BigDecimal(30), Currency.getInstance(Locale.GERMANY));
		
		assertEquals(expected, money1.add(money2));
	}

	@Test
	public void addWithDifferentCurrencyMoneyShouldThrowIllegalArgumentExceptionTest() {
		Money money1 = new Money(Currency.getInstance(Locale.GERMANY));
		Money money2 = new Money(Currency.getInstance(Locale.UK));

		assertThrows(IllegalArgumentException.class, () -> {
			money1.add(money2);
		});
	}
	
	@Test
	public void checkToString() {
		Money money = new Money(new BigDecimal(3.5f), Currency.getInstance(Locale.GERMANY));
		
		assertEquals("3,50 EUR", money.toString());
	}
}
