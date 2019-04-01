package example.operation.impl.msg;

import com.alibaba.fastjson.JSON;
import example.operation.entity.Msg;
import example.operation.entity.response.ResponseData;
import example.operation.entity.response.StatusCode;
import example.operation.impl.common.CommonService;
import example.operation.impl.interestArticle.interestOpt;
import example.tool.common.Assemble;
import example.tool.common.Common;
import example.tool.common.Mapper;
import example.tool.parser.form.FormData;
import example.tool.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class msgOpt {

    private static Logger logger = LoggerFactory.getLogger(msgOpt.class);
    /**
     * 获取所有成员数据
     *
     * @param msg http传递的数据
     */
    public static ResponseData getRangeMsgListToBg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            List<Msg> msgList = sqlSession.selectList(Mapper.GET_RANGE_MSG_TO_BG, map);
            int msgNum = sqlSession.selectOne(Mapper.GET_MSG_NUM);
            //检查是否查找到指定起始位置及数目的新闻并返回相应结果
            if (CommonService.checkNotNull(msgList)) {
                //设置回传的返回数据
                Map<String, Object> data = new HashMap<String, Object>(2);
                data.put(Common.TOTAL_NUM, msgNum);
                data.put(Common.MSG_LIST_DATA, msgList);
                Assemble.responseSuccessSetting(responseData, data);

            } else {
                message = "msg info not found";
                msgOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            msgOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }

    /**
     * 删除成员数据操作
     */
    public static void deleteMsgOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        int num = sqlSession.delete(Mapper.DELETE_MSG, map);
        if (num <= 0) {
            String message = "delete data from database error";
            msgOpt.logger.warn(message);
            throw new Exception(message);
        }
    }

    /**
     * 同时删除多条新闻
     *
     * @param msg
     */
    public static ResponseData deleteMsg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            Map<String, Object> map = FormData.getParam(msg);
            map.put("id",Integer.parseInt(map.get("id").toString()));
            //删除数据和文件资源等操作
            deleteMsgOpt(sqlSession, map);
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteMsg error";
            msgOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 批量删除新闻数据
     *
     * @param msg
     */
    public static ResponseData deleteBatchMsg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            //获取传递过来的数据
            String listStr = (String) FormData.getParam(msg, Common.DELETE_LIST);
            //解析Json数组数据
            List<Map> list = JSON.parseArray(listStr, Map.class);
            //循环传递过来的list中每个数据，并删除每条新闻数据
            for (Map<String, Object> map : list) {
                deleteMsgOpt(sqlSession, map);
            }
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteBatchMsg error";
            msgOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 更新留言数据操作
     */
    public static void setupMsgOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        int num = sqlSession.update(Mapper.UPDATE_MSG, map);
        if (num <= 0) {
            String message = "update data from database error";
            msgOpt.logger.warn(message);
            throw new Exception(message);
        }
    }

    /**
     * 同时更新多条留言
     *
     * @param msg
     */
    public static ResponseData setupMsg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            Map<String, Object> map = FormData.getParam(msg);
            map.put("id",Integer.parseInt(map.get("id").toString()));
            map.put("status_cd",Integer.parseInt(map.get("status_cd").toString()));
            //删除数据和文件资源等操作
            setupMsgOpt(sqlSession, map);
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "setupMsg error";
            msgOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 批量更新留言数据
     *
     * @param msg
     */
    public static ResponseData setupBatchMsg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            //获取传递过来的数据
            String listStr = (String) FormData.getParam(msg, Common.SETUP_LIST);
            //解析Json数组数据
            List<Map> list = JSON.parseArray(listStr, Map.class);
            //循环传递过来的list中每个数据，并删除每条新闻数据
            for (Map<String, Object> map : list) {
                setupMsgOpt(sqlSession, map);
            }
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "setupBatchMsg error";
            msgOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 后台前端搜索指定匹配字段的新闻数据
     *
     * @param msg
     * @return
     */
    public static ResponseData searchMsgData(Object msg) {
        return CommonService.simpleImplOpt(false, (responseData, sqlSession) -> {
            Map<String, Object> map = FormData.getParam(msg);
            List<Msg> list = sqlSession.selectList(Mapper.SEARCH_MSG_DATA, map);
            int newsNum = sqlSession.selectOne(Mapper.SEARCH_MSG_DATA_NUM, map);
            Map<String, Object> data = new HashMap<String, Object>(2);
            data.put(Common.TOTAL_NUM, newsNum);
            data.put(Common.MSG_LIST_DATA, list);
            Assemble.responseSuccessSetting(responseData, data);
        });
    }

    /**
     * 设置动态信息的置顶设置
     * @return
     */
    public static ResponseData setMsgStickInfo(Object msg) {
        return CommonService.simpleImplOpt(true, (responseData, sqlSession) -> {

            //获取传递过来的数据
            String message = "";
            Map<String, Object> map = FormData.getParam(msg);

            //设置置顶标记及置顶时间戳
            int num = sqlSession.update(Mapper.SET_MSG_STICK_INFO, map);

            //返回数据
            if (num > 0) {
                //数据库更新成功
                Assemble.responseSuccessSetting(responseData, null);

            } else {
                //数据库更新失败
                message = "db error";
                msgOpt.logger.error(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        });
    }

    /**
     * 保存留言信息
     */
    public static ResponseData saveMsgInfo(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        int num = 0;
        try {
            Msg msgInfo = (Msg) FormData.getParam(msg,Msg.class);
            msgInfo.setCreate_time(CommonService.getDateTime());
            msgInfo.setUpdate_time(CommonService.getDateTime());
            num=sqlSession.insert(Mapper.INSERT_NEW_MSG, msgInfo);
            if (num>0) {
                Assemble.responseSuccessSetting(responseData, null);
            }
            else {
                message = "database influence record error";
                msgOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "saveMsgInfo system error";
            msgOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }
}
