/**
 *
 */
package advent.of.code.days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import advent.of.code.Main;

/**
 * @author Jari
 *
 */
public class Day4 {
	private static final Logger logger = Logger.getLogger(Day4.class.getSimpleName());

	private List<String> inputList = Main.getCm().readFile("day4.txt");
	private Map<Integer, List<Integer>> duplicateMinutes = new HashMap<>();
	private Map<Integer, Integer> mostMinutes = new HashMap<>();
	private int winnerId;

	public void firstTask() {

		Collections.sort(inputList);

		handleInput();
		getLength();

		Integer winnerMinute = getDublicate();

		int value = winnerId * winnerMinute;
		logger.info("The ID of the guard I chose multiplied by the minute: " + value);

	}

	public void secondTask() {
		handleInput();

		Integer winnerMinute = getSameMinute();
		int value = winnerId * winnerMinute;
		logger.info("The ID of the guard I chose multiplied by the minute: " + value);
	}

	/**
	 * Handle input if not handeled already
	 */
	private void handleInput() {

		if (!duplicateMinutes.isEmpty() && !mostMinutes.isEmpty()) {
			return;
		}

		int sleep = 0;
		int guardId = 0;

		for (String input : inputList) {
			Calendar timeStamp = getTimeStamp(input.substring(1, 17));
			String action = input.substring(19);

			if (action.contains("#")) {
				guardId = Integer.parseInt(action.split(" ")[1].split("#")[1]);
			}

			if (action.contains("falls asleep")) {
				sleep = timeStamp.get(Calendar.MINUTE);
			}
			if (action.contains("wakes up")) {
				handleWakeUp(sleep, guardId, timeStamp);
			}
		}
	}

	/**
	 * Handle wake up action
	 *
	 * @param sleep
	 * @param guardId
	 * @param timeStamp
	 */
	private void handleWakeUp(int sleep, int guardId, Calendar timeStamp) {
		int wakeUp;
		int minutes;
		wakeUp = timeStamp.get(Calendar.MINUTE);
		List<Integer> minuteList = new ArrayList<>();

		if (duplicateMinutes.containsKey(guardId)) {
			minuteList = duplicateMinutes.get(guardId);

		}
		for (int i = sleep; i < wakeUp; i++) {
			minuteList.add(i);
		}

		minutes = wakeUp - sleep;
		if (mostMinutes.containsKey(guardId)) {
			Integer storedMinutes = mostMinutes.get(guardId);
			minutes = minutes + storedMinutes;
		}

		mostMinutes.put(guardId, minutes);
		Collections.sort(minuteList);
		duplicateMinutes.put(guardId, minuteList);
	}

	/**
	 * Get timestamp of string
	 *
	 * @param time
	 * @return
	 */
	private Calendar getTimeStamp(String time) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = formatter.parse(time);
			Calendar cal = formatter.getCalendar();
			cal.setTime(date);

			return cal;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	/**
	 * Sort mostMinutes map by value
	 *
	 */
	private void getLength() {

		Map<Integer, Integer> sorted = mostMinutes.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		Entry<Integer, Integer> entry = sorted.entrySet().iterator().next();

		winnerId = entry.getKey();
		logger.severe("Longest sleeper is: " + winnerId + ". Slept " + entry.getValue() + " minutes");
	}

	public Integer getDublicate() {

		List<Integer> list = duplicateMinutes.get(winnerId);
		Map<Integer, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

		Map<Integer, Long> sorted = counts.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return sorted.entrySet().iterator().next().getKey();
	}

	public Integer getSameMinute() {

		Long counter = 0L;
		Integer minuteToBreakIn = 0;

		for (Entry<Integer, List<Integer>> entry : duplicateMinutes.entrySet()) {
			Map<Integer, Long> counts = entry.getValue().stream()
					.collect(Collectors.groupingBy(e -> e, Collectors.counting()));

			for (Entry<Integer, Long> entry2 : counts.entrySet()) {
				if (entry2.getValue() > counter) {
					counter = entry2.getValue();
					minuteToBreakIn = entry2.getKey();
					winnerId = entry.getKey();
				}
			}
		}

		return minuteToBreakIn;
	}

}
