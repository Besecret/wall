package com.wanding.util;



import java.util.List;

import java.util.function.BinaryOperator;

import com.wanding.comm.User;



/**
 * Common until
 * 
 * @author wang.dong
 * @param <R>
 *
 */
public class CommonUtil {


    /**
     * mergeFunction implements List add
     * 
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

    /**
     * translate use id to name
     * @param id
     * @return
     */
    public static String getUserNameById(String id) {

        if (User.student.getId().equals(id)) {
            return User.student.getName();
        } else if (User.local_resident.getId().equals(id)) {
            return User.local_resident.getName();
        } else if (User.Long_term_resident.getId().equals(id)) {
            return User.Long_term_resident.getName();
        } else {
            return User.other.getName();
        }
    }
}
