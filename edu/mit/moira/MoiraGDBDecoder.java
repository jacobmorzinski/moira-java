package edu.mit.moira;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * The sole job of this FrameDecoder is to read the MRGDB initial response,
 * which will begin with a four-byte unsigned long that announces the length of
 * the remainder of the frame.
 * <p>
 * After reading the frame we reassemble the length header with the body and
 * pass upstream, for the Handler to inspect. In normal usage the handler
 * should remove this decoder from the pipeline after it has handled the
 * MRGDB exchange.
 * 
 * @author Jacob
 * 
 */
public class MoiraGDBDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) {

		// Are the length bytes available?
		if (buffer.readableBytes() < 4) {
			return null;
		}

		// Is the whole buffer available?
		buffer.markReaderIndex();
		int length = buffer.readInt();
		if (buffer.readableBytes() < length) {
			buffer.resetReaderIndex();
			return null;
		}

		// Read the frame, and put the length header back on.
		ChannelBuffer frame = buffer.readBytes(length);
		ChannelBuffer header = channel.getConfig().getBufferFactory()
				.getBuffer(frame.order(), 4);
		header.writeInt(length);
		return ChannelBuffers.wrappedBuffer(header, frame);
	}

}
