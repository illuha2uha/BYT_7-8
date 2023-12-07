package Test.b_Money;

import static org.junit.Assert.*;

import Main.b_Money.*;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount()  {
		try {
			SweBank.openAccount("Alice");
		} catch (AccountExistsException e) {
			fail("AccountExistsException should not be thrown for a new account");
		}

		try {
			SweBank.openAccount("Bob");
			fail("AccountDoesNotExistException should be thrown for an existing account");
		} catch (AccountExistsException ignored) {}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(500, SEK));
		assertEquals(5.0, SweBank.getBalance("Ulrika"), 0.001);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.withdraw("Ulrika", new Money(500, SEK));
		assertEquals(5.0, SweBank.getBalance("Ulrika"), 0.001);
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		double balance = SweBank.getBalance("Ulrika");
		assertEquals(10.0, balance, 0.001);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(500, SEK));
		assertEquals(5.0, SweBank.getBalance("Ulrika"), 0.001);
		assertEquals(5.0, Nordea.getBalance("Bob"), 0.001);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.addTimedPayment("Ulrika", "payment1", 1, 1, new Money(100, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals(9.0, SweBank.getBalance("Ulrika"), 0.001);
		assertEquals(1.0, Nordea.getBalance("Bob"), 0.001);
	}
}
