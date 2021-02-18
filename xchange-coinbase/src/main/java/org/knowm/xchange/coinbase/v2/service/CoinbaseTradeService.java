package org.knowm.xchange.coinbase.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellTransactionsResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.math.BigDecimal;

public final class CoinbaseTradeService extends CoinbaseTradeServiceRaw implements TradeService {

  public CoinbaseTradeService(Exchange exchange) {
    super(exchange);
  }

  /**
   * ********************************************************************************************************************************************************
   */
  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinbaseTradeHistoryParams();
  }

  public UserTrades getBuysTradeHistory(CoinbaseTradeHistoryParams params, String accountId) throws IOException {
    final String apiKey = exchange.getExchangeSpecification().getApiKey();
    final BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    final CoinbaseBuySellTransactionsResponse buys = coinbase.getBuys(
            params.getLimit(),
            params.getStartId(),
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signatureCreator2,
            timestamp,
            accountId
    );
    return CoinbaseAdapters.adaptTrades(buys.getData(), Order.OrderType.BID);
  }

  public UserTrades getSellsTradeHistory(CoinbaseTradeHistoryParams params, String accountId) throws IOException {
    final String apiKey = exchange.getExchangeSpecification().getApiKey();
    final BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    final CoinbaseBuySellTransactionsResponse buys = coinbase.getSells(
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signatureCreator2,
            timestamp,
            accountId,
            params.getLimit(),
            params.getStartId()
    );
    return CoinbaseAdapters.adaptTrades(buys.getData(), Order.OrderType.ASK);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
