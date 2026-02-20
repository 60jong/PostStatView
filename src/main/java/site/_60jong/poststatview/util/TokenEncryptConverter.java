package site._60jong.poststatview.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Converter
@Component
public class TokenEncryptConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private final byte[] key;
    // CBC 모드는 IV(초기화 벡터)가 필요한데, 여기서는 가장 심플하게 키의 앞 16바이트를 IV로 사용하는 방식을 쓸게.
    private final byte[] iv;

    public TokenEncryptConverter(@Value("${encryption.secret-key}") String secretKey) {
        this.key = secretKey.getBytes();
        this.iv = secretKey.substring(0, 16).getBytes();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("토큰 암호화 중 에러 발생", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decoded = Base64.getDecoder().decode(dbData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException("토큰 복호화 중 에러 발생", e);
        }
    }
}