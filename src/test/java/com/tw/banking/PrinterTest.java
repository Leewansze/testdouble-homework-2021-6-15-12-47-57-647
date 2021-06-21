package com.tw.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static com.tw.banking.Printer.SEPARATOR;
import static com.tw.banking.Printer.STATEMENT_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrinterTest {

    private Console console;

    private Printer printer;

    @BeforeEach
    void setUp() {
        console = mock(Console.class);
        printer = spy(new Printer(console));
    }

    @Test
    public void testPrint_shouldPrintHeader_andPrintStatementLinesWithTransactions() {
        Transaction tx1 = mock(Transaction.class);
        doReturn(1).when(tx1).amount();
        Transaction tx2 = mock(Transaction.class);
        doReturn(2).when(tx2).amount();
        doReturn(1).when(tx1).compareTo(eq(tx2));

        doReturn("DUMMY_STRING").when(printer).statementLine(any(), anyInt());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(tx1);
        transactions.add(tx2);
        printer.print(transactions);

        verify(console, times(1)).printLine(STATEMENT_HEADER);
        verify(console, times(2)).printLine("DUMMY_STRING");
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(printer, times(2)).statementLine(transactionArgumentCaptor.capture(), integerArgumentCaptor.capture());

        List<Transaction> allTxs = transactionArgumentCaptor.getAllValues();
        assertEquals(tx1, allTxs.get(0));
        assertEquals(tx2, allTxs.get(1));
        List<Integer> runningBalances = integerArgumentCaptor.getAllValues();
        assertEquals(1, runningBalances.get(0));
        assertEquals(3, runningBalances.get(1));
    }

    @Test
    void testStatementLine_shouldPrintCorrectly() {
        Transaction tx = mock(Transaction.class);
        doReturn("date").when(tx).date();
        doReturn(1).when(tx).amount();

        String statementLine = printer.statementLine(tx, 2);
        assertEquals("date" + SEPARATOR + "1" + SEPARATOR + "2", statementLine);
    }
}
