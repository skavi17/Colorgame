package com.kavi.colorgame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
	public String id = UUID.randomUUID().toString();
    public Colors color;
    public String name;
    public List<Integer> controlledBlocks = new ArrayList<Integer>();

}
