package example.tool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import example.operation.impl.common.CommonService;
import example.tool.common.Common;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/22.
 * config配置，即把所有配置读取到properties中
 */
public class GlobalConfig {

    private static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
    private final static String GENERAL_CONFIG = "/config/general.properties";
    private final static String TARGET_ENV = "TARGET_ENV";
    private static Properties props = new Properties();
    private static int cores = Runtime.getRuntime().availableProcessors();
    private static String targetEnvironment;

    //加载properties文件信息
    static {
        InputStream inputStream = null;
        try {
            //从Jenkins中获取设置的目标environment
            targetEnvironment = System.getenv(TARGET_ENV);
            if (!CommonService.checkNotNull(targetEnvironment)) {
                targetEnvironment = Common.DEV_ENVIRONMENT;
            }
            GlobalConfig.logger.debug("Target Environment is " + targetEnvironment);

            /*加载主配置文件，获取全局辅助配置信息*/
            inputStream = GlobalConfig.class.getResourceAsStream(GENERAL_CONFIG);
            props.load(new InputStreamReader(inputStream, Common.UTF8));
            String[] settingFiles = GlobalConfig.getProperties(Common.SETTING_FILES).split(",");

            /*读取具体环境下所有config文件*/
            for (String file : settingFiles) {
                String targetConfigUrl = "/config/" + targetEnvironment + "/" + file + "_" + targetEnvironment + ".properties";
                inputStream = GlobalConfig.class.getResourceAsStream(targetConfigUrl);
                props.load(new InputStreamReader(inputStream, Common.UTF8));
            }

        } catch (Exception e) {
            GlobalConfig.logger.error("===init properties error: ", e);

        } finally {
            /*关闭输入流*/
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                GlobalConfig.logger.error("close inputStream occurs error: ", e);
            }
        }
    }

    /**
     * 读取配置文件String value
     *
     * @param key properties的key
     * @return
     */
    public static String getProperties(String key) {
        return props.getProperty(key);
    }

    /**
     * 读取配置文件int value
     *
     * @param key properties的key
     * @return
     */
    public static Integer getIntProperties(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    /**
     * 读取配置文件boolean value
     *
     * @param key properties的key
     * @return
     */
    public static Boolean getBooleanProperties(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }


    /**
     * 获取所有环境properties的引用
     *
     * @return
     */
    public static Properties getPropertiesRef() {
        return props;
    }

    /**
     * 获取系统的核数
     * @return
     */
    public static int getCores() {
        return cores;
    }

    /**
     * 是否为生产环境的设置
     * @return
     */
    public static boolean isProdEnv(){
        return Objects.equals(targetEnvironment, Common.PROD_ENVIRONMENT);
    }
}
