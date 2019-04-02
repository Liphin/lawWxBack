package example.operation.impl.team;

import com.alibaba.fastjson.JSON;
import example.operation.entity.Team;
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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class teamOpt {
    private static Logger logger = LoggerFactory.getLogger(teamOpt.class);

    /**
     * 获取所有成员数据
     *
     * @param msg http传递的数据
     */
    public static ResponseData getRangeTeamListToBg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            List<Team> teamList = sqlSession.selectList(Mapper.GET_RANGE_TEAM_TO_BG, map);
            int teamNum = sqlSession.selectOne(Mapper.GET_TEAM_NUM);
            //检查是否查找到指定起始位置及数目的新闻并返回相应结果
            if (CommonService.checkNotNull(teamList)) {
                //设置回传的返回数据
                Map<String, Object> data = new HashMap<String, Object>(2);
                data.put(Common.TOTAL_NUM, teamNum);
                data.put(Common.TEAM_LIST_DATA, teamList);
                Assemble.responseSuccessSetting(responseData, data);

            } else {
                message = "team info not found";
                teamOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            teamOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }

    /**
     * 获取指定范围的团队成员信息到移动端
     */
    public static ResponseData getTeamListToPh(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            map.put("status_cd",Integer.parseInt(map.get("status_cd").toString()));
            List<Team> teamList = sqlSession.selectList(Mapper.GET_RANGE_TEAM_TO_PH, map);
            if (CommonService.checkNotNull(teamList)) {
                //设置回传的返回数据
                Assemble.responseSuccessSetting(responseData, teamList);

            } else {
                message = "team info not found";
                teamOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e){
            message = "sys error";
            teamOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;

    }

    /**
     * 删除成员数据操作
     */
    public static void deleteTeamOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        int num = sqlSession.delete(Mapper.DELETE_TEAM, map);
        if (num > 0) {
            //删除成员封面数据
            String dynamicInfoCoverImg = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_COVERIMG);

            //删除新闻封面文件数据
            String coverImg = dynamicInfoCoverImg + map.get(Common.TIMESTAMP) + Common.SUFFIX_PNG;
            deleteFile(coverImg);


        } else {
            String message = "delete file from database error";
            teamOpt.logger.warn(message);
            throw new Exception(message);
        }
    }

    /**
     * 同时删除多条新闻
     *
     * @param msg
     */
    public static ResponseData deleteTeam(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            Map<String, Object> map = FormData.getParam(msg);
            map.put("id",Integer.parseInt(map.get("id").toString()));
            //删除数据和文件资源等操作
            deleteTeamOpt(sqlSession, map);
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteTeam error";
            teamOpt.logger.error(message, e);
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
    public static ResponseData deleteBatchTeam(Object msg) {
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
                deleteTeamOpt(sqlSession, map);
            }
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteBatchTeam error";
            teamOpt.logger.error(message, e);
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
    public static ResponseData searchTeamData(Object msg) {
        return CommonService.simpleImplOpt(false, (responseData, sqlSession) -> {
            Map<String, Object> map = FormData.getParam(msg);
            List<Team> list = sqlSession.selectList(Mapper.SEARCH_TEAM_DATA, map);
            int newsNum = sqlSession.selectOne(Mapper.SEARCH_TEAM_DATA_NUM, map);
            Map<String, Object> data = new HashMap<String, Object>(2);
            data.put(Common.TOTAL_NUM, newsNum);
            data.put(Common.TEAM_LIST_DATA, list);
            Assemble.responseSuccessSetting(responseData, data);
        });
    }

    /**
     * 保存成员数据
     *
     * @param msg
     * @return
     */
    public static ResponseData saveTeamData(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        int num = 0;
        try {
            //前端新闻数据获取
            Team team = (Team) FormData.getParam(msg, Team.class);
            //根据操作类型动态进行新闻插入或更新数据库等操作
            if (team.getOptType() == Common.DYNAMIC_OPT_INSERT) {
                //设置时间戳、创建时间和更新时间等
                team.setCreate_time(CommonService.getDateTime());
                team.setUpdate_time(CommonService.getDateTime());
                num = sqlSession.insert(Mapper.INSERT_NEW_TEAM, team);

            } else if (team.getOptType() == Common.DYNAMIC_OPT_UPDATE) {
                //更新更新时间
                team.setUpdate_time(CommonService.getDateTime());
                num = sqlSession.update(Mapper.UPDATE_TEAM, team);
            }

            //根据更新的数据number动态进行不同返回
            if (num > 0) {
                Assemble.responseSuccessSetting(responseData, null);

            } else {
                message = "database influence record error";
                teamOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "saveTeamData system error";
            teamOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 设置动态信息的置顶设置
     * @return
     */
    public static ResponseData setTeamStickInfo(Object msg) {
        return CommonService.simpleImplOpt(true, (responseData, sqlSession) -> {

            //获取传递过来的数据
            String message = "";
            Map<String, Object> map = FormData.getParam(msg);

            //设置置顶标记及置顶时间戳
            int num = sqlSession.update(Mapper.SET_TEAM_STICK_INFO, map);

            //返回数据
            if (num > 0) {
                //数据库更新成功
                Assemble.responseSuccessSetting(responseData, null);

            } else {
                //数据库更新失败
                message = "db error";
                teamOpt.logger.error(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        });
    }

    /**
     * 删除单文件操作
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }


}
