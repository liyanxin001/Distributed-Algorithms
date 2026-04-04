package com.greatfree.ring.lcr.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ring {
	
	public static Map<String, List<String>> constructRing(String rootKey, List<String> nodeKeys) {
	    Map<String, List<String>> ring = new HashMap<String, List<String>>();
	    
	    int totalNodes = nodeKeys.size() + 1; // including root
	    
	    if (totalNodes == 1) {
	        // Just the root node with no connections
	        ring.put(rootKey, new LinkedList<String>());
	        return ring;
	    }
	    
	    // Create a list of all node keys in order (root first, then nodeKeys)
	    List<String> allNodes = new ArrayList<String>();
	    allNodes.add(rootKey);
	    allNodes.addAll(nodeKeys);
	    
	    // For each node, connect it to the next node in the ring
	    for (int i = 0; i < totalNodes; i++) {
	        String currentNode = allNodes.get(i);
	        String nextNode = allNodes.get((i + 1) % totalNodes); // wraps around to root
	        
	        if (!ring.containsKey(currentNode)) {
	            ring.put(currentNode, new LinkedList<String>());
	        }
	        ring.get(currentNode).add(nextNode);
	    }
	    
	    return ring;
	}

}
