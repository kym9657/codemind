package testcases.C0028_UNINIT;/* TEMPLATE GENERATED TESTCASE FILE
Filename: CWE327_Use_Broken_Crypto__3DES_01.java
Label Definition File: CWE327_Use_Broken_Crypto.label.xml
Template File: point-flaw-01.tmpl.java
*/
/*
* @description
* CWE: 327 Use of Broken or Risky Cryptographic Algorithm
* Sinks: 3DES
*    GoodSink: use AES
*    BadSink : use 3DES
* Flow Variant: 01 Baseline
*
* */



import testcasesupport.*;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


// compile error
public class C0028_CWE457_Uninitialized_Variable__basic_01 
{
 
    private void bad(boolean check) throws Throwable
    {

        // String CIPHER_INPUT;
        // if(check) CIPHER_INPUT = "ABCDEFG123456";

        // KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        // /* Perform initialization of KeyGenerator */
        // keyGenerator.init(128);

        // SecretKey secretKey = keyGenerator.generateKey();
        // byte[] byteKey = secretKey.getEncoded();

        // /* FIX: Use a stronger crypto algorithm, AES */
        // SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "AES");

        // Cipher aesCipher = Cipher.getInstance("AES");
        // aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // byte[] encrypted = aesCipher.doFinal(CIPHER_INPUT.getBytes("UTF-8"));

        // IO.writeLine(IO.toHex(encrypted));

    }

}

