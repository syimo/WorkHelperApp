package com.king.app.workhelper;

import android.util.SparseArray;

import com.king.applib.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huoguangxu
 * @since 2017/3/31.
 */

public class ListTest extends BaseTestCase {

    private List<String> stringList;
    private List<String> hasNullList;
    private SparseArray<String> mStringSparseArray;

    @Override protected void setUp() throws Exception {
        super.setUp();
        stringList = new ArrayList<>();
        stringList.add("0");
        stringList.add("1");
        stringList.add("3");

        hasNullList = new ArrayList<>();
        hasNullList.add("4");
//        hasNullList.add(null);
        hasNullList.add("5");

        mStringSparseArray = new SparseArray<>();
    }

    //List是否可以添加null，是否会抛异常。
    public void testList() throws Exception {
        stringList.addAll(hasNullList);//strings1的内容可以为null，但是string1不能为null.
        for (int i = 0, size = stringList.size(); i < size; i++) {
            final String s = stringList.get(i);
            if (s == null) {
                Logger.i("第" + i + "个是null");
            } else {
                Logger.i(i + "===" + s);
            }
        }
    }

    //测试List.addAll()是深拷贝还是浅拷贝.结论：深复制.
    public void testAddAll() throws Exception {
        stringList.addAll(hasNullList);
        hasNullList = Arrays.asList("哈哈哈", "呵呵呵");

        printList(stringList);
        printList(hasNullList);
    }

    public void testSArray() throws Exception {
        mStringSparseArray.append(0, "000");
        mStringSparseArray.append(1, "aaa");
        mStringSparseArray.append(1, "bbb");//key相同时，把前面的值给覆盖了

        for (int i = 0, size = mStringSparseArray.size(); i < size; i++) {
            Logger.i("index: " + i + ";results: " + mStringSparseArray.get(mStringSparseArray.keyAt(i)));
        }
    }
    
    public void testListSub() throws Exception {
        List<String> subList = stringList.subList(1, 2);
//        subList.add("aaa");//修改sublist会影响原来的list
//        stringList.add("4");//修改原list,则sublist的所有操作会报错。java.util.ConcurrentModificationException
        stringList.clear();
        printList(stringList);
        printList(subList);
    }
}
