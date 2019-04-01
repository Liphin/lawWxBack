package example.tool.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/2/12.
 */
public class Common {

    private static Logger logger = LoggerFactory.getLogger(Common.class.getName());

    //global环境设置配置
    public static String DEV_ENVIRONMENT = "dev"; //测试环境
    public static String PROD_ENVIRONMENT = "prod"; //生产环境
    public static String UTF8 = "UTF-8";
    public static String SETTING_FILES = "com.viewcoder.setting.files"; //设定目标环境下的文件

    //数据返回设置
    public static String RETURN_TEXT_HTML = "text/html;charset=UTF-8";
    public static String RETURN_JSON = "application/json";

    //上传参数解析
    public static String WX_USER_ID = "wx_user_id";
    public static String TOTAL_NUM = "totalNum";
    public static String INTEREST_LIST_DATA = "interestListData";
    public static String STUDY_LIST_DATA = "studyListData";
    public static String DYNAMIC_LIST_DATA = "dynamicListData";
    public static String TEAM_LIST_DATA = "teamListData";
    public static String MSG_LIST_DATA = "msgListData";
    public static int DYNAMIC_OPT_INSERT = 1;//新增记录
    public static int DYNAMIC_OPT_UPDATE = 2;//更新记录
    public static String DELETE_LIST = "deleteList";
    public static String SETUP_LIST = "setupList";
    public static String TIMESTAMP = "timestamp";
    public static String STATUS_CD = "status_cd";
    public static String SEARCH = "search";
    public static String CREATE_TIME = "create_time";
    public static String UPDATE_TIME = "update_time";
    public static String NEW_RESOURCE = "new_resource";
    public static String DYNAMICINFO = "dynamicinfo";
    public static String USER_DYNAMIC = "user_dynamic";
    public static String DYNAMIC_TIMESTAMP = "dynamic_timestamp";
    public static String DYNAMIC_PITCH = "dynamic_pitch";

    //config文件的配置参数
    public static String DYNAMICINFOS_SYS_PATH_HTML = "dynamicinfos.system.path.html";
    public static String DYNAMICINFOS_SYS_PATH_COVERIMG = "dynamicinfos.system.path.coverimg";
    public static String DYNAMICINFOS_SYS_PATH_RESOURCE = "dynamicinfos.system.path.resource";

    //业务需求限制
    public static int DRAFT_STATUS = 0;//文章草稿状态

    //后缀名参数
    public static String SUFFIX_INDEX_HTML = "-index.html";
    public static String SUFFIX_PNG = ".png";
    public static String SUFFIX_GIF = ".gif";

}
