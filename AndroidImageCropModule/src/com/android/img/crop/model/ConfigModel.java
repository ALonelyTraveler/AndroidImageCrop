package com.android.img.crop.model;

import java.io.Serializable;

public class ConfigModel implements Serializable {

	/**
	 * 配置信息
	 */
	private static final long serialVersionUID = 1L;

	private String name; // 名称
	private int width; // 宽度
	private int height; // 高度
	private String folderName;//文件夹名
	private int iconWidth;//图标大小

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("name="+name+";width="+width+";height="+height);
		return super.toString();
	}

}
