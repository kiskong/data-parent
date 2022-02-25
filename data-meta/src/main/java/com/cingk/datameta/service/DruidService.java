package com.cingk.datameta.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.BaseEncoding;
import org.springframework.stereotype.Service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-15
 */
@Service
public class DruidService {

    public void getData() {

        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("druid-pool-").build();
        ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);
        for (int i = 0; i < 600; i++) {
            executorService.execute(new SubThread());
        }
        executorService.shutdown();

    }


    class SubThread implements Runnable {
        Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }


        }
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DruidService ds = new DruidService();

        Jedis jedis = RedisDS.create().getJedis();
        String sb = jedis.get("kgrp:check_rule_file:cisp:interfaceCode:G3007_T");
        jedis.close();

        JSONObject parms = JSONObject.parseObject(sb);

        String check = parms.getString("check");

//        URLUtil.decode(new String(BaseEncoding.base64().decode(check),StandardCharsets.UTF_8));

        byte[] bchars = check.getBytes(StandardCharsets.UTF_8);

        //google
        byte[] bcheck = BaseEncoding.base64Url().decode(check);
        byte[] iv = Arrays.copyOfRange(bcheck, 0, 16);
        byte[] rule = Arrays.copyOfRange(bcheck, 16, bcheck.length);
        String key = "RHTJ8888RHTJ8888";
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes(StandardCharsets.UTF_8));
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(iv);
        SymmetricCrypto aes = new SymmetricCrypto("AES/CFB8/NoPadding", secretKey, algorithmParameterSpec);

        String rs = aes.decryptStr(rule, StandardCharsets.UTF_8);
        String checkRule = new String(BaseEncoding.base64Url().decode(rs),StandardCharsets.UTF_8);

        System.out.println(checkRule);
    }

}
