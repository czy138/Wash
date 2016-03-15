package com.gem.entity;

public enum OrderStatus {
	//�������ȡ�£������У�����ɣ���ȡ��
	PAYNO(1,"������"),GETNO(2,"��ȡ��"),SERVICE(3,"������"),DONE(4,"�����"),CANCEL(5,"��ȡ��"),PAYED(6,"�Ѹ���");
	private int statusId;
	private String orderStatus;	
	public static OrderStatus valueOf(int statusId){
		switch(statusId){
		case 1:
			return OrderStatus.valueOf("PAYNO");
		case 2:
			return OrderStatus.valueOf("GETNO");
		case 3:
			return OrderStatus.valueOf("SERVICE");
		case 4:
			return OrderStatus.valueOf("DONE");
		case 5:
			return OrderStatus.valueOf("CANCEL");
		case 6:
			return OrderStatus.valueOf("PAYED");
		default:
			return null;
		}
	}
	private OrderStatus(int statusId, String orderStatus) {
		this.statusId = statusId;
		this.orderStatus = orderStatus;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
