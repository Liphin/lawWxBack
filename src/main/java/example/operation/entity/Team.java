package example.operation.entity;

public class Team {
    private int id;
    private String wx_user_id;
    private String wx_user_name;
    private String imgUrl;
    private String name;
    private int lvl_cd;
    private int status_cd;
    private String phone;
    private String mail;
    private String content;
    private String business_type_list;
    private int stick_cd;
    private String stick_time;
    private String timestamp;
    private String create_time;
    private String update_time;

    private int optType;
    private String search; //搜索的文本
    private String coverImage; //图片路径

    public Team() {
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLvl_cd() {
        return lvl_cd;
    }

    public void setLvl_cd(int lvl_cd) {
        this.lvl_cd = lvl_cd;
    }

    public int getStatus_cd() {
        return status_cd;
    }

    public void setStatus_cd(int status_cd) {
        this.status_cd = status_cd;
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

    public String getBusiness_type_list() {
        return business_type_list;
    }

    public void setBusiness_type_list(String business_type_list) {
        this.business_type_list = business_type_list;
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

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", wx_user_id='" + wx_user_id + '\'' +
                ", wx_user_name='" + wx_user_name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", lvl_cd=" + lvl_cd +
                ", status_cd=" + status_cd +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", content='" + content + '\'' +
                ", business_type_list='" + business_type_list + '\'' +
                ", stick_cd=" + stick_cd +
                ", stick_time='" + stick_time + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", optType=" + optType +
                ", search='" + search + '\'' +
                ", coverImage='" + coverImage + '\'' +
                '}';
    }
}
