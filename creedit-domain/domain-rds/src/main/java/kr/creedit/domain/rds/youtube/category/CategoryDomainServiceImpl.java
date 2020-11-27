package kr.creedit.domain.rds.youtube.category;

import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.Channels;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryDomainServiceImpl implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    @Override
    public Channel getBestChannelForWeek(Channels channels) {
        categoryRepository.findById(1L);
        return null;
    }
}
