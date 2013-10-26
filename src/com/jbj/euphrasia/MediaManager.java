package com.jbj.euphrasia;

import java.io.FileDescriptor;

public abstract class MediaManager {
	
	protected FileDescriptor myFileName;
	protected boolean myStatus;
	
	public MediaManager(FileDescriptor fileName){
		myFileName = fileName;
		myStatus = false;
	}
	
	public void execute(){
		if(!myStatus)
			this.start();
		else
			this.stop();
		myStatus = !myStatus;
	}
	
	protected abstract void start();
	
	protected abstract void stop();
	
	public abstract void pause();
}
