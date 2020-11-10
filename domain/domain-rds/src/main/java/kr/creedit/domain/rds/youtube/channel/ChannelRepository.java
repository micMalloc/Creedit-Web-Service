package kr.creedit.domain.rds.youtube.channel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByChannelId(String channelId);

}
