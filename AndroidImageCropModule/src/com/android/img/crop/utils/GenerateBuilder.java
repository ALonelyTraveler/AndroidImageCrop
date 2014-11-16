package com.android.img.crop.utils;

import java.io.File;
import java.util.List;

import com.android.img.crop.listener.GenerateListener;
import com.android.img.crop.model.ConfigModel;

public class GenerateBuilder {
	private String rootPath;
	private ConfigModel currentModel;// 当前模式
	private List<ConfigModel> generateModels;// 需要生成的模式
	private GenerateListener listener;
	private String iconName;

	public GenerateBuilder setRootPath(String rootPath) {
		this.rootPath = rootPath;
		return this;
	}

	public GenerateBuilder setCurrentMode(ConfigModel model) {
		this.currentModel = model;
		return this;
	}

	public GenerateBuilder setIconName(String iconName) {
		this.iconName = iconName;
		return this;
	}

	public GenerateBuilder setGenerateListener(GenerateListener listener) {
		this.listener = listener;
		return this;
	}

	public GenerateBuilder setGenerateModels(List<ConfigModel> models) {
		this.generateModels = models;
		return this;
	}

	public void generate() {
		if (rootPath != null && !rootPath.equals("")) {
			File file = new File(rootPath);
			if ((!file.exists()) || file.isFile()) {
				if (listener != null) {
					listener.generateFail("文件目录不存在 ！");
				}
			} else {
				new GenerateRunnable().start();
			}
		} else {
			if (listener != null) {
				listener.generateFail("文件目录不能为空");
			}
		}
	}

	class GenerateRunnable extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			File srcFile = new File(rootPath);
			if(listener!=null)
			{
				listener.generateStart();
			}
			if (srcFile.exists() && srcFile.isDirectory()) {
				List<File> files = FileUtils.getAllWithEnd(srcFile, "jpg",
						"png");
				if (files != null) {
					for (File file : files) {
						if (file.isFile()) {
							System.out.println("文件名---->"
									+ file.getAbsolutePath());
							for (ConfigModel model : generateModels) {
								File dstFile = new File(
										srcFile.getParentFile(),
										model.getFolderName() + "/"
												+ file.getName());
								FileUtils.createFolder(dstFile.getParentFile());
								if(file.getName().equals(iconName))
								{
									ImageUtils.writeHighQuality(
											ImageUtils.zoomImage(file,
													currentModel.getIconWidth(),
													model.getIconWidth()), dstFile);
								}
								else{
									ImageUtils.writeHighQuality(
											ImageUtils.zoomImage(file,
													currentModel.getWidth(),
													model.getWidth()), dstFile);
								}
							}
						}
					}
				}
			}
			if (listener != null) {
				listener.generateSuccess();
			}
		}
	}
}
