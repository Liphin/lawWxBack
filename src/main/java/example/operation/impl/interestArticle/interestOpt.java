package example.operation.impl.interestArticle;

import com.alibaba.fastjson.JSON;
import example.operation.entity.DynamicInfo;
import example.operation.entity.UserDynamic;
import example.operation.entity.response.ResponseData;
import example.operation.entity.response.StatusCode;
import example.operation.impl.common.CommonService;
import example.tool.common.Assemble;
import example.tool.common.Common;
import example.tool.common.Mapper;
import example.tool.config.GlobalConfig;
import example.tool.parser.form.FormData;
import example.tool.util.MybatisUtils;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class interestOpt {

    private static Logger logger = LoggerFactory.getLogger(interestOpt.class);

    /**
     * 获取所有趣文数据
     *
     * @param msg http传递的数据
     */
    public static ResponseData getRangeInterestListToBg(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            List<DynamicInfo> newsList = sqlSession.selectList(Mapper.GET_RANGE_INTEREST_TO_BG, map);
            int newsNum = sqlSession.selectOne(Mapper.GET_INTEREST_NUM);
            //检查是否查找到指定起始位置及数目的新闻并返回相应结果
            if (CommonService.checkNotNull(newsList)) {
                //设置回传的返回数据
                Map<String, Object> data = new HashMap<String, Object>(2);
                data.put(Common.TOTAL_NUM, newsNum);
                data.put(Common.INTEREST_LIST_DATA, newsList);
                Assemble.responseSuccessSetting(responseData, data);

            } else {
                message = "news info not found";
                interestOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "sys error";
            interestOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }

    /**
     * 获取指定范围的新闻数据
     */
    public static ResponseData getNewsListToPh(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            List<DynamicInfo> newsList = sqlSession.selectList(Mapper.GET_RANGE_NEWS_INFO_TO_PHONE, map);
            //检查是否查找到指定起始位置及数目的新闻并返回相应结果
            if (CommonService.checkNotNull(newsList)) {
                Assemble.responseSuccessSetting(responseData, newsList);

            } else {
                message = "news info not found";
                interestOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        } catch (Exception e) {
            message = "sys error";
            interestOpt.logger.debug(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);
        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }

    /**
     * 后台前端搜索指定匹配字段的新闻数据
     *
     * @param msg
     * @return
     */
    public static ResponseData searchInterestData(Object msg) {
        return CommonService.simpleImplOpt(false, (responseData, sqlSession) -> {
            Map<String, Object> map = FormData.getParam(msg);
            List<DynamicInfo> list = sqlSession.selectList(Mapper.SEARCH_INTEREST_DATA, map);
            int newsNum = sqlSession.selectOne(Mapper.SEARCH_INTEREST_DATA_NUM, map);
            Map<String, Object> data = new HashMap<String, Object>(2);
            data.put(Common.TOTAL_NUM, newsNum);
            data.put(Common.INTEREST_LIST_DATA, list);
            Assemble.responseSuccessSetting(responseData, data);
            //Assemble.responseSuccessSetting(responseData, list);
        });
    }

    /**
     * 保存新闻的HTML数据
     *
     * @param msg
     * @return
     */
    public static ResponseData saveInterestData(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        int num = 0;
        try {
            //前端新闻数据获取
            DynamicInfo dynamicInfo = (DynamicInfo) FormData.getParam(msg, DynamicInfo.class);
            //根据操作类型动态进行新闻插入或更新数据库等操作
            if (dynamicInfo.getOptType() == Common.DYNAMIC_OPT_INSERT) {
                //设置时间戳、创建时间和更新时间等
                dynamicInfo.setCreate_time(CommonService.getDateTime());
                dynamicInfo.setUpdate_time(CommonService.getDateTime());
                num = sqlSession.insert(Mapper.INSERT_NEW_ARTICLE, dynamicInfo);

            } else if (dynamicInfo.getOptType() == Common.DYNAMIC_OPT_UPDATE) {
                //更新更新时间
                dynamicInfo.setUpdate_time(CommonService.getDateTime());
                num = sqlSession.update(Mapper.UPDATE_NEWS, dynamicInfo);
            }

            //根据更新的数据number动态进行不同返回
            if (num > 0) {
                //如果有上传封面图片文件则进行封面文件保存
                if (CommonService.checkNotNull(dynamicInfo.getCoverImage())) {
                    saveCoverImgFile(dynamicInfo);
                }

                //保存新闻得到文件操作
                saveHtmlFile(dynamicInfo);
                Assemble.responseSuccessSetting(responseData, null);

            } else {
                message = "database influence record error";
                interestOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "saveNewsData system error";
            interestOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 保存HTML文件操作
     *
     * @param dynamicInfo 趣文数据
     * @throws Exception 保存出错抛出异常信息
     */
    private static void saveHtmlFile(DynamicInfo dynamicInfo) throws Exception {
        FileOutputStream out = null;
        try {
            //获取保存到系统的路径
            String dynamicInfoHtmlPath = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_HTML);
            //拼接最终保存的文件名
            String systemFileName = dynamicInfoHtmlPath + dynamicInfo.getTimestamp() + Common.SUFFIX_INDEX_HTML;
            interestOpt.logger.debug("final html file name: " + systemFileName);
            File file = new File(systemFileName);
            //如果文件不存在则创建文件
            if (!file.exists()) file.createNewFile();
            //输出内容到文件
            out = new FileOutputStream(file, false); //不append，直接覆写
            out.write(dynamicInfo.getHtml().getBytes("utf-8"));
            out.flush();

        } catch (Exception e) {
            String message = "saveHtmlFile system error";
            interestOpt.logger.error(message, e);
            throw new Exception(e);

        } finally {
            out.close();
        }
    }

    /**
     * 保存文章消息上传图片信息
     *
     * @param dynamicInfo 动态数据
     * @throws Exception 保存出错抛出异常信息
     */
    public static void saveCoverImgFile(DynamicInfo dynamicInfo) throws Exception {
        FileOutputStream out = null;
        try {
            //获取保存到系统的路径
            String dynamicInfoCoverImg = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_COVERIMG);
            //拼接最终保存的文件名
            String coverImageName = dynamicInfoCoverImg + dynamicInfo.getTimestamp() + Common.SUFFIX_PNG;
            File file = new File(coverImageName);
            //如果文件不存在则创建文件
            if (!file.exists()) file.createNewFile();
            //输出内容到文件
            out = new FileOutputStream(file, false); //不append，直接覆写
            out.write(dynamicInfo.getCoverImage().get());
            out.flush();

        } catch (Exception e) {
            String message = "saveCoverImgFile system error";
            interestOpt.logger.error(message, e);
            throw new Exception(e);

        } finally {
            out.close();
        }
    }

    /**
     * 上传资源文件信息
     *
     * @param msg
     */
    public static ResponseData uploadResource(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        FileOutputStream out = null;
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            FileUpload fileUpload = (FileUpload) map.get(Common.NEW_RESOURCE);
            String timestamp = (String) map.get(Common.TIMESTAMP);

            //获取保存到系统的路径
            String resourceFilePath = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_RESOURCE);
            //拼接最终保存的文件名
            String fileName = timestamp + "_" + CommonService.getUnionId() + Common.SUFFIX_PNG;
            String resourceFileAbsPath = resourceFilePath + fileName;
            File file = new File(resourceFileAbsPath);
            //如果文件不存在则创建文件
            if (!file.exists()) file.createNewFile();
            //输出内容到文件
            out = new FileOutputStream(file, false); //不append，直接覆写
            out.write(fileUpload.get());
            out.flush();

            Assemble.responseSuccessSetting(responseData, fileName);

        } catch (Exception e) {
            message = "uploadResource error";
            interestOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return responseData;
    }

    /**
     * 获取用户-新闻表数据
     *
     * @param msg
     * @return
     */
    public static ResponseData getUserDynamicInfo(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            //Map<String, Object> map = FormData.getParam(msg);
            UserDynamic UserDynamic_id = (UserDynamic) FormData.getParam(msg, UserDynamic.class);
            //获取user_dynamic表对应该用户的数据
            UserDynamic userDynamic = sqlSession.selectOne(Mapper.GET_USER_DYNAMIC_INFO, UserDynamic_id);
            //获取该条dynamicinfo最新数据
            DynamicInfo dynamicInfo = sqlSession.selectOne(Mapper.GET_SINGLE_NEWS_DETAIL_INFO, UserDynamic_id.getDynamic_timestamp());
            //该用户第一次查看该新闻则插入user_dynamic表一条记录信息
            if (!CommonService.checkNotNull(userDynamic)) {
                //更新dynamicinfo表阅读次数
                sqlSession.update(Mapper.UPDATE_DYNAMIC_VIEW_COUNT, UserDynamic_id.getDynamic_timestamp());

                //设置dynamicInfo数量+1
                dynamicInfo.setView_count(dynamicInfo.getView_count() + 1);

                //插入新的记录到user_dynamic
                UserDynamic userDynamicNew = new UserDynamic();
                //userDynamicNew.setWx_user_id(String.valueOf(map.get(Common.WX_USER_ID)));
                userDynamicNew.setWx_user_id(UserDynamic_id.getWx_user_id());
                userDynamicNew.setDynamic_id(dynamicInfo.getId());
                userDynamicNew.setDynamic_timestamp(dynamicInfo.getTimestamp());
                userDynamicNew.setDynamic_view(1);
                userDynamicNew.setDynamic_pitch(0);//默认打开时先不点赞
                userDynamicNew.setTimestamp(CommonService.getTimeStamp());
                userDynamicNew.setCreate_time(CommonService.getDateTime());
                sqlSession.insert(Mapper.INSERT_NEW_USER_DYNAMIC_INFO, userDynamicNew);
            }
            //成功获取数据返回
            Map<String, Object> data = new HashMap<>();
            data.put(Common.DYNAMICINFO, dynamicInfo); //添加新闻具体数据
            data.put(Common.USER_DYNAMIC, userDynamic); //添加该用户对该新闻的行为数据
            Assemble.responseSuccessSetting(responseData, data);

        } catch (Exception e) {
            message = "getUserDynamic system error";
            interestOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 手机端前端搜索指定匹配字段的新闻数据
     *
     * @param msg
     * @return
     */
    public static ResponseData searchNews(Object msg) {
        return CommonService.simpleImplOpt(false, (responseData, sqlSession) -> {
            DynamicInfo dynamicInfo = (DynamicInfo) FormData.getParam(msg, DynamicInfo.class);
            List<DynamicInfo> list = sqlSession.selectList(Mapper.SEARCH_NEWS, dynamicInfo);
            Assemble.responseSuccessSetting(responseData, list);
        });
    }

    /**
     * 更新新闻的点赞数量
     *
     * @param msg
     * @return
     */
    public static ResponseData updatePitchCount(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";
        try {
            Map<String, Object> map = FormData.getParam(msg);
            //更新user_dynamic表的dynamic_pitch
            //插入新的记录到user_dynamic
            UserDynamic userDynamicUpdate = new UserDynamic();
            userDynamicUpdate.setWx_user_id(String.valueOf(map.get(Common.WX_USER_ID)));
            userDynamicUpdate.setDynamic_timestamp(String.valueOf(map.get(Common.DYNAMIC_TIMESTAMP)));
            userDynamicUpdate.setDynamic_pitch(Integer.parseInt(String.valueOf(map.get(Common.DYNAMIC_PITCH))));
            sqlSession.update(Mapper.UPDATE_USER_DYNAMIC_PITCH_COUNT, userDynamicUpdate);
            //更新dynamicinfo表的pitch_count
            sqlSession.update(Mapper.UPDATE_DYNAMIC_PITCH_COUNT, map);
            //返回dynamicinfo表最新数据
            DynamicInfo dynamicInfo = sqlSession.selectOne(Mapper.GET_SINGLE_NEWS_DETAIL_INFO, map);
            //判空处理
            if (CommonService.checkNotNull(dynamicInfo)) {
                Assemble.responseSuccessSetting(responseData, dynamicInfo.getPitch_count());

            } else {
                message = "user dynamic update num 0";
                interestOpt.logger.warn(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }

        } catch (Exception e) {
            message = "updatePitchCount system error";
            interestOpt.logger.error(message, e);
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
    public static ResponseData setDynamicStickInfo(Object msg) {
        return CommonService.simpleImplOpt(true, (responseData, sqlSession) -> {

            //获取传递过来的数据
            String message = "";
            Map<String, Object> map = FormData.getParam(msg);

            //设置置顶标记及置顶时间戳
            int num = sqlSession.update(Mapper.SET_DYNAMIC_STICK_INFO, map);

            //返回数据
            if (num > 0) {
                //数据库更新成功
                Assemble.responseSuccessSetting(responseData, null);

            } else {
                //数据库更新失败
                message = "db error";
                interestOpt.logger.error(message);
                Assemble.responseErrorSetting(responseData, 401, message);
            }
        });
    }

    /**
     * 同时删除多条新闻
     *
     * @param msg
     */
    public static ResponseData deleteInterest(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            Map<String, Object> map = FormData.getParam(msg);
            map.put("id",Integer.parseInt(map.get("id").toString()));
            //删除数据和文件资源等操作
            deleteInterestOpt(sqlSession, map);
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteNews error";
            interestOpt.logger.error(message, e);
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
    public static ResponseData deleteBatchInterest(Object msg) {
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
                deleteInterestOpt(sqlSession, map);
            }
            //返回正确数据
            Assemble.responseSuccessSetting(responseData, null);

        } catch (Exception e) {
            message = "deleteBatchNews error";
            interestOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
    }

    /**
     * 删除趣文数据操作
     */
    public static void deleteInterestOpt(SqlSession sqlSession, Map<String, Object> map) throws Exception {
        //删除数据库数据
        int num = sqlSession.delete(Mapper.DELETE_NEWS, map);
        if (num > 0) {
            //删除新闻封面和内容数据
            String dynamicInfoCoverImg = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_COVERIMG);
            String dynamicInfoHtmlPath = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_HTML);

            //删除新闻封面文件数据
            String coverImg = dynamicInfoCoverImg + map.get(Common.TIMESTAMP) + Common.SUFFIX_PNG;
            deleteFile(coverImg);

            //删除新闻内容文件数据
            String htmlFile = dynamicInfoHtmlPath + map.get(Common.TIMESTAMP) + Common.SUFFIX_INDEX_HTML;
            deleteFile(htmlFile);

        } else {
            String message = "delete file from database error";
            interestOpt.logger.warn(message);
            throw new Exception(message);
        }
    }

    /**
     * 拷贝趣文功能
     *
     * @param msg
     */
    public static ResponseData copyInterest(Object msg) {
        ResponseData responseData = new ResponseData(StatusCode.ERROR.getValue());
        SqlSession sqlSession = MybatisUtils.getSession();
        String message = "";

        try {
            //获取传递过来的数据
            DynamicInfo dynamicInfo = (DynamicInfo) FormData.getParam(msg, DynamicInfo.class);
            String sourceTimeStamp = dynamicInfo.getTimestamp();

            //设置新的timestamp等时间数据
            dynamicInfo.setTimestamp(CommonService.getTimeStamp() + "_" + dynamicInfo.getWx_user_id());
            dynamicInfo.setCreate_time(CommonService.getDateTime());
            dynamicInfo.setUpdate_time(CommonService.getDateTime());
            dynamicInfo.setStatus_cd(Common.DRAFT_STATUS);

            //插入新数据到数据库
            int num = sqlSession.insert(Mapper.INSERT_NEW_ARTICLE, dynamicInfo);
            if (num > 0) {
                //拷贝新闻封面和内容数据
                String dynamicInfoCoverImg = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_COVERIMG);
                String dynamicInfoHtmlPath = GlobalConfig.getProperties(Common.DYNAMICINFOS_SYS_PATH_HTML);

                //拷贝新闻封面数据
                String sourceCoverImg = dynamicInfoCoverImg + sourceTimeStamp + Common.SUFFIX_PNG;
                String destCoverImg = dynamicInfoCoverImg + dynamicInfo.getTimestamp() + Common.SUFFIX_PNG;
                CommonService.copyFiles(sourceCoverImg, destCoverImg);

                //拷贝新闻内容数据
                String sourceNewsFile = dynamicInfoHtmlPath + sourceTimeStamp + Common.SUFFIX_INDEX_HTML;
                String destNewsFile = dynamicInfoHtmlPath + dynamicInfo.getTimestamp() + Common.SUFFIX_INDEX_HTML;
                CommonService.copyFiles(sourceNewsFile, destNewsFile);

                //返回正确数据
                Assemble.responseSuccessSetting(responseData, null);
            }

        } catch (Exception e) {
            message = "copyNews error";
            interestOpt.logger.error(message, e);
            Assemble.responseErrorSetting(responseData, 500, message);

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, true);
        }
        return responseData;
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
