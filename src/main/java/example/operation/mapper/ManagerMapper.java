package example.operation.mapper;

import example.operation.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ManagerMapper {
    /********************以下是选择user操作***********************/
    //管理者登录管理平台，验证账号密码是否正确
    @Select("select * from manager where manager_account=#{account} and password=#{password} ")
    public Manager managerLogin(@Param("account") String account, @Param("password") String password);

    //根据管理者id获取管理者信息数据
    @Select("select * from manager where wx_user_id=#{wx_user_id}")
    public Manager getManagerData(String wx_user_id);
}
