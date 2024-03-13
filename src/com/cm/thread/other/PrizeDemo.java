package com.cm.thread.other;

import java.util.*;

/**
 * @author 陈萌
 * @describe 奖品Demo
 * @date 2022/12/6 08:14
 */
public class PrizeDemo {

    public static void main(String[] args) {

        // 奖品集
        List<String> prizeList = new ArrayList<>();
        prizeList.add("奖品 1");
        prizeList.add("奖品 2");
        prizeList.add("奖品 3");
        prizeList.add("奖品 4");
        prizeList.add("奖品 5");
        prizeList.add("奖品 6");

        // 结果集
        Map<Integer, String> prizeResultMap = new HashMap<>();

        int i = 1;
        while (!prizeList.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(prizeList.size());
            String prize = prizeList.get(index);
            prizeList.remove(index);
            prizeResultMap.put(i, prize);
            i++;
        }

        prizeResultMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(PrizeDemo::accept);

    }

    private static void accept(Map.Entry<Integer, String> prizeEntry) {
        if (prizeEntry == null) {
            return;
        }
        System.out.println("序号：" + prizeEntry.getKey() + "  " + "奖品：" + prizeEntry.getValue());
    }

}
