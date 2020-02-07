package com.diatom.batch.config;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.model.Country;


/**
 * @author Dan Garrette
 * @since 2.0
 */
public class DelegatingTradeLineAggregator implements LineAggregator<Object> {
	private LineAggregator<Country> tradeLineAggregator;
	

	public String aggregate(Object item) {
		if (item instanceof Country) {
			return this.tradeLineAggregator.aggregate((Country) item);
		}
		else {
			throw new RuntimeException();
		}
	}

	public void setTradeLineAggregator(LineAggregator<Country> tradeLineAggregator) {
		this.tradeLineAggregator = tradeLineAggregator;
	}

}