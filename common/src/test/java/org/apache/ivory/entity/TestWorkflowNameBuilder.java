/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ivory.entity;

import java.util.Arrays;

import org.apache.ivory.Tag;
import org.apache.ivory.entity.v0.feed.Feed;
import org.apache.ivory.entity.v0.process.Process;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestWorkflowNameBuilder {

	@Test
	public void getTagTest() {
		Feed feed = new Feed();
		feed.setName("raw-logs");

		WorkflowNameBuilder<Feed> builder = new WorkflowNameBuilder<Feed>(feed);
		Tag tag = builder.getWorkflowTag("IVORY_FEED_RETENTION_raw-logs");
		Assert.assertEquals(tag, Tag.RETENTION);

		tag = builder.getWorkflowTag("IVORY_FEED_raw-logs");
		Assert.assertNull(tag);

		tag = builder.getWorkflowTag("IVORY_FEED_REPLICATION_raw-logs_corp1");
		Assert.assertEquals(tag, Tag.REPLICATION);

	}

	@Test
	public void getSuffixesTest() {
		Feed feed = new Feed();
		feed.setName("raw-logs");
		WorkflowNameBuilder<Feed> builder = new WorkflowNameBuilder<Feed>(feed);

		String suffixes = builder
				.getWorkflowSuffixes("IVORY_FEED_REPLICATION_raw-logs_corp-1");
		Assert.assertEquals(suffixes, "_corp-1");

		suffixes = builder
				.getWorkflowSuffixes("IVORY_FEED_REPLICATION_raw-logs");
		Assert.assertEquals(suffixes, "");
	}

	@Test
	public void WorkflowNameTest() {
		Feed feed = new Feed();
		feed.setName("raw-logs");

		WorkflowNameBuilder<Feed> builder = new WorkflowNameBuilder<Feed>(feed);
		Assert.assertEquals(builder.getWorkflowName().toString(),
				"IVORY_FEED_raw-logs");

		builder.setTag(Tag.REPLICATION);
		Assert.assertEquals(builder.getWorkflowName().toString(),
				"IVORY_FEED_REPLICATION_raw-logs");

		builder.setSuffixes(Arrays.asList("cluster1"));
		Assert.assertEquals(builder.getWorkflowName().toString(),
				"IVORY_FEED_REPLICATION_raw-logs_cluster1");

		Process process = new Process();
		process.setName("agg-logs");
		WorkflowNameBuilder<Process> processBuilder = new WorkflowNameBuilder<Process>(
				process);
		processBuilder.setTag(Tag.DEFAULT);
		Assert.assertEquals(processBuilder.getWorkflowName().toString(),
				"IVORY_PROCESS_DEFAULT_agg-logs");

	}
}
