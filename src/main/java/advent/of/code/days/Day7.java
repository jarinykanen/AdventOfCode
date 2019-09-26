/**
 * 
 */
package advent.of.code.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Jari
 *
 */
public class Day7 {

    private static final Logger logger = Logger.getLogger(Day7.class.getSimpleName());

    private static final String INPUT = "Step C must be finished before step A can begin.\r\n"
	    + "Step C must be finished before step F can begin.\r\n"
	    + "Step A must be finished before step B can begin.\r\n"
	    + "Step A must be finished before step D can begin.\r\n"
	    + "Step B must be finished before step E can begin.\r\n"
	    + "Step D must be finished before step E can begin.\r\n"
	    + "Step F must be finished before step E can begin.";

    private List<String> inputList = Arrays.asList(INPUT.split("\r\n"));

    private List<String> order = new ArrayList<>();

    private Map<String, Integer> map = new LinkedHashMap<>();

    public void firstTask() {

//	ExecutorService executorService = Executors.newFixedThreadPool(10);
//	IntStream.range(0, inputList.size()).forEach(i -> parseString(inputList.get(i)));
//	executorService.shutdown();
	for (String value : inputList) {
	    parseString(value);
	}

	int counter = 0;

	createMap(counter);

	List<String> newList = inputList;
	compare(newList);

    }

    private void createMap(int counter) {
	for (int i = 0; i < order.size(); i++) {
	    String[] split = order.get(i).split("->");
	    String mustBeDone = split[0];
	    String nextToDo = split[1];
	    if (!map.containsKey(mustBeDone)) {
		counter++;
		map.put(mustBeDone, counter);
	    } else if (map.containsKey(mustBeDone)) {
		Integer oldValue = map.get(mustBeDone);
		counter++;
		Integer newValue = counter;
		map.put(mustBeDone, newValue);
	    }

	    if ((i + 1) < order.size()) {
		String[] split2 = order.get(i + 1).split("->");
		String mustBeDoneNext = split2[0];
		if (!mustBeDone.equals(mustBeDoneNext)) {
		    counter = 0;
		}
	    }

	}

    }

    private void parseString(String string) {

	String[] split = string.split(" ");
	String mustBeDone = split[1];
	String nextToDo = split[7];

	order.add(mustBeDone + "->" + nextToDo);
    }

    private void compare(List<String> newList) {

	for (String value : newList) {
	    String[] parse = value.split("->");
	    String first = parse[0];
	    String second = parse[1];

	}

    }

}
