package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.resp.ImgVerifyCodeRespDto;

import java.io.IOException;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description: 资源相关服务类
 * @Version: 1.0
 */

public interface ResourceService {
    /**
     * 获取图片验证码
     * @return 图片验证码
     * @throws IOException 验证码图片生成失败
     */
    RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException;

}
