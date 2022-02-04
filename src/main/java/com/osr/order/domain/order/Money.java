package com.osr.order.domain.order;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Money {

	private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.GERMANY);

	private static final BigDecimal DEFAULT_VALUE = BigDecimal.ZERO;

	private BigDecimal value;
	private String currencyCode;

	protected Money() {

	}

	public Money(BigDecimal value, Currency currency) {
		this(value, currency.getCurrencyCode());
	}

	public Money(BigDecimal value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}

	public Money(BigDecimal value) {
		this(value, DEFAULT_CURRENCY);
	}

	public Money(Currency currency) {
		this(DEFAULT_VALUE, currency);
	}

	public Money(String currency) {
		this(DEFAULT_VALUE, currency);
	}

	public Money add(Money money) {
		if (!isCompatibleCurrency(money)) {
			throw new IllegalArgumentException("Currency mismatch");
		}

		return new Money(this.value.add(money.value), determinateCurrencyCode(money));
	}

	/**
	 * IS compatible if had the same currencyCode
	 * 
	 * @param money Money to compare
	 * @return true if they are compatible, false in other case
	 */
	private boolean isCompatibleCurrency(Money money) {
		return this.currencyCode.equals(money.currencyCode);
	}

	private boolean isZeroValue(BigDecimal value) {
		return DEFAULT_VALUE.equals(value);
	}

	private Currency determinateCurrencyCode(Money money) {
		String currencyCode = isZeroValue(this.value) ? money.currencyCode : this.currencyCode;

		return Currency.getInstance(currencyCode);
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value.floatValue(), currencyCode);
	}

}
