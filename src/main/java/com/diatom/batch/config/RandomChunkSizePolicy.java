package com.diatom.batch.config;

import java.util.Random;

import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author Michael Minella
 */
public class RandomChunkSizePolicy implements CompletionPolicy {

	private int chunksize;
	private int totalProcessed;
	private Random random = new Random();

	public boolean isComplete(RepeatContext context,
			RepeatStatus result) {

		if(RepeatStatus.FINISHED == result) {
			return true;
		}
		else {
			return isComplete(context);
		}
	}

	
	public boolean isComplete(RepeatContext context) {
		return this.totalProcessed >= chunksize;
	}

	public RepeatContext start(RepeatContext parent) {
		this.chunksize = random.nextInt(20);
		this.totalProcessed = 0;

		System.out.println("The chunk size has been set to " +
				this.chunksize);

		return parent;
	}

	public void update(RepeatContext context) {
		this.totalProcessed++;
	}
}