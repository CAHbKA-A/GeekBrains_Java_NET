import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        new Client().start();
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder(), new StringDecoder(), new ClientDecoder());
                        }
                    });

            ChannelFuture channelFuture = client.connect("localhost", 9000).sync();

            System.out.println("Client started");
            while (true) {
                String msg = String.format("Hello from client %s: %s", Thread.currentThread().getName(), LocalDateTime.now());
                channelFuture.channel().writeAndFlush(msg).sync();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
