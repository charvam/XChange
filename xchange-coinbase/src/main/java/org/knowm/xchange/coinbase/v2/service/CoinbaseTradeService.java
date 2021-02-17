package org.knowm.xchange.coinbase.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellTransactionV2;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellTransactionsResponse;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    params.setPageNumber(0);
    params.setPageLength(100);
    return params;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    final String apiKey = exchange.getExchangeSpecification().getApiKey();
    final BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    final List<CoinbaseBuySellTransactionV2> buyTransactions = new ArrayList<>();
    final List<CoinbaseBuySellTransactionV2> sellTransactions = new ArrayList<>();
    final AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    final Map<String, Wallet> wallets = accountInfo.getWallets();
    for (String account : wallets.keySet()) {
        if (account.length() == 36) {
            final CoinbaseBuySellTransactionsResponse buys
              = coinbase.getBuys(Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp, account);
            final CoinbaseBuySellTransactionsResponse sells
              = coinbase.getSells(Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp, account);
            buyTransactions.addAll(buys.getData());
            sellTransactions.addAll(sells.getData());
        }
    }
    return CoinbaseAdapters.adaptTrades(buyTransactions, sellTransactions);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
