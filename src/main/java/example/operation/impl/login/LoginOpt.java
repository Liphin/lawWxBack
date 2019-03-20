package example.operation.impl.login;

import example.operation.entity.Manager;
import example.operation.entity.response.ResponseData;
import example.operation.entity.response.StatusCode;
import example.operation.impl.common.CommonService;
import example.tool.common.Assemble;
import example.tool.common.Common;
import example.tool.common.Mapper;
import example.tool.parser.form.FormData;
import example.tool.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginOpt {
    private static Logger logger = LoggerFactory.getLogger(LoginOpt.class);

    /**
     * 管理者登录后台系统，该步骤进行账号密码验证
     * @param msg
     * @return
     */
    public static ResponseData managerLogin(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            Manager manager = sqlSession.selectOne(Mapper.MANAGER_LOGIN,map);
            //检查是否查找到manager信息返回相应结果
            if(CommonService.checkNotNull(manager)){
                manager.setPassword(null);
                Assemble.responseSuccessSetting(responseData, manager);

            }else {
                message = "manager info not found";
                LoginOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            LoginOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }


    /**
     * 根据用户id和sessionId获取用户数据
     * @param msg
     * @return
     */
    public static ResponseData getUserInfoByIdAndSessionId(Object msg){
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            String wxUserId = String.valueOf(map.get(Common.WX_USER_ID));
            Manager manager = sqlSession.selectOne(Mapper.GET_MANAGER_DATA,wxUserId);
            //检查是否查找到manager信息返回相应结果
            if(CommonService.checkNotNull(manager)){
                manager.setPassword(null);
                Assemble.responseSuccessSetting(responseData, manager);

            }else {
                message = "manager info not found";
                LoginOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            LoginOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }
}
