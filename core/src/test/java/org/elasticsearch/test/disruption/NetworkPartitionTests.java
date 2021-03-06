/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.elasticsearch.test.disruption;


import org.apache.lucene.util.LuceneTestCase;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.elasticsearch.test.InternalTestCluster;
import org.junit.Test;

import java.io.IOException;

@LuceneTestCase.Slow
public class NetworkPartitionTests extends ElasticsearchIntegrationTest{

    @Test
    public void testNetworkPartitionWithNodeShutdown() throws IOException {
        internalCluster().ensureAtLeastNumDataNodes(2);
        String[] nodeNames = internalCluster().getNodeNames();
        NetworkPartition networkPartition = new NetworkUnresponsivePartition(nodeNames[0], nodeNames[1], getRandom());
        internalCluster().setDisruptionScheme(networkPartition);
        networkPartition.startDisrupting();
        internalCluster().stopRandomNode(InternalTestCluster.nameFilter(nodeNames[0]));
        internalCluster().clearDisruptionScheme();
    }
}
