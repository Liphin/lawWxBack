package example.operation.mapper;

import example.operation.entity.DynamicInfo;
import example.operation.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface DynamicInfoMapper {
    /****************** 趣文数据操作 *********************/
    /* select */
    //获取指定起始create_time以及数目的新闻数据-->返回后台前端
    @Select("select * from dynamicinfo where type=1 and create_time<#{create_time} order by create_time desc limit 120")
    public List<DynamicInfo> getRangeInterestToBg(@Param("create_time") String create_time);

    @Select("select * from dynamicinfo where type=2 and create_time<#{create_time} order by create_time desc limit 120")
    public List<DynamicInfo> getRangeStudyToBg(@Param("create_time") String create_time);

    @Select("select * from dynamicinfo where type=3 and create_time<#{create_time} order by create_time desc limit 120")
    public List<DynamicInfo> getRangeDynamicToBg(@Param("create_time") String create_time);

    //管理员在手机客户端审核时搜索相关标题的数据
    @SelectProvider(type = SqlProvider.class, method = "searchInterestData")
    public List<DynamicInfo> searchInterestData(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchStudyData")
    public List<DynamicInfo> searchStudyData(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchDynamicData")
    public List<DynamicInfo> searchDynamicData(Map<String, Object> map);


    //管理员在手机客户端审核时搜索相关标题的数据数目
    @SelectProvider(type = SqlProvider.class, method = "searchInterestDataNum")
    public int searchInterestDataNum(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchStudyDataNum")
    public int searchStudyDataNum(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchDynamicDataNum")
    public int searchDynamicDataNum(Map<String, Object> map);

    //获取所有趣文数据的记录数
    @Select("select count(*) from dynamicinfo where type=1")
    public int getInterestNum();

    //获取所有研究所数据的记录数
    @Select("select count(*) from dynamicinfo where type=2")
    public int getStudyNum();

    //获取所有律所资讯数据的记录数
    @Select("select count(*) from dynamicinfo where type=3")
    public int getDynamicNum();

    /* delete */
    @Delete("delete from dynamicinfo where id=#{id}")
    public int deleteNews(@Param("id") Integer id);

    /* insert */
    //注册操作， 默认用户名为手机号，因为注册时需要验证码，验证过该手机用户存在，绑定微信后再进行update个人信息处理
    @Insert("insert into dynamicinfo(subtype,type, title, wx_user_id, wx_user_name, status_cd, timestamp, create_time,update_time) " +
            "values(#{subtype},#{type}, #{title}, #{wx_user_id}, #{wx_user_name}, #{status_cd}, #{timestamp}, #{create_time},#{update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insertNewArticle(DynamicInfo dynamicInfo);

    /* update */
    //管理员更新news信息操作
    @Update("update dynamicinfo set subtype=#{subtype} ,type=#{type}, title=#{title}, status_cd=1, update_time=#{update_time} where id=#{id}")
    public int updateNews(DynamicInfo dynamicInfo);

    //更新趣文数据置顶功能
    @Update("update dynamicinfo set stick_cd=#{stick_cd}, stick_time=#{stick_time} where timestamp=#{timestamp}")
    public int setDynamicStickInfo(@Param("stick_cd") String stick_cd, @Param("stick_time") String stick_time, @Param("timestamp") String timestamp);

}
