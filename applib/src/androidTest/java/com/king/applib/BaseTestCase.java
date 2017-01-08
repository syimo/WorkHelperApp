package com.king.applib;

import android.content.Context;
import android.test.AndroidTestCase;

import com.king.applib.log.Logger;

/**
 * TestCase基础类
 * Created by HuoGuangxu on 2016/11/30.
 */

abstract class BaseTestCase extends AndroidTestCase {
    protected Context mContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getContext();
        Logger.init("aaa").methodCount(1).hideThreadInfo();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}