package com.erp.webtoon.service;

import com.erp.webtoon.TokenProvider;
import com.erp.webtoon.domain.RefreshToken;
import com.erp.webtoon.dto.token.TokenResponseDto;
import com.erp.webtoon.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

//    @Transactional
//    public void login(TokenResponseDto tokenDto){
//        RefreshToken refreshToken = RefreshToken.builder()
//                .refreshToken(tokenDto.getRefreshToken())
//                .expiration(tokenDto.getRefreshExpire())
//                .build();
//        String loginUserEmail = refreshToken.getEmail();
//        validateRefreshToken(tokenDto.getRefreshToken());
//        if (refreshTokenRepository.existsByEmail(loginUserEmail)){
//            log.info("기존의 존재하는 refresh 토큰 삭제");
//            refreshTokenRepository.deleteByEmail(loginUserEmail);
//        }
//        refreshTokenRepository.save(refreshToken);
//    }

//    @Transactional
//    public Optional<RefreshToken> getRefreshToken(String refreshToken){
//        return refreshTokenRepository.findByRefreshToken(refreshToken);
//    }
//
//    public Map<String, String> validateRefreshToken(String refreshToken){
//        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
//        String createdAccessToken = tokenProvider.validateRefreshToken(refreshToken1);
//
//        return createRefreshJson(createdAccessToken);
//    }
//
//    public Map<String, String> createRefreshJson(String createdAccessToken){
//        Map<String, String> map = new HashMap<>();
//        if(createdAccessToken == null){
//            map.put("errortype", "Forbidden");
//            map.put("status", "402");
//            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
//
//            return map;
//        }
//
//        // 기존에 존재하는 accessToken 제거
//        map.put("status", "200");
//        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
//        map.put("accessToken", createdAccessToken);
//
//        return map;
//    }
}
