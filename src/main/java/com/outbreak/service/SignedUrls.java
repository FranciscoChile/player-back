package com.outbreak.service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignedUrls {
    
    // [START cloudcdn_sign_url]
  /**
   * Creates a signed URL for a Cloud CDN endpoint with the given key
   * URL must start with http:// or https://, and must contain a forward
   * slash (/) after the hostname.
   *
   * @param url the Cloud CDN endpoint to sign
   * @param key url signing key uploaded to the backend service/bucket, as a 16-byte array
   * @param keyName the name of the signing key added to the back end bucket or service
   * @param expirationTime the date that the signed URL expires
   * @return a properly formatted signed URL
   * @throws InvalidKeyException when there is an error generating the signature for the input key
   * @throws NoSuchAlgorithmException when HmacSHA1 algorithm is not available in the environment
   */
  public static String signUrl(String url,
  byte[] key,
  String keyName,
  Date expirationTime)
throws InvalidKeyException, NoSuchAlgorithmException {

final long unixTime = expirationTime.getTime() / 1000;

String urlToSign = url
+ (url.contains("?") ? "&" : "?")
+ "Expires=" + unixTime
+ "&KeyName=" + keyName;

String encoded = SignedUrls.getSignature(key, urlToSign);
return urlToSign + "&Signature=" + encoded;
}

public static String getSignature(byte[] privateKey, String input)
throws InvalidKeyException, NoSuchAlgorithmException {

final String algorithm = "HmacSHA1";
final int offset = 0;
Key key = new SecretKeySpec(privateKey, offset, privateKey.length, algorithm);
Mac mac = Mac.getInstance(algorithm);
mac.init(key);
return  Base64.getUrlEncoder().encodeToString(mac.doFinal(input.getBytes()));
}
// [END cloudcdn_sign_url]

}
