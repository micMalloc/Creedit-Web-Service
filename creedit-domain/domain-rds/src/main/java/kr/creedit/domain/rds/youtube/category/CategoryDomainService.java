package kr.creedit.domain.rds.youtube.category;

import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.Channels;

public interface CategoryDomainService {

    /**
     * 한 주간 가장 베스트인 채널 반환 서비스
     * @param channels 카테고리의 채널 리스트
     * @return Channel 주간 베스트 채널
     */
    Channel getBestChannelForWeek(Channels channels);
}
