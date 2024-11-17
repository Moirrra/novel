package com.moirrra.novel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.resp.ImgVerifyCodeRespDto;
import com.moirrra.novel.manager.redis.VerifyCodeManager;
import com.moirrra.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description: 资源相关服务实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Override
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(ImgVerifyCodeRespDto.builder()
                        .sessionId(sessionId)
                        .img(verifyCodeManager.genImgVerifyCode(sessionId))
                        .build());
    }
}
