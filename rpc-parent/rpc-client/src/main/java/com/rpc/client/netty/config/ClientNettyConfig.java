package com.rpc.client.netty.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import com.rpc.client.netty.initializer.ProtocolInitalizer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@Configuration
public class ClientNettyConfig {

	
	@Autowired
	@Qualifier("protocolInitalizer")
	private ProtocolInitalizer protocolInitalizer;
	// bootstrap配置
	@SuppressWarnings("unchecked")
	@Bean(name = "bootstrap")
	public Bootstrap bootstrap() {
		Bootstrap b = new Bootstrap();
		b.group(bossGroup()).channel(NioSocketChannel.class).handler(protocolInitalizer);
		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (@SuppressWarnings("rawtypes")
		ChannelOption option : keySet) {
			b.option(option, tcpChannelOptions.get(option));
		}
		return b;
	}

	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup();
	}

	@Bean(name = "tcpChannelOptions")
	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.TCP_NODELAY, true);
		return options;
	}

	@Bean(name = "stringEncoder")
	public StringEncoder stringEncoder() {
		return new StringEncoder();
	}

	@Bean(name = "stringDecoder")
	public StringDecoder stringDecoder() {
		return new StringDecoder();
	}

	/**
	 * Necessary to make the Value annotations work.
	 *
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
