package com.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//购物车模型 包含2个属性 3个方法  添加、删除、清空
public class Cart {
	private double total;// 总共价格和积分
	private Map<String, CartItem> map = new HashMap<String, CartItem>();

	/**
	 * 返回购物车中所有的商品清单
	 * @return
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public double getTotal() {
		total = 0;
		Collection<CartItem> values = map.values();
		for (CartItem item : values) {
			total += item.getSubTotal();
		}
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * 添加商品到购物车中
	 * 
	 * @param cartItem
	 */
	public void addCartItemToCartI(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();
		if (map.containsKey(pid)) {
			// 购物车中存在该商品
			CartItem oldItem = map.get(pid);
			oldItem.setNum(cartItem.getNum() + oldItem.getNum());
		} else {
			map.put(pid, cartItem);
		}
	}

	/**
	 * 移除购物车中的商品
	 * 
	 * @param pid
	 */
	public void removeCartItem(String pid) {
		map.remove(pid);
	}

	/**
	 * 清空购物车
	 */
	public void removeCart() {
		map.clear();
	}
}
