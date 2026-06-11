package voxera.service;

import org.springframework.stereotype.Service;
import voxera.entity.Channel;
import voxera.repository.ChannelRepository;

import java.util.List;

@Service
public class channelService {

    private final ChannelRepository channelRepository;

    public channelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    public List<Channel> findByCategoryId(Integer categoryId) {
        return channelRepository.findByCategoryId(categoryId);
    }

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }
}
