package com.wanding.util;


import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import com.wanding.model.UserInfo;


/**
 * Common until
 * @author wang.dong
 * @param <R>
 *
 */
public class CommonUtil  {

    
    /**
     * mergeFunction  implements List add
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> BinaryOperator<List<T>> mergeFunction() {

        BinaryOperator<List<T>> funtion = (oldList, newList) -> {
            oldList.addAll(newList);
            return oldList;
        };
        return funtion;
    }

  
}
