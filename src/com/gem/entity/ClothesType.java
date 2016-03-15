package com.gem.entity;

public enum ClothesType {
	COLTHES(1,"����"),LUXURY(2,"�ݳ�Ʒ"),BED(3,"�Ҿ���Ʒ");
	private int clothesType;
	private String cTypeName;
	//����id�õ�ö�ٶ���
	public static ClothesType valueOf(int clothesType){
		switch(clothesType){
		case 1:
			return ClothesType.valueOf("COLTHES");
		case 2:
			return ClothesType.valueOf("LUXURY");
		case 3:
			return ClothesType.valueOf("BED");
		default:
			return null;
		}
	}
	public int getClothesType() {
		return clothesType;
	}
	public void setClothesType(int clothesType) {
		this.clothesType = clothesType;
	}
	public String getcTypeName() {
		return cTypeName;
	}
	public void setcTypeName(String cTypeName) {
		this.cTypeName = cTypeName;
	}
	private ClothesType(int clothesType,String cTypeName){
		this.clothesType=clothesType;
		this.cTypeName=cTypeName;
	}
}
