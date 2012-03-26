package edu.mit.moira;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import edu.mit.moira.internal.Constants;

/**
 * Handler for the connection. Needs to store state, because the initial
 * challenge/response uses a different RPC format than the later Moira calls do.
 * 
 * @author Jacob
 * 
 */
public class MoiraNettyHandler extends SimpleChannelUpstreamHandler {
    private static final Logger logger = Logger.getLogger(
            MoiraNettyHandler.class.getName());
    
    /**
     * Has the mrgdb challenge/response initialization been completed.
     */
    private boolean isInitialized = false;
    
    /**
     * Has kerberos authentication been performed in this channel.
     */
    private boolean isAuthenticated = false;

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        logger.info("Connected!  Sending MRGDB challenge...");
        ChannelBuffer buf = ChannelBuffers.wrappedBuffer(MoiraConnection.chal);
        e.getChannel().write(buf);
    }



	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws MoiraException {
		if (!isInitialized) {
			ChannelBuffer body = (ChannelBuffer) e.getMessage();
			ChannelBuffer response = ChannelBuffers.wrappedBuffer(MoiraConnection.resp);
			if (body.compareTo(response) == 0) {
				logger.info("Initialized");
				isInitialized = true;
				ChannelPipeline pipeline = ctx.getPipeline();
				pipeline.addFirst("rpcencoder", new MoiraRPCEncoder());
				pipeline.addFirst("rpcdecoder", new MoiraRPCDecoder());
				for (String s : pipeline.getNames()) {
					if (pipeline.get(s) instanceof MoiraGDBDecoder) {
						pipeline.remove(s);
					}
				}
				MoiraParams mp = new MoiraParams(Constants.MR_QUERY, "gprn", "sipb");
				e.getChannel().write(mp);
			} else {
				throw new MoiraException("Challenge/response failure during initial connection.");
			}
		} else { // (isInitialized == true)
			logger.info("Moira response");
			System.out.println(e.getMessage());
		}
	}



	@Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
//            logger.info(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }

}
