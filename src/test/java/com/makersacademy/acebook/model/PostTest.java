package com.makersacademy.acebook.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class PostTest {

	private Post post = new Post("hello");

	@Test
	public void postHasContent() {
		assertThat(post.getContent(), containsString("hello"));
	}

	@Test
	public void setContentChangesContent() {
		post.setContent("this is a new content!!");
		assertEquals(String.valueOf("this is a new content!!"), post.getContent());
	}

}
