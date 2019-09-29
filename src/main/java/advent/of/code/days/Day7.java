/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import advent.of.code.Main;
import advent.of.code.commons.Node;
import advent.of.code.commons.Worker;

/**
 * @author Jari
 *
 *         Needs a optimization and cleaning... Works for now.
 *
 */
public class Day7 {

	private static final Logger logger = Logger.getLogger(Day7.class.getSimpleName());

	private List<String> input;
	private Map<String, List<String>> edges = new LinkedHashMap<>();
	private List<String> nodes = new ArrayList<>();
	private Map<String, Short> timeLeftMap = new LinkedHashMap<>();
	private List<Worker> workerPool = new ArrayList<>();

	private Map<String, Node> nodesInProgress = new HashMap<>();

	private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int WORKERS = 5;
//	private short time = 0;

	public Day7() {
		this.input = Main.getCm().readFile("day7.txt");
		clearInput();
	}

	private void clearInput() {
		for (String value : input) {
			parseString(value);
		}
	}

	public void firstTask() {
		handle();
	}

	public void secondTask() {
		handleSecond();
	}

	private short getAlphaIndexes(String node) {

		logger.info(node + ": " + (ALPHABETS.indexOf(node) + 1));
		return (short) (ALPHABETS.indexOf(node) + 1);
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

	private void handleSecond() {
		clearInput();
		createTimeLeftMap();
		createWorkerPool();
		int counter = 0;
		while (!nodes.isEmpty()) {

			List<String> copyNodes = copyList();
			List<Node> nodeList = returnNodeObjsToWorkOn(copyNodes);

			handleNodes(nodeList);

			counter++;
		}
		logger.info("It will take: " + counter + " seconds to complite the construction.");

	}

	private void handleNodes(List<Node> nodeList) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (node.getAssignedTo() == null) {
				assignWorkerToNode(node);
			} else {
				Worker w = node.getAssignedTo();
				logger.info("Node: " + node.getName() + " is assigned to worker: " + w.getName());

				w.process();
				checkIfDone(node, w);
			}
		}
	}

	private void assignWorkerToNode(Node node) {
		for (int j = 0; j < workerPool.size(); j++) {
			Worker w = workerPool.get(j);
			if (w.isFree()) {
				w.setNodeAssigned(node);
				w.setSecondsLeft(getAlphaIndexes(node.getName()));
				node.setAssignedTo(w);
				w.process();
				nodesInProgress.put(node.getName(), node);
				logger.info("Node: " + node.getName() + " is assigned to worker: " + w.getName());
				checkIfDone(node, w);
				break;
			}
		}
	}

	private void checkIfDone(Node node, Worker w) {
		if (w.getSecondsLeft() == 0) {
			nodes.remove(node.getName());
			edges.remove(node.getName());
			w.setFree(true);
			w.setNodeAssigned(null);
			w.setSecondsLeft((short) 0);
			nodesInProgress.remove(node.getName());
		}
	}

	private void createWorkerPool() {

		for (int i = 1; i <= WORKERS; i++) {
			Worker worker = new Worker("Worker number: " + i, null);
			workerPool.add(worker);
		}
	}

	private void createTimeLeftMap() {

		for (String node : nodes) {
			short work = getAlphaIndexes(node);
			timeLeftMap.put(node, work);
		}

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
	 * Returns the node (in alphabetical order) that has been solved (previous
	 * required steps have been done).
	 *
	 * @param copyNodes
	 * @return
	 */
	private List<Node> returnNodeObjsToWorkOn(List<String> copyNodes) {
		for (List<String> edge : edges.values()) {
			for (String e : edge) {
				if (copyNodes.contains(e)) {
					copyNodes.remove(e);
				}
			}
		}

		Collections.sort(copyNodes);
		List<Node> list = new ArrayList<>();
		for (String s : copyNodes) {
			if (!nodesInProgress.containsKey(s)) {
				Node node = new Node(s);
				list.add(node);
			} else {
				list.add(nodesInProgress.get(s));
			}
		}
		return list;
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
