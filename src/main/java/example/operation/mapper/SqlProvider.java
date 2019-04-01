package example.operation.mapper;


import example.tool.common.Common;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */
public class SqlProvider {


    /**
     * 根据项目传递过来参数进行更新项目公开程度状态
     *
     * @param map
     * @return
     */
//    public String updateProjectOpenness(Map<String, Object> map) {
//        StringBuilder stringBuilder = new StringBuilder();
//        int is_public = Integer.parseInt((map.get(Common.IS_PUBLIC)).toString());
//
//        //需检查user_id和project_id同时不为空才继续进行update操作
//        if (CommonService.checkNotNull(map.get(Common.USER_ID)) && CommonService.checkNotNull(map.get(Common.PROJECT_ID))) {
//            stringBuilder.append("update project set is_public=" + is_public);
//
//            //如果提交公开待审核状态则进行添加对应的industry类型
//            if (is_public == 1) {
//                stringBuilder.append(" , industry_code='" + map.get(Common.INDUSTRY_CODE) + "'");
//                stringBuilder.append(" , industry_sub_code='" + map.get(Common.INDUSTRY_SUB_CODE) + "'");
//
//            } else if (is_public == 0) {
//                //若设置不公开则设置industry类型等为空
//                stringBuilder.append(" , industry_code=''");
//                stringBuilder.append(" , industry_sub_code=''");
//            }
//            stringBuilder.append(" where id=" + map.get(Common.PROJECT_ID) + " and user_id=" + map.get(Common.USER_ID));
//        }
//        return stringBuilder.toString();
//    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据
     *
     * @param map
     * @return
     */
    public String searchInterestData(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        String createTime = String.valueOf(map.get(Common.CREATE_TIME));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select * from dynamicinfo where create_time<('"+createTime+"') and type=1 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) order by create_time desc limit 120");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据
     *
     * @param map
     * @return
     */
    public String searchStudyData(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        String createTime = String.valueOf(map.get(Common.CREATE_TIME));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select * from dynamicinfo where create_time<('"+createTime+"') and type=2 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) order by create_time desc limit 120");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据
     *
     * @param map
     * @return
     */
    public String searchDynamicData(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        String createTime = String.valueOf(map.get(Common.CREATE_TIME));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select * from dynamicinfo where create_time<('"+createTime+"') and type=3 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) order by create_time desc limit 120");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关人名的数据
     *
     * @param map
     * @return
     */
    public String searchTeamData(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        String createTime = String.valueOf(map.get(Common.CREATE_TIME));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select * from team where create_time<('"+createTime+"') and status_cd in ("+statusCd+") and (name like concat('%','" + search + "','%')) order by create_time desc limit 120");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关业务描述的数据
     *
     * @param map
     * @return
     */
    public String searchMsgData(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        String createTime = String.valueOf(map.get(Common.CREATE_TIME));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select * from msg where create_time<('"+createTime+"') and status_cd in ("+statusCd+") and (content like concat('%','" + search + "','%')) order by create_time desc limit 120");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据的总数目
     *
     * @param map
     * @return
     */
    public String searchInterestDataNum(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select count(*) from dynamicinfo where type=1 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) ");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据的总数目
     *
     * @param map
     * @return
     */
    public String searchStudyDataNum(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select count(*) from dynamicinfo where type=2 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) ");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据的总数目
     *
     * @param map
     * @return
     */
    public String searchDynamicDataNum(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select count(*) from dynamicinfo where type=3 and status_cd in ("+statusCd+") and (title like concat('%','" + search + "','%')) ");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关标题的数据的总数目
     *
     * @param map
     * @return
     */
    public String searchTeamDataNum(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select count(*) from team where status_cd in ("+statusCd+") and (name like concat('%','" + search + "','%')) ");
        return stringBuilder.toString();
    }

    /**
     * 管理员在手机客户端审核时搜索相关业务描述的数据的总数目
     *
     * @param map
     * @return
     */
    public String searchMsgDataNum(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String statusCd = String.valueOf(map.get(Common.STATUS_CD));
        String search = String.valueOf(map.get(Common.SEARCH));
        //根据审核级别动态获取相应数据
        stringBuilder.append("select count(*) from msg where status_cd in ("+statusCd+") and (content like concat('%','" + search + "','%')) ");
        return stringBuilder.toString();
    }

    /**
     * 根据dynamicPitch数值动态更新dynamic的pitch_count数据
     *
     * @param map
     * @return
     */
    public String updateDynamicPitchCount(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String timestamp = String.valueOf(map.get(Common.DYNAMIC_TIMESTAMP));
        Integer dynamicPitch = Integer.parseInt(String.valueOf(map.get(Common.DYNAMIC_PITCH)));
        if (dynamicPitch == 1) {
            stringBuilder.append("update dynamicinfo set pitch_count=pitch_count+1 where timestamp='" + timestamp + "'");
        } else {
            stringBuilder.append("update dynamicinfo set pitch_count=pitch_count-1 where timestamp='" + timestamp + "'");
        }
        return stringBuilder.toString();
    }

}





