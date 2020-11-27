package kr.creedit.domain.rds.youtube.channel;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Channels {

    @OneToMany(mappedBy = "category")
    @OrderBy("id DESC")
    private final List<Channel> channels;

    public Channels() {
        this.channels = new ArrayList<>();
    }

    public Channels(List<Channel> channels) {
        this.channels = channels;
    }

    public void add(Channel channel) {
        this.channels.add(channel);
    }

    public int size() {
        return channels.size();
    }
}
