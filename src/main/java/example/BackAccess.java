package example;

import com.alibaba.fastjson.JSON;
import example.operation.entity.response.ResponseData;
import example.operation.impl.interestArticle.interestOpt;
import example.operation.impl.lawfirmDynamic.dynamicOpt;
import example.operation.impl.login.LoginOpt;
import example.operation.impl.mass.massOpt;
import example.operation.impl.msg.msgOpt;
import example.operation.impl.studyArticle.studyOpt;
import example.operation.impl.team.teamOpt;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import example.tool.common.Common;
import example.tool.parser.form.FormData;
import java.util.Map;
import static com.aliyun.oss.internal.OSSHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Administrator on 2017/2/8.
 */

public class BackAccess {

    private static Logger logger = LoggerFactory.getLogger(BackAccess.class);

    /**
     * 暴露第三方回调的url链接无需cross域验证
     * @param request request请求对象
     * @param msg request请求护具
     * @param ctx 返回通道
     * @return
     */
    public static boolean nonCrossVerify(HttpRequest request, Object msg, ChannelHandlerContext ctx) {
        //获取uri数据
        String uri = request.uri();

        //记录并返回消息体是否被消费了
        boolean messagePurchase = true;

        /* **************************************************************/
        if (uri.equals("/netty1")) {
            Map<String, Object> map = FormData.getParam(msg);
            BackAccess.logger.debug("come to netty test link");
            httpResponse(ctx, msg, JSON.toJSONString("hello world 1"));

        }
        //若尚未消费该事件，则返回false
        else {
            messagePurchase = false;
        }
        return messagePurchase;
    }


    /**
     * 无需获取登录状态才能访问的链接请求
     *
     * @param request request请求对象
     * @param msg request请求护具
     * @param ctx 返回通道
     */
    public static boolean nonLoginAccess(HttpRequest request, Object msg, ChannelHandlerContext ctx) {
        //获取uri数据
        String uri = request.uri();

        //记录并返回消息体是否被消费了
        boolean messagePurchase = true;

        /* **************************************************************/
        if (uri.equals("/netty2")) {
            Map<String, Object> map = FormData.getParam(msg);
            BackAccess.logger.debug("come to netty test link");
            httpResponse(ctx, msg, JSON.toJSONString("hello world 2"));

        }
        //管理员登录操作
        else if (uri.equals("/managerLogin")) {
            ResponseData responseData = LoginOpt.managerLogin(msg);
            httpResponse(ctx, msg, responseData);
        }
        //根据用户id和session获取用户信息数据
        else if (uri.equals("/getUserInfoByIdAndSessionId")) {
            ResponseData responseData = LoginOpt.getUserInfoByIdAndSessionId(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的趣文数据
        else if (uri.equals("/getRangeInterestListToBg")) {
            ResponseData responseData = interestOpt.getRangeInterestListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量删除趣文数据
        else if (uri.equals("/deleteBatchInterest")) {
            ResponseData responseData = interestOpt.deleteBatchInterest(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除新闻数据
        else if (uri.equals("/deleteInterest")) {
            ResponseData responseData = interestOpt.deleteInterest(msg);
            httpResponse(ctx, msg, responseData);
        }
        //拷贝趣文数据
        else if (uri.equals("/copyInterest")) {
            ResponseData responseData = interestOpt.copyInterest(msg);
            httpResponse(ctx, msg, responseData);
        }
        //管理员在PC客户端审核时搜索相关标题的数据
        else if (uri.equals("/searchInterestData")) {
            ResponseData responseData = interestOpt.searchInterestData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //保存趣文数据
        else if (uri.equals("/saveInterestData")) {
            ResponseData responseData = interestOpt.saveInterestData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的研究所数据
        else if (uri.equals("/getRangeStudyListToBg")) {
            ResponseData responseData = studyOpt.getRangeStudyListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量删除研究所数据
        else if (uri.equals("/deleteBatchStudy")) {
            ResponseData responseData = studyOpt.deleteBatchStudy(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除新闻数据
        else if (uri.equals("/deleteStudy")) {
            ResponseData responseData = studyOpt.deleteStudy(msg);
            httpResponse(ctx, msg, responseData);
        }
        //拷贝研究所数据
        else if (uri.equals("/copyStudy")) {
            ResponseData responseData = studyOpt.copyStudy(msg);
            httpResponse(ctx, msg, responseData);
        }
        //管理员在PC客户端审核时搜索相关标题的数据
        else if (uri.equals("/searchStudyData")) {
            ResponseData responseData = studyOpt.searchStudyData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //保存研究所数据
        else if (uri.equals("/saveStudyData")) {
            ResponseData responseData = studyOpt.saveStudyData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的律所资讯数据
        else if (uri.equals("/getRangeDynamicListToBg")) {
            ResponseData responseData = dynamicOpt.getRangeDynamicListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量删除律所资讯数据
        else if (uri.equals("/deleteBatchDynamic")) {
            ResponseData responseData = dynamicOpt.deleteBatchDynamic(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除律所资讯数据
        else if (uri.equals("/deleteDynamic")) {
            ResponseData responseData = dynamicOpt.deleteDynamic(msg);
            httpResponse(ctx, msg, responseData);
        }
        //拷贝律所资讯数据
        else if (uri.equals("/copyDynamic")) {
            ResponseData responseData = dynamicOpt.copyDynamic(msg);
            httpResponse(ctx, msg, responseData);
        }
        //管理员在PC客户端审核时搜索相关标题的数据
        else if (uri.equals("/searchDynamicData")) {
            ResponseData responseData = dynamicOpt.searchDynamicData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //保存研究所数据
        else if (uri.equals("/saveDynamicData")) {
            ResponseData responseData = dynamicOpt.saveDynamicData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取该用户对动态新闻的用户操作数据情况
        else if (uri.equals("/getUserDynamicInfo")) {
            ResponseData responseData = interestOpt.getUserDynamicInfo(msg);
            httpResponse(ctx, msg, responseData);
        }
        //模糊搜索指定title和时间字段的数据
        else if (uri.equals("/searchNews")) {
            ResponseData responseData = interestOpt.searchNews(msg);
            httpResponse(ctx, msg, responseData);
        }
        //更新用户对动态新闻的点赞情况
        else if (uri.equals("/updatePitchCount")) {
            ResponseData responseData = interestOpt.updatePitchCount(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取文章信息到手机端
        else if (uri.equals("/getNewsListToPh")) {
            ResponseData responseData = interestOpt.getNewsListToPh(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的团队成员数据
        else if (uri.equals("/getRangeTeamListToBg")) {
            ResponseData responseData = teamOpt.getRangeTeamListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量删除团队成员数据
        else if (uri.equals("/deleteBatchTeam")) {
            ResponseData responseData = teamOpt.deleteBatchTeam(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除团队成员数据
        else if (uri.equals("/deleteTeam")) {
            ResponseData responseData = teamOpt.deleteTeam(msg);
            httpResponse(ctx, msg, responseData);
        }
        //管理员在PC客户端审核时搜索相关人名的数据
        else if (uri.equals("/searchTeamData")) {
            ResponseData responseData = teamOpt.searchTeamData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //保存团队成员数据
        else if (uri.equals("/saveTeamData")) {
            ResponseData responseData = teamOpt.saveTeamData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //团队成员置顶功能设置
        else if (uri.equals("/setTeamStickInfo")) {
            ResponseData responseData = teamOpt.setTeamStickInfo(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取团队成员信息到移动端
        else if (uri.equals("/getTeamListToPh")) {
            ResponseData responseData = teamOpt.getTeamListToPh(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的留言数据
        else if (uri.equals("/getRangeMsgListToBg")) {
            ResponseData responseData = msgOpt.getRangeMsgListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量删除留言数据
        else if (uri.equals("/deleteBatchMsg")) {
            ResponseData responseData = msgOpt.deleteBatchMsg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除留言数据
        else if (uri.equals("/deleteMsg")) {
            ResponseData responseData = msgOpt.deleteMsg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //批量置办留言数据
        else if (uri.equals("/setupBatchMsg")) {
            ResponseData responseData = msgOpt.setupBatchMsg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //置办留言数据
        else if (uri.equals("/setupMsg")) {
            ResponseData responseData = msgOpt.setupMsg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //管理员在PC客户端审核时搜索相关业务的数据
        else if (uri.equals("/searchMsgData")) {
            ResponseData responseData = msgOpt.searchMsgData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //置顶功能设置
        else if (uri.equals("/setMsgStickInfo")) {
            ResponseData responseData = msgOpt.setMsgStickInfo(msg);
            httpResponse(ctx, msg, responseData);
        }
        //保存留言功能
        else if (uri.equals("/saveMsgInfo")) {
            ResponseData responseData = msgOpt.saveMsgInfo(msg);
            httpResponse(ctx, msg, responseData);
        }
        //上传资源信息
        else if (uri.equals("/uploadResource")) {
            ResponseData responseData = interestOpt.uploadResource(msg);
            httpResponse(ctx, msg, responseData);
        }
        //置顶功能设置
        else if (uri.equals("/setDynamicStickInfo")) {
            ResponseData responseData = interestOpt.setDynamicStickInfo(msg);
            httpResponse(ctx, msg, responseData);
        }
        //添加微信缩略的功能
        else if (uri.equals("/addMassListToSend")) {
            ResponseData responseData = interestOpt.addMassListToSend(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围的消息任务列表
        else if (uri.equals("/getRangeMassToBg")) {
            ResponseData responseData = massOpt.getRangeMassToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //更新指定群发任务列表
        else if (uri.equals("/updateMassResult")) {
            ResponseData responseData = massOpt.updateMassResult(msg);
            httpResponse(ctx, msg, responseData);
        }
        //删除群发任务
        else if (uri.equals("/deleteMass")) {
            ResponseData responseData = massOpt.deleteMass(msg);
            httpResponse(ctx, msg, responseData);
        }
        //获取指定范围内的待群发的数据
        else if (uri.equals("/getRangeMassListToBg")) {
            ResponseData responseData = interestOpt.getRangeMassListToBg(msg);
            httpResponse(ctx, msg, responseData);
        }
        //搜索待群发的数据
        else if (uri.equals("/searchMassListData")) {
            ResponseData responseData = interestOpt.searchMassListData(msg);
            httpResponse(ctx, msg, responseData);
        }
        //更新上传图文结果
        else if (uri.equals("/updateMassUpload")) {
            ResponseData responseData = massOpt.updateMassUpload(msg);
            httpResponse(ctx, msg, responseData);
        }
        //若尚未消费该事件，则返回false
        else {
            messagePurchase = false;
        }
        return messagePurchase;
    }


    /**
     * 需要用户已登录状态才可访问的请求
     *
     * @param request uri请求地址
     * @param msg request请求护具
     * @param ctx 返回通道
     */
    public static void loginAccess(HttpRequest request, Object msg, ChannelHandlerContext ctx) {
        //获取uri数据
        String uri = request.uri();

        /* **************************************************************/
        /*测试专区*/
        if (uri.equals("/netty3")) {
            Map<String, Object> map = FormData.getParam(msg);
            BackAccess.logger.debug("come to netty test link");
            httpResponse(ctx, msg, JSON.toJSONString("hello world 3"));

        } else {
            String message = "server do not serve such request: " + uri;
            httpResponse(ctx, msg, message);
            BackAccess.logger.debug(message);
        }
    }


    /**
     * 返回http请求相关消息
     *
     * @param ctx 通信通道
     * @param msg 请求的引用
     */
    public static void httpResponse(ChannelHandlerContext ctx, Object msg, Object dataBack) {

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(JSON.toJSONString(dataBack).getBytes()));
        response.headers().set(CONTENT_TYPE, Common.RETURN_JSON);
        commonResponse(ctx, msg, response);
        BackAccess.logger.debug("Return Response Data: \n" + dataBack.toString());
    }


    /**
     * 返回http请求相关消息
     *
     * @param ctx 通信通道
     * @param msg 请求的引用
     */
    public static void httpResponsePureHtml(ChannelHandlerContext ctx, Object msg, String htmlData) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(htmlData.getBytes()));
        response.headers().set(CONTENT_TYPE, Common.RETURN_TEXT_HTML);
        commonResponse(ctx, msg, response);
        BackAccess.logger.debug("Return html pure data response");
    }


    /**
     * 返回json数据和HTML数据相同的消息体
     *
     * @param ctx      通信通道
     * @param msg      请求数据
     * @param response 请求返回消息封装体
     */
    private static void commonResponse(ChannelHandlerContext ctx, Object msg, FullHttpResponse response) {
        if (HttpUtil.is100ContinueExpected((HttpMessage) msg)) {
            ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }
        boolean keepAlive = HttpUtil.isKeepAlive((HttpMessage) msg);
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "POST");
        response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "user_id, session_id, *");
        response.headers().set(ACCEPT, "*");
        if (!keepAlive) {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.writeAndFlush(response);
        }
    }

}