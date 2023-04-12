package com.esprit.examen.services;

import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @InjectMocks
    StockServiceImpl stockService;

    @Mock
    StockRepository stockRepository;

    @Test
    public void retrieveAllStocksTest() {
        // Given
        List<Stock> expectedStocks = Arrays.asList(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(expectedStocks);

        // When
        List<Stock> result = stockService.retrieveAllStocks();

        // Then
        assertEquals(expectedStocks, result);
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void addStockTest() {
        // Given
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        // When
        Stock result = stockService.addStock(stock);

        // Then
        assertEquals(stock, result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void deleteStockTest() {
        // Given
        Long id = 1L;

        // When
        stockService.deleteStock(id);

        // Then
        verify(stockRepository, times(1)).deleteById(id);
    }

    @Test
    public void updateStockTest() {
        // Given
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        // When
        Stock result = stockService.updateStock(stock);

        // Then
        assertEquals(stock, result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void retrieveStockTest() {
        // Given
        Long id = 1L;
        Stock expectedStock = new Stock();
        when(stockRepository.findById(id)).thenReturn(Optional.of(expectedStock));

        // When
        Stock result = stockService.retrieveStock(id);

        // Then
        assertEquals(expectedStock, result);
        verify(stockRepository, times(1)).findById(id);
    }

    @Test
    public void retrieveStatusStockTest() {
        // Given
        List<Stock> stocksEnRouge = Arrays.asList(
                new Stock("Stock 1", 5, 10),
                new Stock("Stock 2", 2, 8)
        );
        when(stockRepository.retrieveStatusStock()).thenReturn(stocksEnRouge);

        // When
        String result = stockService.retrieveStatusStock();

        // Then
        verify(stockRepository, times(1)).retrieveStatusStock();
    }
}
