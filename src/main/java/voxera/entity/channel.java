package voxera.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class channel {

    private int channelId;
    private String channelName;
    private String channelDescription;
    private String channelType;
    private String channelStatus;
    private int serverId;

}
