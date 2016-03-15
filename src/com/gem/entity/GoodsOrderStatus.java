package com.gem.entity;

public enum GoodsOrderStatus {
	//��������ջ��������ۣ���ɣ���ȡ��
	 NPay(1,"������"), Receipt(2,"���ջ�"),Evaluated(3,"������"),Finish(4,"���"),Cancel(5,"��ȡ��");
	private int goodsOrderStatusId;
	private String goodsOrderStatus;
	
	public static GoodsOrderStatus getEnumById(int id){
		switch(id){
		case 1:
			return  NPay;
		case 2:
			return Receipt;
		case 3:
			return Evaluated;
		case 4:
			return Finish;
		case 5:
			return Cancel;
		default:
			return null;			
		}
	}
	
	public int getGoodsOrderStatusId() {
		return goodsOrderStatusId;
	}

	public void setGoodsOrderStatusId(int goodsOrderStatusId) {
		this.goodsOrderStatusId = goodsOrderStatusId;
	}

	public String getGoodsOrderStatus() {
		return goodsOrderStatus;
	}

	public void setGoodsOrderStatus(String goodsOrderStatus) {
		this.goodsOrderStatus = goodsOrderStatus;
	}

	private GoodsOrderStatus(int goodsOrderStatusId, String goodsOrderStatus) {
		this.goodsOrderStatusId = goodsOrderStatusId;
		this.goodsOrderStatus = goodsOrderStatus;
	}
	
}
