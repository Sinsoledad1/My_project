package org.echoiot.util;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Random;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/7 13:23
 */
@Component
public class InviteCodeUtil {

    public static String generateInviteCode(int len) {
        Assert.isTrue(len > 0, "长度要大于0");

        char[] chars = {'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z',
                'X', '9', 'C', '7', 'P', '5', 'K', '3',
                'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y',
                'T', 'N', '6', 'B', 'G', 'H', 'A', 'L'};
        Random random = new Random();
        char[] inviteChars = new char[len];
        for (int i = 0; i < len; i++) {
            inviteChars[i] = chars[random.nextInt(chars.length)];
        }
        return String.valueOf(inviteChars);
    }
}