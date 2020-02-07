package com.diatom.batch.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
 
public class ConsoleItemWriter<T> implements ItemWriter<T> { 
   
    public void write(List<? extends T> items) throws Exception { 
    	System.out.println(items.size());
        for (T item : items) { 
            System.out.println(item); 
        } 
    } 
}