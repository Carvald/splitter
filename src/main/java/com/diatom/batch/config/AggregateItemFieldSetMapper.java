package com.diatom.batch.config;



import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

/**
 * Delegating mapper to convert form a vanilla {@link FieldSetMapper} to one
 * that returns {@link AggregateItem} instances for consumption by the
 * {@link AggregateItemReader}.
 * 
 * @author Dave Syer
 * 
 */
public class AggregateItemFieldSetMapper<T> implements FieldSetMapper<AggregateItem<T>>, InitializingBean {

	private FieldSetMapper<T> delegate;

	private String end = "END";

	private String begin = "BEGIN";

	/**
	 * Public setter for the delegate.
	 * @param delegate the delegate to set
	 */
	public void setDelegate(FieldSetMapper<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Public setter for the end field value. If the {@link FieldSet} input has
	 * a first field with this value that signals the start of an aggregate
	 * record.
	 * 
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * Public setter for the begin value. If the {@link FieldSet} input has a
	 * first field with this value that signals the end of an aggregate record.
	 * 
	 * @param begin the begin to set
	 */
	public void setBegin(String begin) {
		this.begin = begin;
	}

	/**
	 * Check mandatory properties (delegate).
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(delegate, "A FieldSetMapper delegate must be provided.");
	}

	/**
	 * Build an {@link AggregateItem} based on matching the first column in the
	 * input {@link FieldSet} to check for begin and end delimiters. If the
	 * current record is neither a begin nor an end marker then it is mapped
	 * using the delegate.
	 * @param fieldSet a {@link FieldSet} to map
	 * 
	 * @return an {@link AggregateItem} that wraps the return value from the
	 * delegate
	 * @throws BindException if one of the delegates does
	 */
	
	public AggregateItem<T> mapFieldSet(FieldSet fieldSet) throws BindException {

		if (fieldSet.readString(0).equals(begin)) {
			return AggregateItem.getHeader();
		}
		if (fieldSet.readString(0).equals(end)) {
			return AggregateItem.getFooter();
		}

		return new AggregateItem<T>(delegate.mapFieldSet(fieldSet));

	}

}