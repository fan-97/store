package com.store.domain;

public class CartItem {
	private Product product;//包含图片，名字，价格
	private int num;//商品数量
	private double subTotal;//商品小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	//小计通过数量跟价格计算
	public double getSubTotal() {
		return num*product.getShop_price();
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
