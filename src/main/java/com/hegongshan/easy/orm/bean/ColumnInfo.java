package com.hegongshan.easy.orm.bean;

import com.hegongshan.easy.orm.annotation.Order;

public class ColumnInfo {
	private String value;
	private boolean isForeignKey;
	private Order order;
	private boolean orderBy;
	private Integer priority;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isOrderBy() {
		return orderBy;
	}

	public void setOrderBy(boolean orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public boolean isForeignKey() {
		return isForeignKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isForeignKey ? 1231 : 1237);
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + (orderBy ? 1231 : 1237);
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnInfo other = (ColumnInfo) obj;
		if (isForeignKey != other.isForeignKey)
			return false;
		if (order != other.order)
			return false;
		if (orderBy != other.orderBy)
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
