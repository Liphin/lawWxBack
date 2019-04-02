package example.operation.mapper;

import example.operation.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface TeamMapper {
    /* select */
    @Select("select * from team where create_time<#{create_time} order by create_time desc limit 120")
    public List<Team> getRangeTeamToBg(@Param("create_time") String create_time);

    @Select("select * from team where status_cd=#{status_cd} and create_time<#{create_time} order by create_time desc")
    public List<Team> getRangeTeamToPh(@Param("create_time") String create_time,@Param("status_cd") Integer status_cd);

    @SelectProvider(type = SqlProvider.class, method = "searchTeamData")
    public List<Team> searchTeamData(Map<String, Object> map);

    @SelectProvider(type = SqlProvider.class, method = "searchTeamDataNum")
    public int searchTeamDataNum(Map<String, Object> map);

    //获取所有团队成员的记录数
    @Select("select count(*) from team")
    public int getTeamNum();

    /* delete */
    @Delete("delete from team where id=#{id}")
    public int deleteTeam(@Param("id") Integer id);

    /* insert */
    @Insert("insert into team(wx_user_id, wx_user_name, imgUrl, name, status_cd, phone, mail, content, timestamp, create_time,update_time) " +
            "values(#{wx_user_id}, #{wx_user_name}, #{imgUrl}, #{name},#{status_cd},#{phone},#{mail},#{content},#{timestamp}, #{create_time},#{update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insertNewTeam(Team team);

    /* update */
    @Update("update team set status_cd=#{status_cd} ,name=#{name},phone=#{phone},mail=#{mail}, content=#{content},update_time=#{update_time} where id=#{id}")
    public int updateTeam(Team team);

    @Update("update team set stick_cd=#{stick_cd}, stick_time=#{stick_time} where timestamp=#{timestamp}")
    public int setTeamStickInfo(@Param("stick_cd") String stick_cd, @Param("stick_time") String stick_time, @Param("timestamp") String timestamp);
}
