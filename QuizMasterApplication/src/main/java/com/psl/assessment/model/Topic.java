package com.psl.assessment.model;

public enum Topic {
	JAVA("Java"),
	PYTHON("python"),
	CPP("Cpp");
	
	private String topic;
	
	private Topic(String topic)
	{
		this.topic = topic;
	}
	
	public static Topic parseFromString(String topic)
	{
		return Topic.valueOf(topic.toUpperCase());
	}
	
	@Override
	public String toString()
	{
		return this.topic;
	}
}
