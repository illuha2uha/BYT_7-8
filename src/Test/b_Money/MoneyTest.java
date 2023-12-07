package Test.b_Money;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import Main.b_Money.*;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Double.valueOf(100.00), SEK100.getAmount());
		assertEquals(Double.valueOf(20.00), EUR20.getAmount());
		assertEquals(Double.valueOf(-100.00), SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(3000), EUR20.universalValue());
		assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(EUR10));
		assertFalse(SEKn100.equals(SEK100));
	}

	@Test
	public void testAdd() {
		Money result = SEK100.add(EUR10);
		assertEquals("200.0 SEK", result.toString());

		result = EUR20.add(SEK200);
		assertEquals("40.0 EUR", result.toString());

		result = SEKn100.add(EUR10);
		assertEquals("0.0 SEK", result.toString());
	}

	@Test
	public void testSub() {
		Money result = SEK100.sub(EUR10);
		assertEquals("0.0 SEK", result.toString());

		result = EUR20.sub(SEK200);
		assertEquals("0.0 EUR", result.toString());

		result = SEKn100.sub(EUR10);
		assertEquals("-200.0 SEK", result.toString());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money result = SEK100.negate();
		assertEquals("-100.0 SEK", result.toString());

		result = EUR20.negate();
		assertEquals("-20.0 EUR", result.toString());

		result = SEKn100.negate();
		assertEquals("100.0 SEK", result.toString());
	}

	@Test
	public void testCompareTo() {


		assertTrue(SEKn100.compareTo(SEK100) < 0);

		assertTrue(SEK100.compareTo(EUR0) > 0);

		assertEquals(0, EUR20.compareTo(new Money(2000, EUR)));

	}
}
