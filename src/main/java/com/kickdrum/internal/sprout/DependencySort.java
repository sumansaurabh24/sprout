package com.kickdrum.internal.sprout;

import java.util.List;
import java.util.Stack;

public class DependencySort {
	public Stack<Node> stack;

	public DependencySort() {
		stack = new Stack<>();
	}

	// Recursive toplogical Sort
	public void toplogicalSort(Node node) {
		List<Node> neighbours = node.getNeighbours();
		for (int i = 0; i < neighbours.size(); i++) {
			Node n = neighbours.get(i);
			if (n != null && !n.visited) {
				toplogicalSort(n);
				n.visited = true;
			}
		}
		stack.push(node);
	}

	public static void main(String arg[]) {

		DependencySort topological = new DependencySort();
		Node node0 = new Node(0);
		Node node26 = new Node(26);
		Node node27 = new Node(27);
		Node node28 = new Node(28);
		Node node29 = new Node(29);
		Node node30 = new Node(30);
		Node node37 = new Node(37);
		Node node41 = new Node(41);
		Node node42 = new Node(42);
		Node node43 = new Node(43);
		Node node44 = new Node(44);
		Node node45 = new Node(45);

		node0.addneighbours(node26); // 26 dependes on 0
		node0.addneighbours(node27); // 27 dependes on 0
		node0.addneighbours(node28); // 28 dependes on 0
		node0.addneighbours(node29); // 29 dependes on 0
		node0.addneighbours(node30); // 30 dependes on 0

		node26.addneighbours(node27); // 27 depends on 26
		node27.addneighbours(node28); // 28 depends on 27 -> 0,30,29,26,27,28

		System.out.println("Topological Sorting Order:");
		topological.toplogicalSort(node0);

		// Print contents of stack
		Stack<Node> resultStack = topological.stack;
		while (resultStack.empty() == false)
			System.out.print(resultStack.pop() + " ");
	}

}