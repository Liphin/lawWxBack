package example.operation.entity;

public class Manager {
    private int id;
    private String manager_account;
    private String manager_name;
    private int lvl_cd;
    private String wx_user_id;
    private String wx_user_name;
    private String mobile;
    private String password;
    private String timestamp;
    private String create_time;
    private String update_time;

    public Manager() {
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

    public String getWx_user_name() {
        return wx_user_name;
    }

    public void setWx_user_name(String wx_user_name) {
        this.wx_user_name = wx_user_name;
    }

    public String getManager_account() {
        return manager_account;
    }

    public void setManager_account(String manager_account) {
        this.manager_account = manager_account;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public int getLvl_cd() {
        return lvl_cd;
    }

    public void setLvl_cd(int lvl_cd) {
        this.lvl_cd = lvl_cd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Manager{" +
                "id=" + id +
                ", manager_account='" + manager_account + '\'' +
                ", manager_name='" + manager_name + '\'' +
                ", lvl_cd=" + lvl_cd +
                ", wx_user_id='" + wx_user_id + '\'' +
                ", wx_user_name='" + wx_user_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
