package com.gem.entity;

import java.util.Date;

public class Orders {
	private int orderId;
	private User user;
	private OrderStatus orderStatus;
	private Business business;
	private Address address;
	private UserCoupon userCoupon;
	private Date orderTime;//ԤԼ
	private Date singleTime;//�µ�
	private Date collectTime;//ȡ��
	private Date sentTime;//����
	private String remark;
	private double total;//ʹ���Ż�ȯ֮ǰ���ܼ�
	private int payType;//0����֧������1��������
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public UserCoupon getUserCoupon() {
		return userCoupon;
	}
	public void setUserCoupon(UserCoupon userCoupon) {
		this.userCoupon = userCoupon;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getSingleTime() {
		return singleTime;
	}
	public void setSingleTime(Date singleTime) {
		this.singleTime = singleTime;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	
}
