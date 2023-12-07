package Test.b_Money;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import Main.b_Money.Currency;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(0.20), DKK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}
	
	@Test
	public void testSetRate() {
		NOK = new Currency("NOK", 0.0);
		NOK.setRate(0.01);

		assertEquals(Double.valueOf(0.01), NOK.getRate());
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK.universalValue(100));
		assertEquals(Integer.valueOf(241960), DKK.universalValue(12098));
		assertEquals(Integer.valueOf(51150), EUR.universalValue(341));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(30), SEK.valueInThisCurrency(300, EUR));
		assertEquals(Integer.valueOf(1050), EUR.valueInThisCurrency(140, DKK));
		assertEquals(Integer.valueOf(1934), DKK.valueInThisCurrency(1451, SEK));
	}

}
