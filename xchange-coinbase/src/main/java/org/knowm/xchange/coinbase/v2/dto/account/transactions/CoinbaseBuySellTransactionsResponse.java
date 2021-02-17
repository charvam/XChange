package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CoinbaseBuySellTransactionsResponse {
  private final List<CoinbaseBuySellTransactionV2> data;

  public CoinbaseBuySellTransactionsResponse(@JsonProperty("data") List<CoinbaseBuySellTransactionV2> data) {
    this.data = data;
  }
}
