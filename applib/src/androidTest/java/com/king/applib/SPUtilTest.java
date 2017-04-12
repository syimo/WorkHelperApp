package com.king.applib;

import com.king.applib.log.Logger;
import com.king.applib.util.ContextUtil;
import com.king.applib.util.ExtendUtil;
import com.king.applib.util.SPUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author huoguangxu
 * @since 2017/3/6.
 */

public class SPUtilTest extends BaseTestCase {
    @Override protected void setUp() throws Exception {
        super.setUp();
        ContextUtil.init(mContext);
    }

    public static class TestOo {
        public String name;
        public int age;

        public TestOo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override public String toString() {
            return "TestOo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    //其实还是putString
    public void testPutStringList() throws Exception {
        List<String> stringList = Arrays.asList("哈哈哈", "呵呵呵", "哦哦哦");
        SPUtil.putStringList("1111", stringList);
    }

    public void testGetStringList() throws Exception {
        List<String> stringList = SPUtil.getStringList("1111");
        ExtendUtil.printList(stringList);
    }

    public void testPutListToSp() throws Exception {
        List<TestOo> listOo = Arrays.asList(new TestOo("1111", 1111), new TestOo("2222", 2222), new TestOo("3333", 3333));
        SPUtil.putListToSp("2222", listOo);
//        SPUtil.putListToSp("3333", Arrays.asList(1, 2, 3));
//        List<Integer> listFromSp = SPUtil.getListFromSp("3333", Integer.class);
//        ExtendUtil.printList(listFromSp);
    }

    public void testGetListFromSp() throws Exception {
        List<TestOo> listFromSp = SPUtil.getListFromSp("2222", TestOo.class);
        ExtendUtil.printList(listFromSp);
    }

    //同时操作多个sp文件，后者无效。
    public void testPutAtTheSameTime() throws Exception {
        SPUtil.putBoolean("aaaaaaaaa", true);
        SPUtil.getSP("spname").edit().putBoolean("aaa", false).putInt("ccc", 1).putString("bbb", "bbb").apply();
    }

    public void testGetAtTheSameTime() throws Exception {
        Logger.i(SPUtil.getBoolean("aaaaaaaaa") + "");
        Logger.i(SPUtil.getSP("spname").getString("bbb", "-1"));
    }

    public void testPutSp() throws Exception {
        
        //不要连续put
        /*SPUtil.putBoolean("aaaaaaaaa", true);
        SPUtil.putBoolean("boolean", false);
        SPUtil.putFloat("float", 5.20f);
        SPUtil.putInt("int", 100);
        SPUtil.putLong("long", 321651);
        SPUtil.putString("string", "双方而上方的");*/
        
        //这样做
        SPUtil.getSP().edit()
                .putBoolean("aaaaaaaaa", true)
                .putBoolean("boolean", false)
                .putFloat("float", 5.20f)
                .putInt("int", 100)
                .putLong("long", 321651)
                .putString("string", "双方而上方的")
                .apply();

    }
    
    public void testPutSet() throws Exception {
        Set<String> texts = new HashSet<>();
        texts.add("董藩");
        texts.add("带看佛陈");
        texts.add("董双管");
        texts.add("；困啊");
        texts.add("哈哈哈");
        texts.add("哈哈哈");
        SPUtil.getSP().edit().putStringSet("list_set", texts).apply();
    }
    
    public void testGetSet() throws Exception {
        Set<String> list_set = SPUtil.getSP().getStringSet("list_set", null);
    }
    
    public void testAnotherSP() throws Exception {
        SPUtil.getSP("spname").edit().putBoolean("aaa", false).putInt("ccc", 1).putString("bbb", "bbb").apply();
    }

    public void testRemove() throws Exception {
        //不要连续remove
        /*SPUtil.remove("string_list");
        SPUtil.remove("list_set");
        SPUtil.remove("int");
        SPUtil.remove("long");
        SPUtil.remove("string");
        SPUtil.remove("aaaaaaaaa");
        SPUtil.remove("boolean");
        SPUtil.remove("float");*/

        //多次commit，一次apply.
        SPUtil.getSP().edit()
                .remove("string_list")
                .remove("list_set")
                .remove("int")
                .remove("long")
                .remove("float")
                .remove("string")
                .remove("aaaaaaaaa")
                .remove("boolean")
                .apply();
        
    }

}
