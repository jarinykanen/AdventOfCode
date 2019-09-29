/**
 *
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day8 {

	private List<Integer> input = new ArrayList<>();
	private int index = 0;

	private List<Integer> nodes = new ArrayList<>();
	private List<Integer> meta = new ArrayList<>();

	public Day8() {
		String raw = Main.getCm().readFile("day8.txt").get(0);
		List<String> stringList = Arrays.asList(raw.split(" "));
		parseToShort(stringList);
	}

	private void parseToShort(List<String> stringList) {
		stringList.stream().forEach(string -> input.add(Integer.parseInt(string)));
	}

	public void bothTasks() {
		Integer part2 = handle();
		int finalValue = calculateFinal();
		Main.getLogger().info("Sum of all metadata entries is: " + finalValue);
		Main.getLogger().info("Value of the root node is: " + part2);

	}

	private int calculateFinal() {
		int finalValue = 0;
		for (Integer value : meta) {
			finalValue += value;
		}
		return finalValue;
	}

	/**
	 * Handle the reading recursively
	 *
	 * @return
	 */
	private Integer handle() {
		int nodeCount = getNexInt();
		int metaCount = getNexInt();

		List<Integer> tempMetaValue = new ArrayList<>();
		List<Integer> tempNodeValue = new ArrayList<>();
		Integer metaValue = 0;

		IntStream.range(0, nodeCount).forEachOrdered(n -> {
			tempNodeValue.add(handle());
		});

		IntStream.range(0, metaCount).forEachOrdered(n -> {
			Integer value = getNexInt();
			meta.add(value);
			tempMetaValue.add(value);
		});

		if (nodeCount == 0) {
			metaValue = calculateSum(tempMetaValue);
		} else {
			for (Integer s : tempMetaValue) {
				if (s >= 1 && s <= tempNodeValue.size()) {
					metaValue += tempNodeValue.get(s - 1);
				}
			}
		}
		return metaValue;
	}

	private Integer calculateSum(List<Integer> tempMetaValue) {
		Integer finalValue = 0;
		for (Integer value : tempMetaValue) {
			finalValue += value;
		}
		return finalValue;
	}

	private int getNexInt() {
		index++;
		return input.get(index - 1);
	}

}
