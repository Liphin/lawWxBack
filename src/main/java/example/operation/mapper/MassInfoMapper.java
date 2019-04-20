package example.operation.mapper;
import example.operation.entity.DynamicInfo;
import example.operation.entity.MassInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


public interface MassInfoMapper {
    /****************** 任务操作 *********************/
    @Select("select * from massInfo where create_time<#{create_time} order by create_time desc limit 120")
    public List<MassInfo> getRangeMassToBg(@Param("create_time") String create_time);

    @Select("select count(*) from massInfo")
    public int getMassNum();

    @Update("update massInfo set msg_id=#{msg_id} ,msg_data_id=#{msg_data_id} where id=#{id}")
    public int updateMass(MassInfo massInfo);

    @Delete("delete from massInfo where id=#{id}")
    public int deleteMass(@Param("id") Integer id);

    //注册操作， 默认用户名为手机号，因为注册时需要验证码，验证过该手机用户存在，绑定微信后再进行update个人信息处理
    @Insert("insert into massInfo(media_id,status_cd, news_count,timestamp, create_time) " +
            "values(#{media_id},#{status_cd},#{news_count}, #{timestamp}, #{create_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insertNewMass(MassInfo massInfo);

}
