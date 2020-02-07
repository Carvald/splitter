package com.diatom.batch.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;


public class CountryItemProcessor implements ItemProcessor<SplitFile, SplitFile> {

  private static final Logger log = LoggerFactory.getLogger(SplitFile.class);

  public SplitFile process(final SplitFile splitFile) throws Exception {
    
	  
    log.info("Converting..."+splitFile.toString());
   	   
	   return null;

    
  }

}