package com.cmri.bpt.common.alg;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public final class KetamaNodeLocator {

	private TreeMap<Long, NamedNode> ketamaNodes;
	private HashAlgorithm hashAlg;
	private int nReplicas = 200;

	public KetamaNodeLocator(List<NamedNode> nodes, HashAlgorithm alg, int nodeCopies) {
		ketamaNodes = new TreeMap<Long, NamedNode>();

		hashAlg = alg;

		nReplicas = nodeCopies;

		for (NamedNode node : nodes) {
			for (int i = 0; i < nReplicas / 4; i++) {
				byte[] digest = hashAlg.computeMd5(node.getName() + i);
				for (int h = 0; h < 4; h++) {
					Long vk = hashAlg.hash(digest, h);

					ketamaNodes.put(vk, node);
				}
			}
		}
	}

	public NamedNode getPrimaryNode(final String key) {
		byte[] digest = hashAlg.computeMd5(key);
		return getNodeForVKey(hashAlg.hash(digest, 0));
	}

	private NamedNode getNodeForVKey(Long vk) {
		if (!ketamaNodes.containsKey(vk)) {
			SortedMap<Long, NamedNode> tailMap = ketamaNodes.tailMap(vk);
			if (tailMap.isEmpty()) {
				vk = ketamaNodes.firstKey();
			} else {
				vk = tailMap.firstKey();
			}
			// For JDK1.6 version
			// vk = ketamaNodes.ceilingKey(vk);
			// if (vk == null) {
			// vk = ketamaNodes.firstKey();
			// }
		}

		return ketamaNodes.get(vk);
	}
}
