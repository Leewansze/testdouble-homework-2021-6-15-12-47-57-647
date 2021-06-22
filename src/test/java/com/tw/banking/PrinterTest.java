package com.tw.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.tw.banking.Printer.SEPARATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrinterTest {

    private Console console;

    private Printer printer;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        console = mock(Console.class);
        printer = spy(new Printer(console));
        transaction = mock(Transaction.class);
    }

    @Test
    public void testPrint_shouldPrintHeader_andPrintStatementLinesWithTransactions() {
        //given
        List<Transaction> transactions = Collections.singletonList(transaction);
        //when
        printer.print(transactions);
        //then
        verify(console, times(2)).printLine(any());
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
