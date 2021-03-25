package com.manastudent.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 利用并行流快速插入数据
 */
public class BatchInsertConsumer {

    // 一次往数据库中插入多少行记录
    private final static int SIZE = 1000;

    /**
     * 插入方法
     *
     * @param list     插入数据集合
     * @param consumer 消费型方法，直接使用 mapper::method 方法引用的方式
     * @param <T>      插入的数据类型
     */
    public static <T> void insertData(List<T> list, Consumer<List<T>> consumer) {
        if (list == null || list.size() < 1) {
            return;
        }

        List<List<T>> streamList = new ArrayList<>();

        for (int i = 0; i < list.size(); i += SIZE) {
            int j = Math.min((i + SIZE), list.size());
            List<T> subList = list.subList(i, j);
            streamList.add(subList);
        }

        streamList.parallelStream().forEach(consumer);
    }
}
