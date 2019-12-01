package com.rpc.service.impl;

import java.awt.Point;

import org.springframework.stereotype.Service;

import com.rpc.service.DemoService;

@Service("domeService")
public class DemoServiceImpl implements DemoService{

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "Hello " +name;
	}

	@Override
	public Point multiPoint(Point p, int multi) {
		// TODO Auto-generated method stub
		p.x = p.x * multi;
		p.y = p.y * multi;
		return p;
	}

}
