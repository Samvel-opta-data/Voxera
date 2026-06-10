package voxera.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category", "messages"})
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer channelId;

    private String channelName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ChannelCategory category;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Message> messages;
}


