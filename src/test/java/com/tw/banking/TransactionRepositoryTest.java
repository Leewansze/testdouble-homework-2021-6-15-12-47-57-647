package com.tw.banking;

import com.tw.banking.Clock;
import com.tw.banking.Transaction;
import com.tw.banking.TransactionRepository;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionRepositoryTest {
    @Test
    public void should_adddeposit_execute_adding() {
        // given
        int amount = 10000;
        String date = "2021-06-21";

        Clock clock = Mockito.mock(Clock.class);
        when(clock.todayAsString()).thenReturn(date);

        TransactionRepository transactionRepository = new TransactionRepository(clock);

        // when
        transactionRepository.addDeposit(amount);

        List<Transaction> transactions = transactionRepository.transactions;
        assertEquals(1, transactions.size());
        // then
        assertEquals(amount, transactions.get(0).amount());
        assertEquals(date, transactions.get(0).date());
    }

    @Test
    public void should_return_allTransactions_when_transactions() {
        Clock clock = Mockito.mock(Clock.class);

        TransactionRepository transactionRepository = new TransactionRepository(clock);
        transactionRepository.addDeposit(-100);

        List<Transaction> transactions = transactionRepository.allTransactions();
        assertEquals(1, transactions.size());
        assertEquals(-100, transactions.get(0).amount());

    }
}
