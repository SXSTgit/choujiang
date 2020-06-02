package com.itsq.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @version 创建时间：2019年12月06日 下午5:53:20
 * @ClassName 类名称
 * @Description 类描述
 */
public class ListQuChongUtils {

	/**
	 * 循环list中的所有元素然后删除重复
	 * 
	 * @param list 待去重的list
	 * @return 去重后的list
	 */
	public static <T> List<T> removeDuplicate(List<T> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	/**
	 * 通过HashSet去重
	 * 
	 * @param list 待去重的list
	 * @return 去重后的list
	 */
	public static <T> List<T> removeDuplicateHashSet(List<T> list) {
		HashSet<T> hs = new HashSet<>(list);
		list.clear();
		list.addAll(hs);
		return list;
	}

	/**
	 * 删除List中重复元素，并保持顺序
	 * 
	 * @param list 待去重的list
	 * @return 去重后的list
	 */
	public static <T> List<T> removeDuplicateKeepOrder(List<T> list) {
		Set set = new HashSet();
		List<T> newList = new ArrayList<>();
		for (T element : list) {
			// set能添加进去就代表不是重复的元素
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
		return list;
	}

	/**
	 * 利用list.contain() 去重
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> removeDuplicateContain(List<T> list) {
		List<T> listTemp = new ArrayList<>();
		for (T aList : list) {
			if (!listTemp.contains(aList)) {
				listTemp.add(aList);
			}
		}
		return listTemp;
	}

	
	
}
