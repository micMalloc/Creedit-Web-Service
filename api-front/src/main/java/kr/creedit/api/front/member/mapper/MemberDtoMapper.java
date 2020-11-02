package kr.creedit.api.front.member.mapper;

import kr.creedit.api.front.member.dto.MemberDto;
import kr.creedit.domain.rds.member.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberDtoMapper {

    MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);

    Member toEntity(MemberDto.Save dto);
}
