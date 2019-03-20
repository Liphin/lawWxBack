package example.operation.impl.common;

import example.operation.entity.response.ResponseData;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Administrator on 2018/9/30.
 * 辅助实现类，用于处理回调方法逻辑
 */
public interface CommonImpl {
    //开放run接口，用于具体回调逻辑的实现
    void run(ResponseData responseData, SqlSession sqlSession) throws Exception;
}
