package example.operation.mapper;

import example.operation.entity.UserDynamic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDynamicMapper {

    /********************以下是user_dynamic表Select操作***********************/
    //选择 “用户-新闻” 表数据
    @Select("select * from user_dynamic where wx_user_id=#{wx_user_id} and dynamic_timestamp=#{dynamic_timestamp}")
    public UserDynamic getUserDynamicInfo(UserDynamic userDynamic);
    //public UserDynamic getUserDynamicInfo(@Param("wx_user_id") String wx_user_id, @Param("dynamic_timestamp") String dynamic_timestamp);


    /********************以下是user_dynamic表Insert操作***********************/
    @Insert("insert into user_dynamic(wx_user_id, dynamic_id, dynamic_timestamp, dynamic_view, dynamic_pitch, timestamp, create_time)" +
            " values(#{wx_user_id}, #{dynamic_id}, #{dynamic_timestamp}, #{dynamic_view}, #{dynamic_pitch}, #{timestamp}, #{create_time})")
    public int insertNewUserDynamicInfo(UserDynamic userDynamic);


    /********************以下是user_dynamic表Update操作***********************/
    //更新 “用户-新闻”表该用户点赞数
    @Update("update user_dynamic set dynamic_pitch=#{dynamic_pitch} where wx_user_id=#{wx_user_id} and dynamic_timestamp=#{dynamic_timestamp}")
    public int updateUserDynamicPitchCount(@Param("wx_user_id") String wx_user_id, @Param("dynamic_timestamp") String dynamic_timestamp, @Param("dynamic_pitch") Integer dynamic_pitch);

}
