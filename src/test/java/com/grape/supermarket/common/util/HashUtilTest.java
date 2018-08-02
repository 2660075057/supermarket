package com.grape.supermarket.common.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by LXP on 2017/9/27.
 */
public class HashUtilTest extends TestCase {
    @Test
    public void testSha1() throws Exception {
        System.out.println(HashUtil.sha1("as1109317898"));
    }

}