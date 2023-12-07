package Test.b_Money;

import Main.b_Money.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1"));

		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(99995.0, testAccount.getBalance().getAmount(), 0.001);
		assertEquals(10005.0, SweBank.getBalance("Alice"), 0.001);
	}



	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(500, SEK));
		assertEquals(100005.0, testAccount.getBalance().getAmount(), 0.001);

		testAccount.withdraw(new Money(200, SEK));
		assertEquals(100003.0, testAccount.getBalance().getAmount(), 0.001);
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(100000.0, testAccount.getBalance().getAmount(), 0.001);
	}
}
