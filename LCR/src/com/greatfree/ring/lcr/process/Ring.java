package com.greatfree.ring.lcr.process;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ring {
	
	public static Map<String, String> constructRing(String rootKey, Set<String> nodeKeys) {
	    Map<String, String> ring = new HashMap<String, String>();
	    
	    List<String> allNodes = new ArrayList<String>();
	    allNodes.add(rootKey);
	    allNodes.addAll(nodeKeys);
	    
	    int totalNodes = allNodes.size();
	    
	    if (totalNodes == 0) {
	        return ring;
	    }
	    
	    // Each node points to the next node (last points to first)
	    for (int i = 0; i < totalNodes; i++) {
	        String currentNode = allNodes.get(i);
	        String nextNode = allNodes.get((i + 1) % totalNodes);
	        ring.put(currentNode, nextNode);
	    }
	    
	    return ring;
	}

}
