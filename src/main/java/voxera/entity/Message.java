package voxera.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"channel", "sender"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 4000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    private long timestamp;

    @PrePersist
    public void onCreate() {
        if (timestamp <= 0L) {
            timestamp = Instant.now().toEpochMilli();
        }
    }
}