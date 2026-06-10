package voxera.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"members", "categories"})
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serverId;

    private String serverName;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    private List<ServerMember> members;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    private List<ChannelCategory> categories;
}
