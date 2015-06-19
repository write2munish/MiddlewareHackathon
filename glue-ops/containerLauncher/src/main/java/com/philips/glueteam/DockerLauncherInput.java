package com.philips.glueteam;

/**
 * Created by mikkel on 16-06-2015.
 */
public class DockerLauncherInput {
    private String eventType;
    private String taskName;
    private String crontabTablename;

    public DockerLauncherInput() {
    }

    public DockerLauncherInput(String eventType, String taskName) {
        this.eventType = eventType;
        this.taskName = taskName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

	public String getCrontabTablename() {
		return crontabTablename;
	}

	public void setCrontabTablename(String crontabTablename) {
		this.crontabTablename = crontabTablename;
	}
}