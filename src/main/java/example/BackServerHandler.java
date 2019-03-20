package example;

/**
 * Created by Administrator on 2017/2/8.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import example.operation.entity.response.StatusCode;
import example.operation.entity.response.ResponseData;
import example.operation.impl.common.CommonService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;


public class BackServerHandler extends SimpleChannelInboundHandler<Object> {
    private static Logger logger = LoggerFactory.getLogger(BackServerHandler.class);

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null) {
            if (msg instanceof HttpRequest) {
                HttpRequest request = (HttpRequest) msg;
                CommonService.printHttpInvokeFunction(request.uri());

                //OPTIONS类型的方法，直接返回
                if (request.method() == HttpMethod.OPTIONS) {
                    BackServerHandler.logger.debug("Come To OPTIONS Method");
                    BackAccess.httpResponse(ctx, msg, new ResponseData(StatusCode.OK.getValue()));
                    return;
                }

                //暴露第三方回调的url链接无需cross域验证
                if(!BackAccess.nonCrossVerify(request, msg, ctx)){

                    //无需获取登录状态才能访问的链接请求
                    if (!BackAccess.nonLoginAccess(request, msg, ctx)) {

                        //登录状态才能访问的链接请求
                        BackAccess.loginAccess(request, msg, ctx);

                    }
                }
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


//    private void reset() {
//        // destroy the decoder to release all resources
//        request = null;
//        decoder.destroy();
//        decoder = null;
//    }

}
