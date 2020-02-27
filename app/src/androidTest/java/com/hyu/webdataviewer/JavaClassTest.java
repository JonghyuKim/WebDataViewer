package com.hyu.webdataviewer;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JavaClassTest {

    @Test
    public void testJavaFunc(){
        String hello = null;

        int ret = getLicenceType("PREMIUM", hello);

        System.out.println("hello!!!! " + ret);
    }

    public static int getLicenceType(String rewardType, String licenseSubType) {
        int licenseType = 0;

        if ("PREMIUM".equals(rewardType)) {

            switch (licenseSubType) {
                case "SUBSCRIBE_1":
                    licenseType = 1;
                    break;
                case "SUBSCRIBE_3":
                    licenseType = 2;
                    break;
                case "SUBSCRIBE_12":
                    licenseType = 3;
                    break;
                case "PURCHARES":
                    licenseType = 4;
                    break;
            }
        }

        return licenseType;
    }


}
