package edu.mit.moira;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class MoiraNettyPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();

        // Add the codecs first,
        pipeline.addLast("decoder", new GDBDecoder());
        pipeline.addLast("encoder", new GDBEncoder());

        // and then business logic.
        pipeline.addLast("handler", new MoiraNettyHandler());

        return pipeline;
	}

}
