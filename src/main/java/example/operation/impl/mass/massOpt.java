package example.operation.impl.mass;

import com.alibaba.fastjson.JSON;
import example.operation.entity.DynamicInfo;
import example.operation.entity.MassInfo;
import example.operation.entity.response.ResponseData;
import example.operation.entity.response.StatusCode;
import example.operation.impl.common.CommonService;
import example.tool.common.Assemble;
import example.tool.common.Common;
import example.tool.common.Mapper;
import example.tool.config.GlobalConfig;
import example.tool.parser.form.FormData;
import example.tool.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class massOpt {
    private static Logger logger = LoggerFactory.getLogger(massOpt.class);

    /**
     * 获取指定范围的列表数据
     */
    public static ResponseData getRangeMassToBg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            List<MassInfo> newsList = sqlSession.selectList(Mapper.GET_RANGE_MASS_TO_BG, map);
            int newsNum = sqlSession.selectOne(Mapper.GET_MASS_NUM);
            //检查是否查找到指定起始位置及数目的新闻并返回相应结果
            if (CommonService.checkNotNull(newsList)) {
                //设置回传的返回数据
                Map<String, Object> data = new HashMap<String, Object>(2);
                data.put(Common.TOTAL_NUM, newsNum);
                data.put(Common.MASS_LIST, newsList);
                Assemble.responseSuccessSetting(responseData, data);

            } else {
                message = "news info not found";
                massOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            massOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }

    /**
     * 更新指定任务列表的结果
     */
    public static ResponseData updateMassResult (Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        int num=0;
        int num2=0;
        try {
            MassInfo massInfo = (MassInfo) FormData.getParam(msg, MassInfo.class);
            DynamicInfo dynamicInfo = new DynamicInfo();
            num = sqlSession.update(Mapper.UPDATE_MASS, massInfo);
            if (num>0) {
                dynamicInfo.setUpdate_time(CommonService.getDateTime());
                dynamicInfo.setMedia_id(massInfo.getMedia_id());
                dynamicInfo.setMsg_id(massInfo.getMsg_id());
                num2 = sqlSession.update(Mapper.UPDATE_MASS_RESULT,dynamicInfo);
                if (num2>0) {
                    Assemble.responseSuccessSetting(responseData, null);
                }
                else {
                    message = "database UPDATE_MASS_RESULT influence record error";
                    massOpt.logger.warn(message);
                    Assemble.responseErrorSetting(responseData, 401, message);
                }
            }
            else {
                message = "database UPDATE_MASS influence record error";
                massOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        } catch (Exception e) {
            message = "sys error";
            massOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 更新上传图文结果
     * @param msg
     * @return
     */
    public static ResponseData updateMassUpload (Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        Map<String, Object> map = FormData.getParam(msg);
        String message = "";
        int num=0;
        try {
            String strJson = map.get(Common.UPDATE_LIST).toString();
            List<Map> list = JSON.parseArray(strJson,Map.class);
            MassInfo massInfo = new MassInfo();
            massInfo.setId(Integer.parseInt(map.get("id").toString()));
            massInfo.setMedia_id(map.get("media_id").toString());
            massInfo.setNews_count(Integer.parseInt(map.get("news_count").toString()));
            massInfo.setStatus_cd(Integer.parseInt(map.get("status_cd").toString()));
            massInfo.setTimestamp(map.get("timestamp").toString());
            //massInfo.setCreate_time(map.get("create_tome").toString());
            massInfo.setCreate_time(CommonService.getDateTime());

            num = sqlSession.insert(Mapper.INSERT_NEW_MASS, massInfo);
            if (num>0) {
                for (Map<String, Object> map2 : list) {
                    updateMassUploadOpt(sqlSession, map2);
                }
                //返回正确数据
                Assemble.responseSuccessSetting(responseData, null);
            }
            else {
                message = "database updateMassUpload influence record error";
                massOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        } catch (Exception e) {
            message = "sys error";
            massOpt.logger.warn(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            message = "sys commit";
            massOpt.logger.debug(message);
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 更新新闻列表的数据
     * @param
     * @return
     */
    public static void updateMassUploadOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        DynamicInfo dynamicInfo = new DynamicInfo();
        dynamicInfo.setTimestamp(map.get("timestamp").toString());
        dynamicInfo.setMedia_id(map.get("media_id").toString());
        dynamicInfo.setUpdate_time(CommonService.getDateTime());

        int num = sqlSession.update(Mapper.UPDATE_NEWS_UPLOAD, dynamicInfo);
        if (num > 0) {
            String message = "updateMassUpload from database success";
            massOpt.logger.debug(message);

        } else {
            String message = "updateMassUpload from database error";
            massOpt.logger.warn(message);
            throw new Exception(message);
        }
    }


    public static ResponseData deleteMass (Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            Map<String, Object> map = FormData.getParam(msg);
            map.put("id",Integer.parseInt(map.get("id").toString()));
            //删除数据和文件资源等操作
            deleteMassOpt(sqlSession, map);
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteNews error";
            massOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }

        return responseData;
    }

    /**
     * 删除群发任务数据操作
     */
    public static void deleteMassOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        int num = sqlSession.delete(Mapper.DELETE_MASS, map);
        int num2 = 0;
        if (num > 0) {
            //更新任务对应文章列表的media_id等群发关键数据
            DynamicInfo dynamicInfo = new DynamicInfo();
            dynamicInfo.setMedia_id(map.get("media_id").toString());
            dynamicInfo.setUpdate_time(CommonService.getDateTime());
            num2= sqlSession.update(Mapper.UPDATE_MASS_RESULT_LIST,dynamicInfo);

            if (num2>0) {
                String message = "delete file success";
                massOpt.logger.debug(message);
            }
            else {
                String message = "delete file UPDATE_MASS_RESULT_LIST from database error";
                massOpt.logger.warn(message);
                throw new Exception(message);
            }

        } else {
            String message = "delete file from database error";
            massOpt.logger.warn(message);
            throw new Exception(message);
        }
    }

}