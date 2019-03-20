package example.operation.impl.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import example.operation.entity.response.StatusCode;
import example.tool.common.*;
import example.tool.parser.form.FormData;
import example.tool.util.MybatisUtils;
import example.operation.entity.response.ResponseData;
import example.operation.impl.common.CommonService;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/28.
 */
public class Example {

    private static Logger logger = LoggerFactory.getLogger(Example.class);


    /**
     * overall中刷新页面时重新获取用户信息
     *
     * @param msg
     * @return
     */
    public static ResponseData example(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            Map<String, Object> map = FormData.getParam(msg);


        } catch (Exception e) {
            message = "";
            Example.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }
}
