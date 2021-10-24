package com.holun.msm.utills;

/**
 *  动态生成短信验证码的工具类
 */
public class VerifyCode {

    /**
     * 创建指定数量的随机字符串
     * @param isNumber 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean isNumber, int length){
        String resultStr = "";
        String codeContent = isNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        boolean flag = true;
        do {
            int count = 0;
            for (int i = 0; i < length; i++) {
                double randomCode = Math.random() * codeContent.length();
                int code = (int) Math.floor(randomCode);
                char c = codeContent.charAt(code);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                resultStr += codeContent.charAt(code);
            }
            if (count >= 2) {
                flag = false;
            }
        } while (flag);
        return resultStr;
    }
}
