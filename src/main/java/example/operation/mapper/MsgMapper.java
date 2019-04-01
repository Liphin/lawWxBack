package example.operation.mapper;

import example.operation.entity.Msg;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface MsgMapper {
    /* select */
    @Select("select * from msg where create_time<#{create_time} order by create_time desc limit 120")
    public List<Msg> getRangeMsgToBg(@Param("create_time") String create_time);

    @SelectProvider(type = SqlProvider.class, method = "searchMsgData")
    public List<Msg> searchMsgData(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchMsgDataNum")
    public int searchMsgDataNum(Map<String, Object> map);

    //获取所有团队成员的记录数
    @Select("select count(*) from msg")
    public int getMsgNum();

    /* delete */
    @Delete("delete from msg where id=#{id}")
    public int deleteMsg(@Param("id") Integer id);


    /* update */
    @Update("update msg set status_cd=#{status_cd} ,update_time=#{update_time} where id=#{id}")
    public int updateMsg(Msg msg);

    @Update("update msg set stick_cd=#{stick_cd}, stick_time=#{stick_time} where timestamp=#{timestamp}")
    public int setMsgStickInfo(@Param("stick_cd") String stick_cd, @Param("stick_time") String stick_time, @Param("timestamp") String timestamp);

    /* insert */
    @Insert("insert into msg(name, phone, mail, content, status_cd, timestamp, create_time,update_time) " +
            "values(#{name}, #{phone}, #{mail}, #{content}, #{status_cd}, #{timestamp}, #{create_time},#{update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insertNewMsg(Msg msg);
}
