package com.store.test;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		Map<String, Node> map = new HashMap<>();
		Node n = new Node();
		n.setNum(12);
		map.put("1", n);
		Node old = map.get("1");
		old.setNum(133);
		Node node = map.get("1");
		System.out.println(node.getNum());
	}

	static class Node {
		private int num;

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

	}
}
