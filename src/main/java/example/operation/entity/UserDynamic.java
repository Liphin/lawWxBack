package example.operation.entity;

public class UserDynamic {
    private int id;
    private String wx_user_id;
    private int dynamic_id;
    private String dynamic_timestamp;
    private int dynamic_view;
    private int dynamic_pitch;
    private String timestamp;
    private String create_time;
    private String update_time;

    public UserDynamic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWx_user_id() {
        return wx_user_id;
    }

    public void setWx_user_id(String wx_user_id) {
        this.wx_user_id = wx_user_id;
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public String getDynamic_timestamp() {
        return dynamic_timestamp;
    }

    public void setDynamic_timestamp(String dynamic_timestamp) {
        this.dynamic_timestamp = dynamic_timestamp;
    }

    public int getDynamic_view() {
        return dynamic_view;
    }

    public void setDynamic_view(int dynamic_view) {
        this.dynamic_view = dynamic_view;
    }

    public int getDynamic_pitch() {
        return dynamic_pitch;
    }

    public void setDynamic_pitch(int dynamic_pitch) {
        this.dynamic_pitch = dynamic_pitch;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "UserDynamic{" +
                "id=" + id +
                ", wx_user_id='" + wx_user_id + '\'' +
                ", dynamic_id=" + dynamic_id +
                ", dynamic_timestamp='" + dynamic_timestamp + '\'' +
                ", dynamic_view=" + dynamic_view +
                ", dynamic_pitch=" + dynamic_pitch +
                ", timestamp='" + timestamp + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
