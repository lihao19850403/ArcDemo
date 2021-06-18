package com.lihao.arcdemo.utils;

import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }
}
