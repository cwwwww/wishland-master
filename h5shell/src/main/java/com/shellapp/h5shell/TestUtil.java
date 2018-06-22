package com.shellapp.h5shell;

/**
 * Created by MSI on 2018/6/15.
 */

public class TestUtil {

    private TestUtil testUtil;

    private TestUtil() {

    }

    public TestUtil getInstance() {
        if (testUtil == null) {
            synchronized (TestUtil.class) {
                if (testUtil == null) {
                    testUtil = new TestUtil();
                }
            }
        }
        return testUtil;
    }

}
