package edu.mit.moira;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class MoiraNettyPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();

        pipeline.addLast("gdbdecoder", new MoiraGDBDecoder());
        
        // These codecs are not used until after MRGDB initialization
        // They are added in the handler. 
//        pipeline.addLast("decoder", new MoiraRPCDecoder());
//        pipeline.addLast("encoder", new MoiraRPCEncoder());

        // and then business logic.
        pipeline.addLast("handler", new MoiraNettyHandler());

        return pipeline;
	}

}
