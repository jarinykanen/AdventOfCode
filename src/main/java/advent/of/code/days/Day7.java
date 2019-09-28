/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day7 {

	private static final Logger logger = Logger.getLogger(Day7.class.getSimpleName());

	private List<String> input;
	private Map<String, List<String>> edges = new LinkedHashMap<>();
	private List<String> nodes = new ArrayList<>();

	public Day7() {
		this.input = Main.getCm().readFile("day7.txt");
	}

	public void firstTask() {

		for (String value : input) {
			parseString(value);
		}

		handle();
	}

	/*
	 * Find the order
	 */
	private void handle() {

		StringBuilder answer = new StringBuilder();

		while (!nodes.isEmpty()) {

			List<String> copyNodes = copyList();
			String node = returnNodeToWorkOn(copyNodes);
			answer.append(node);
			nodes.remove(node);
			edges.remove(node);
		}
		logger.info("Order is: " + answer);
	}

	/**
	 * Take a copy of a list
	 *
	 * @return
	 */
	private List<String> copyList() {

		List<String> copy = new ArrayList<>();
		for (String node : nodes) {
			copy.add(node);
		}

		return copy;
	}

	/**
	 * Returns the node (in alphabetical order) that has been solved (previous
	 * required steps have been done).
	 *
	 * @param copyNodes
	 * @return
	 */
	private String returnNodeToWorkOn(List<String> copyNodes) {
		for (List<String> edge : edges.values()) {
			for (String e : edge) {
				if (copyNodes.contains(e)) {
					copyNodes.remove(e);
				}
			}
		}

		Collections.sort(copyNodes);
		return copyNodes.get(0);
	}

	/**
	 * Parse input to a edge map and list of nodes
	 *
	 * @param string
	 */
	private void parseString(String string) {

		String[] split = string.split(" ");
		String mustBeDone = split[1];
		String nextToDo = split[7];

		if (!edges.containsKey(mustBeDone)) {
			List<String> points = new ArrayList<>();
			points.add(nextToDo);
			edges.put(mustBeDone, points);
		} else if (!edges.get(mustBeDone).contains(nextToDo)) {
			edges.get(mustBeDone).add(nextToDo);
		}

		if (!nodes.contains(mustBeDone)) {
			nodes.add(mustBeDone);
		} else if (!nodes.contains(nextToDo)) {
			nodes.add(nextToDo);
		}
	}

}
