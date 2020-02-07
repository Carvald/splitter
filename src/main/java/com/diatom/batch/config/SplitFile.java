package com.diatom.batch.config;



import java.util.List;

public class SplitFile {
private List<String> fileLines;
private String name;
public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

private Integer fileCount;




public List<String> getFileLines() {
 return fileLines;
}

public void setFileLines(List<String> fileLines) {
 this.fileLines = fileLines;
}

public Integer getFileCount() {
 return fileCount;
}

public void setFileCount(Integer fileCount) {
 this.fileCount = fileCount;
}

}