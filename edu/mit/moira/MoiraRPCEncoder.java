package edu.mit.moira;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import edu.mit.moira.internal.Constants;

/**
 * 
 * Netty will need to have a GDB encoder/decoder.
 * 
 * The Moira RPC format is:
 * 
 * 4-byte total length (including these 4 bytes)
 * 4-byte version number (MR_VERSION_2 == 2)
 * 4-byte opcode (from client) or status (from server)
 * 4-byte argc
 * 
 * 4-byte len, followed by null-terminated string, padded to 4-byte boundary
 *   (the len doesn't include the padding)
 * ...
 *
 * (followed by more packets if status was MR_MORE_DATA)
 *
 * All numbers are in network byte order.
 * 
 * 
 * @author jmorzins
 * 
 */
@Sharable
public class MoiraRPCEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {

		if (!(msg instanceof MoiraParams)) {
			return msg;
		}

		MoiraParams mp = (MoiraParams) msg;
		
		ChannelBufferFactory bf = channel.getConfig().getBufferFactory();
		
		ChannelBuffer body = ChannelBuffers.dynamicBuffer(bf);
		
		body.writeInt(Constants.MR_VERSION_2);
		body.writeInt(mp.opcode);
		body.writeInt(mp.args.length);
		// Now loop over the args
		for (byte[] ba : mp.args) {
			int argLen = ba.length;
			body.writeInt(argLen);
			body.writeBytes(ba);
			int remainder = argLen % 4;         // 0,1,2,3
			int padding = (4 - remainder) % 4;  // 0,3,2,1
			body.writeZero(padding);
		}
		
		int totalLength = body.readableBytes() + 4;  // length includes the header
		ChannelBuffer header = bf.getBuffer(body.order(), 4);
		header.writeInt(totalLength);

		return ChannelBuffers.wrappedBuffer(header, body);
	}
	
}
