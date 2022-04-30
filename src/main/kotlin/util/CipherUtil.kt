package com.jarvis.acg.api.util

import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CipherUtil {

    private const val AES_PK = "pctqoJFNVzc5vitI4WN1QV6QAzi3oAK+"
    private const val AES_IV = "tTUbDVcfDTl51w=="
    private const val CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"

    fun encode(content: ByteArray): String? {
        try {
            val secretKey: SecretKey = SecretKeySpec(AES_PK.toByteArray(), "AES")
            val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(AES_IV.toByteArray()))
            val byteAES = cipher.doFinal(content)
            return Base64.getEncoder().encodeToString(byteAES)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun decode(content: String): String? {
        try {
            val secretKey: SecretKey = SecretKeySpec(AES_PK.toByteArray(), "AES")
            val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(AES_IV.toByteArray()))
            val byteContent: ByteArray = Base64.getDecoder().decode(content)
            val byteDecode = cipher.doFinal(byteContent)
            return String(byteDecode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}