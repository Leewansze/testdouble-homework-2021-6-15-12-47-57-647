package com.tw.banking;

import com.tw.banking.Account;
import com.tw.banking.Printer;
import com.tw.banking.Transaction;
import com.tw.banking.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountTest {
    @Test
    public void should_deposit_execute_adddeposit() {
        // given
        int amount = 10000;
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        Printer printer = Mockito.mock(Printer.class);
        Account account = new Account(transactionRepository, printer);
        // when
        account.deposit(amount);
        // then
        verify(transactionRepository, Mockito.times(1)).addDeposit(amount);
    }
    @Test
    public void should_withdraw_execute_addwithdraw() {
        // given
        int amount = 10000;
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        Printer printer = Mockito.mock(Printer.class);

        Account account = new Account(transactionRepository, printer);
        // when
        account.withdraw(amount);
        // then
        verify(transactionRepository, Mockito.times(1)).addWithdraw(amount);
    }
    @Test
    public void should_printStatement_execute_printing() {
        // given

        Mockito.mock(Transaction.class);

        List<Transaction> transactions = new ArrayList<>();

        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        when(transactionRepository.allTransactions()).thenReturn(transactions);

        Printer printer = Mockito.mock(Printer.class);

        Account account = new Account(transactionRepository, printer);

        // when
        account.printStatement();

        // then
        verify(printer, Mockito.times(1)).print(transactionRepository.allTransactions());
    }
}
