package com.android.img.crop.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.android.img.crop.model.ConfigModel;

public class ConfigUtils {

	//获取可裁剪的图片格式列表
	public static List<ConfigModel> getConfigModels(InputStream inputStream) throws Exception {
		List<ConfigModel> list = null;
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputStream);

		// 获取根节点
		Element root = document.getRootElement();

		Element image_config = root.element("img_config");
		if (image_config != null) {
			list = new ArrayList<ConfigModel>();
			List<Element> models = image_config.elements("model");
			for (Element element : models) {
				ConfigModel model = new ConfigModel();
				model.setName(element.attributeValue("name"));
				model.setWidth(Integer.parseInt(element.attributeValue("width")));
				model.setHeight(Integer.parseInt(element
						.attributeValue("height")));
				model.setFolderName(element.attributeValue("folder"));
				model.setIconWidth(Integer.parseInt(element.attributeValue("iconWidth")));
				list.add(model);
			}
		}
		return list;
	}

	//获取配置文件中的默认图标
	public static String getIconName(InputStream inputStream) throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputStream);

		// 获取根节点
		Element root = document.getRootElement();
		String name = null;
		Element firstWorldElement = root.element("default_icon_name");
		if (firstWorldElement != null) {
			name = firstWorldElement.attributeValue("name");
		}
		return name;
	}

}
