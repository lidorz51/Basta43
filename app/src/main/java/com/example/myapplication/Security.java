/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {
        private static final String ALGORITHM = "AES";
        private static final String KEY = "1Hbfh667adfDEJ78";

        public static String encrypt(String value) throws Exception
        {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(Security.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
            String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
            return encryptedValue64;

        }

        private static Key generateKey() throws Exception
        {
            Key key = new SecretKeySpec(Security.KEY.getBytes(),Security.ALGORITHM);
            return key;
        }
}
