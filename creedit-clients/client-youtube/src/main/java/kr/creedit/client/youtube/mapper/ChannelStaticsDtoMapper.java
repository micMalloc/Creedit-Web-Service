package kr.creedit.client.youtube.mapper;

import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChannelStaticsDtoMapper {

    ChannelStaticsDtoMapper INSTANCE = Mappers.getMapper(ChannelStaticsDtoMapper.class);

    @Mapping(target = "viewCount", source = "dto.viewCount")
    @Mapping(target = "subscriberCount", source = "dto.subscriberCount")
    @Mapping(target = "hiddenSubscriberCount", source = "dto.hiddenSubscriberCount")
    @Mapping(target = "videoCount", source = "dto.videoCount")
    Statistics toEntity(ChannelStatisticsDto.StatisticsDto dto);
}
