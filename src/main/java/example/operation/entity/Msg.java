package example.operation.entity;

public class Msg {
    private int id;
    private String name;
    private int status_cd;
    private int type;
    private String phone;
    private String mail;
    private String content;
    private int stick_cd;
    private String stick_time;
    private String timestamp;
    private String create_time;
    private String update_time;

    public Msg() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus_cd() {
        return status_cd;
    }

    public void setStatus_cd(int status_cd) {
        this.status_cd = status_cd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStick_cd() {
        return stick_cd;
    }

    public void setStick_cd(int stick_cd) {
        this.stick_cd = stick_cd;
    }

    public String getStick_time() {
        return stick_time;
    }

    public void setStick_time(String stick_time) {
        this.stick_time = stick_time;
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
        return "msg{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status_cd=" + status_cd +
                ", type=" + type +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", content='" + content + '\'' +
                ", stick_cd=" + stick_cd +
                ", stick_time='" + stick_time + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
